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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.edu.vn.a18110371yumme.DAO.TypeDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.adapter.TypeAdapter;
import hcmute.edu.vn.a18110371yumme.models.Type;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TypeFragment extends Fragment {

    public static String id;
    TypeDAO typeDAO= new TypeDAO(getActivity());
    private FirebaseAuth mAuth;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference("Type");
    public static TypeAdapter typeAdapter;
    RecyclerView recyclerView;
    ArrayList<Type> list = new ArrayList<>();
    FloatingActionButton add;

    public TypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_type, container, false);
        recyclerView = view.findViewById(R.id.rcv_type);
        add = view.findViewById(R.id.btn_type);
        mAuth = FirebaseAuth.getInstance();

        list = typeDAO.getAll();
        typeAdapter= new TypeAdapter(list,getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(typeAdapter);
        Log.d("test2", String.valueOf(list));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = getLayoutInflater().inflate(R.layout.add_type,null);
                final EditText TypeName = view1.findViewById(R.id.edt_type_name);
                final EditText TypeDescription = view1.findViewById(R.id.edt_description);
                final EditText Image = view1.findViewById(R.id.edt_image);
                builder.setView(view1);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String image = Image.getText().toString();
                        String typeName = TypeName.getText().toString();
                        String typeDescription = TypeDescription.getText().toString();
                        Type s = new Type(null,typeName,typeDescription,image);
                        typeDAO.insert(s);

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
}
