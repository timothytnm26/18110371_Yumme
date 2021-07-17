package hcmute.edu.vn.a18110371yumme.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.edu.vn.a18110371yumme.DAO.GoodDAO;
import hcmute.edu.vn.a18110371yumme.DAO.TypeDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.adapter.GoodAdapter;
import hcmute.edu.vn.a18110371yumme.models.Good;
import hcmute.edu.vn.a18110371yumme.models.Type;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class StoreGoodsFragment extends Fragment {

    RecyclerView rcv;
    FloatingActionButton add;

    GoodDAO goodDAO = new GoodDAO(getActivity());
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static GoodAdapter goodAdapter;
    ArrayList<Good> list = new ArrayList<>();

    public StoreGoodsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_store_goods, container, false);
        rcv = view.findViewById(R.id.recycler_store_good);
        add = view.findViewById(R.id.btn_add_good);

        String i = mAuth.getCurrentUser().getUid();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();
        list = goodDAO.getAll(i);
        goodAdapter = new GoodAdapter(list,getActivity());
        rcv.setAdapter(goodAdapter);
        final ArrayList<Type> listPL = new TypeDAO(getActivity()).getAllspn();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.add_good,null);
                final EditText GoodName = view1.findViewById(R.id.edt_good_name);
                final Spinner spinner = view1.findViewById(R.id.spinner_type);
                final EditText Description = view1.findViewById(R.id.edt_description);
                final EditText Price = view1.findViewById(R.id.edt_price);
                final EditText url = view1.findViewById(R.id.edt_image);

                //Test

                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, listPL);
                spinner.setAdapter(adapter);

                builder.setView(view1);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String goodName = GoodName.getText().toString();
                        String description = Description.getText().toString();
                        int price = Integer.parseInt(Price.getText().toString());
                        String url1 = url.getText().toString();
                        Type type = (Type) spinner.getSelectedItem();
                        String typeId = type.getTypeID();

                        String a = mAuth.getCurrentUser().getUid();
                        Good s = new Good(null,goodName,price,url1,a,typeId,description);
                        goodDAO.insert(s);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(view1);
                builder.show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}