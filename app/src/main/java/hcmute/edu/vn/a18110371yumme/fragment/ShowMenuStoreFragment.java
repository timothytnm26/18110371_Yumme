package hcmute.edu.vn.a18110371yumme.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import hcmute.edu.vn.a18110371yumme.DAO.StoreDAO;
import hcmute.edu.vn.a18110371yumme.DAO.ShowMenuDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.adapter.ShowMenuStoreAdapter;
import hcmute.edu.vn.a18110371yumme.localdb.DbHelper;
import hcmute.edu.vn.a18110371yumme.models.Store;
import hcmute.edu.vn.a18110371yumme.models.Cart;
import hcmute.edu.vn.a18110371yumme.models.Good;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class ShowMenuStoreFragment extends Fragment {
    public static String idStore;
    public static ShowMenuStoreAdapter showMenuStoreAdapter;
    RecyclerView recyclerMenu;
    Integer Number, TotalPrice;
    ShowMenuDAO showMenuDAO;
    ArrayList<Good> list = new ArrayList<>();
    ArrayList<Store> listStore = new ArrayList<>();
    public ShowMenuStoreFragment(String idStore) {
        this.idStore = idStore;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_one_store_fragment, container, false);
        final TextView storeName, storeLocation;
        ImageView ivBack = view.findViewById(R.id.ic_back);
        storeName = view.findViewById(R.id.txt_StoreName);
        storeLocation = view.findViewById(R.id.txt_store_address);
        recyclerMenu = view.findViewById(R.id.recyclerStoreMenu);
        showMenuDAO = new ShowMenuDAO(getActivity());
        LinearLayoutManager place = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerMenu.setLayoutManager(place);
        list = showMenuDAO.getGoodsByStoreID(idStore);
        showMenuStoreAdapter = new ShowMenuStoreAdapter(list,getActivity());
        recyclerMenu.setAdapter(showMenuStoreAdapter);
        showMenuStoreAdapter.setOnMenuItemClickListener(new ShowMenuStoreAdapter.OnMenuClickListener() {
            @Override
            public void onMenuItemClick(final int position) {
                final Dialog dialog = new Dialog(getActivity(), R.style.theme_dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.add_to_cart);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.BOTTOM;
                lp.windowAnimations = R.style.DialogAnimation;

                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                dialog.show();

                //ánh xạ
                final DecimalFormat formatter = new DecimalFormat("###,###,###");
                ImageView GoodImage = dialog.findViewById(R.id.good_image);
                TextView GoodName = dialog.findViewById(R.id.txt_good_name);
                TextView GoodPrice = dialog.findViewById(R.id.txt_good_price);
                final Button ButtonAddToCart = dialog.findViewById(R.id.btn_add_to_cart);
                LinearLayoutCompat ButtonGetDown = dialog.findViewById(R.id.btn_cancel);
                final ElegantNumberButton ButtonNumber = dialog.findViewById(R.id.btn_number);

                //set default so luong
                ButtonNumber.setRange(1, 10);
                ButtonNumber.setNumber("1");
                Number = Integer.parseInt(ButtonNumber.getNumber());
                TotalPrice = Number * list.get(position).getGoodPrice();
                ButtonAddToCart.setText("Add to Cart - " + formatter.format(TotalPrice) + " VND");

                //sự kiện
                try {
                    Picasso.with(getActivity()).load(list.get(position).getGoodImage()).into(GoodImage);
                } catch (Exception e) {
                    Picasso.with(getActivity()).load("https://firebasestorage.googleapis.com/v0/b/polyfood-7fcd7.appspot.com/o/no_image.jpg?alt=media&token=fa11b05a-5e3e-4f0b-a172-f10dad5208f6").into(GoodImage);
                }

                GoodName.setText(list.get(position).getGoodName());
                GoodPrice.setText(formatter.format(list.get(position).getGoodPrice()) + " VND");
                ButtonNumber.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                        Number = Integer.parseInt(ButtonNumber.getNumber());
                        TotalPrice = Number * list.get(position).getGoodPrice();
                        ButtonAddToCart.setText("Add to Cart - " + formatter.format(TotalPrice) + " VND");
                    }
                });
                ButtonGetDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                //Nút thêm vào giỏ hàng
                ButtonAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String GoodID = list.get(position).getGoodID();
                        String OrderID = null;
                        int number = Number;
                        Cart cart = new Cart(OrderID,GoodID,number);

                        //1. Add vô ArrayList
                        DbHelper.cart.add(cart);
                        //2. Add vô SQLite
                        DbHelper db = new DbHelper(getContext());
                        db.insertCart(cart);
                        db.insertGood(list.get(position));
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Added to Cart !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new ListStoreFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference("Store");
        mData.child(idStore).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Store store = dataSnapshot.getValue(Store.class);
                storeName.setText(store.getStoreName());
                storeLocation.setText(store.getStoreAddress());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}