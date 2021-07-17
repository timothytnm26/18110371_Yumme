package hcmute.edu.vn.a18110371yumme.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.adapter.CartAdapter;
import hcmute.edu.vn.a18110371yumme.localdb.DbHelper;
import hcmute.edu.vn.a18110371yumme.models.CartDetail;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    Button Add;
    CartAdapter cartAdapter;
    ArrayList<CartDetail> list = new ArrayList<>();
    DbHelper db;
    public CartFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.rcv_cart);
        Add = view.findViewById(R.id.btn_order);
        db = new DbHelper(getContext());

        LinearLayoutManager place = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(place);

        list = db.listCart();
        cartAdapter = new CartAdapter(list, getContext());
        recyclerView.setAdapter(cartAdapter);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}