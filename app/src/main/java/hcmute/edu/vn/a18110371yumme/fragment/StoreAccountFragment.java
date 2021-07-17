package hcmute.edu.vn.a18110371yumme.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hcmute.edu.vn.a18110371yumme.DAO.StoreDAO;
import hcmute.edu.vn.a18110371yumme.Login;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.Store;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class StoreAccountFragment extends Fragment {
    ImageView ivAvt;
    ImageView ivEditViTri, ivLogoutCH, ivEditProfileCH;
    TextView tvNameCHa, tvMailCHa,tvDiaChiCHa, tvDanhgia, kinhdo, vido;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    String userID = fAuth.getCurrentUser().getUid();
    StoreDAO cuaHangDAO = new StoreDAO(getActivity());

    public StoreAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_store_account, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ivAvt = view.findViewById(R.id.ivCH);
        ivEditViTri = view.findViewById(R.id.ivEditViTri);
        ivLogoutCH = view.findViewById(R.id.ivLogoutCH);
        tvNameCHa = view.findViewById(R.id.tvNameCHa);
        tvMailCHa = view.findViewById(R.id.tvMailCHa);
        tvDiaChiCHa = view.findViewById(R.id.tvDiaChiCHa);
        tvDanhgia = view.findViewById(R.id.tvDanhgia);
        kinhdo = view.findViewById(R.id.kinhdo);
        vido = view.findViewById(R.id.vido);
        ivEditProfileCH = view.findViewById(R.id.ivEditProfileCH);

        ivAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.edit_avata_store,null);
                final EditText url = view1.findViewById(R.id.url);
                builder.setView(view1);
                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mData.child("CuaHang").child(userID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Store user = dataSnapshot.getValue(Store.class);
                                String url1 = url.getText().toString();
                                userID = fAuth.getCurrentUser().getUid();
//                                        String userId = mData.push().getKey();
                                Store s = new Store(userID,user.getStoreMail(),user.getStorePassword(),user.getStoreGood(), user.getStoreName(),user.getStoreAddress(),user.getStoreRate(),url1,user.getStoreLatitude(),user.getStoreLongitude(),1);
                                cuaHangDAO.update(s);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(view1);
                builder.show();
            }
        });

        ivEditViTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view1 = layoutInflater.inflate(R.layout.store_location,null);

                final EditText kinhdo = view1.findViewById(R.id.edtKinhDo);
                final EditText vido = view1.findViewById(R.id.edtViDo);

                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mData.child("Store").child(userID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Store user = dataSnapshot.getValue(Store.class);
                                double kinhdo1 = Double.parseDouble(kinhdo.getText().toString());
                                double vido1 = Double.parseDouble(vido.getText().toString());

                                Store s = new Store(userID,user.getStoreMail(),user.getStorePassword(),user.getStoreGood(), user.getStoreName(),user.getStoreAddress(),user.getStoreRate(),user.getStoreImage(),vido1,kinhdo1,1);
                                cuaHangDAO.update(s);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(view1);
                builder.show();

            }
        });

        ivEditProfileCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view1 = layoutInflater.inflate(R.layout.edit_store,null);

                final EditText name = view1.findViewById(R.id.edtNameCHa);
                final EditText diachi = view1.findViewById(R.id.edtDiaCHiCHa);

                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mData.child("Store").child(userID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try{
                                    Store user = dataSnapshot.getValue(Store.class);
                                    String name1 = name.getText().toString();
                                    String diachi1 = diachi.getText().toString();

                                    Store s = new Store(userID,user.getStoreMail(),user.getStorePassword(),user.getStoreGood(), name1,diachi1,user.getStoreRate(),user.getStoreImage(),user.getStoreLatitude(),user.getStoreLongitude(),1);
                                    cuaHangDAO.update(s);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(view1);
                builder.show();
            }
        });

        mData.child("CuaHang").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Store user = dataSnapshot.getValue(Store.class);

                try {
                    Picasso.with(getActivity()).load(user.getStoreImage()).into(ivAvt);
                    tvNameCHa.setText(user.getStoreName());
                    tvMailCHa.setText(user.getStoreMail());
                    tvDiaChiCHa.setText(user.getStoreAddress());
                    tvDanhgia.setText(String.valueOf(user.getStoreRate()));
                    kinhdo.setText(String.valueOf(user.getStoreLongitude()));
                    vido.setText(" , "+ String.valueOf(user.getStoreLatitude()));
                } catch (Exception e) {

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ivLogoutCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view1 = layoutInflater.inflate(R.layout.logout_alert_dialog,null);


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setView(view1);
                builder.show();
            }
        });

    }
}