package hcmute.edu.vn.a18110371yumme.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.edu.vn.a18110371yumme.DAO.UserDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.adapter.UserAdapter;
import hcmute.edu.vn.a18110371yumme.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserManagementFragment extends Fragment {

     UserDAO userDAO = new UserDAO(getActivity());
    private FirebaseAuth mAuth;

    public static UserAdapter userAdapter;
    RecyclerView recyclerView;
    ArrayList<User> list = new ArrayList<>();


    public UserManagementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_management, container, false);
        recyclerView = view.findViewById(R.id.rcv_user_management);
        mAuth = FirebaseAuth.getInstance();

        list = userDAO.getAll();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(getActivity(),list);
        recyclerView.setAdapter(userAdapter);
        Log.d("test1", String.valueOf(list));

        return view;
    }
}