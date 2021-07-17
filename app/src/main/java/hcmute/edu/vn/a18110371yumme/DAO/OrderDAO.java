package hcmute.edu.vn.a18110371yumme.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import hcmute.edu.vn.a18110371yumme.fragment.OrdersFragment;
import hcmute.edu.vn.a18110371yumme.fragment.StoreActivityFragment;
import hcmute.edu.vn.a18110371yumme.fragment.OrdersFragment;
import hcmute.edu.vn.a18110371yumme.models.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderDAO {
    DatabaseReference mDatabase;
    Context context;
    public OrderDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("Order");
        this.context = context;
    }


    public ArrayList<Order> getOrderByUserID(String userID) {
        final ArrayList<Order> list = new ArrayList<Order>();
        mDatabase.orderByChild("userID").equalTo(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ds.getKey();
                    Order order = ds.getValue(Order.class);
                    Log.d("ab1", order.getStoreID());
                    list.add(order);

                }
                OrdersFragment.orderApdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }
    public ArrayList<Order> getOrderByStoreID(String storeID) {
        final ArrayList<Order> list = new ArrayList<Order>();
        mDatabase.orderByChild("storeID").equalTo(storeID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ds.getKey();
                    Order order = ds.getValue(Order.class);
                    Log.d("ab1", order.getStoreID());
                    list.add(order);

                }
                StoreActivityFragment.orderApdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }


}
