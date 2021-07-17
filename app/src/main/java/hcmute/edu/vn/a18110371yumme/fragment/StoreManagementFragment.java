package hcmute.edu.vn.a18110371yumme.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.edu.vn.a18110371yumme.DAO.StoreDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.adapter.StoreAdapter;
import hcmute.edu.vn.a18110371yumme.models.Store;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StoreManagementFragment extends Fragment {

    StoreDAO storeDAO = new StoreDAO(getActivity());
    private FirebaseAuth mAuth;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    String userID;
    public static StoreAdapter storeAdapter;
    RecyclerView lv;
    ArrayList<Store> list = new ArrayList<>();
    FloatingActionButton add;

    public StoreManagementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_store_management, container, false);
        lv = view.findViewById(R.id.rcv_store_management);
        add = view.findViewById(R.id.btn_add_store);
        mAuth = FirebaseAuth.getInstance();

        list = storeDAO.getAll();
        storeAdapter = new StoreAdapter(list,getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(layoutManager);
        lv.setAdapter(storeAdapter);
        Log.d("test1", String.valueOf(list));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.add_store,null);
                final EditText StoreName = view1.findViewById(R.id.edt_store_name);
                final EditText StoreEmail = view1.findViewById(R.id.edt_store_email);
                final EditText StorePassword = view1.findViewById(R.id.edt_password);
                builder.setView(view1);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        final String storeName = StoreName.getText().toString();
                        final String storeEmail = StoreEmail.getText().toString();
                        final String storePassword = StorePassword.getText().toString();

                        mAuth.createUserWithEmailAndPassword(storeEmail, storePassword)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Log.v(storeEmail,storePassword);
                                            Toast.makeText(getActivity(), "Sign Up Successfully !!",
                                                    Toast.LENGTH_SHORT).show();
                                            userID = mAuth.getCurrentUser().getUid();
                                            Store store = new Store(userID,storeEmail,storePassword,null, storeName,"",1.0,"",null,null,1);
                                            mData.child("Store").child(userID).push();
                                            mData.child("Store").child(userID).setValue(store);


                                        } else {
                                            Log.v(storeEmail,storePassword);
                                            Toast.makeText(getActivity(), "Please type Email form correctly !! Password must have 6 characters !!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
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
        return view;
    }
}