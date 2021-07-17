package hcmute.edu.vn.a18110371yumme.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.edu.vn.a18110371yumme.DAO.OrderDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.adapter.OrderApdapter;
import hcmute.edu.vn.a18110371yumme.models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class OrdersFragment extends Fragment {

    RecyclerView rcv;
    OrderDAO orderDAO = new OrderDAO(getActivity());
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static OrderApdapter orderApdapter;
    ArrayList<Order> list = new ArrayList<>();

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        String i = mAuth.getCurrentUser().getUid();
        rcv = view.findViewById(R.id.rcv_Order);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();
        list = orderDAO.getOrderByUserID(""+ i +"");
        orderApdapter = new OrderApdapter(list,getActivity());
        rcv.setAdapter(orderApdapter);

        return view;
    }
}