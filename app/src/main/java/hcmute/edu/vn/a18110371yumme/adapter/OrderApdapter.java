package hcmute.edu.vn.a18110371yumme.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import hcmute.edu.vn.a18110371yumme.DAO.OrderDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.Order;
import hcmute.edu.vn.a18110371yumme.models.Store;

public class OrderApdapter extends RecyclerView.Adapter<OrderApdapter.ViewHolder> {

    List<Order> list;
    Context context;
    OrderDAO orderDAO;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public OrderApdapter(List<Order> list, Context context){
        this.list = list;
        this.context = context;
        orderDAO = new OrderDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_one_item,parent,false);
        orderDAO = new OrderDAO(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String i = list.get(position).getStoreID();
        holder.OrderID.setText(list.get(position).getOrderID());
        holder.Time.setText(list.get(position).getTime());
        holder.State.setText(list.get(position).getState());

        mData.child("CuaHang").child(i).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Store store = dataSnapshot.getValue(Store.class);

                holder.StoreName.setText(store.getStoreName());

                Log.d("abc1","" + list.get(position).getOrderID());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView OrderID, StoreName, Time, State;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            OrderID = itemView.findViewById(R.id.txt_orderID);
            StoreName = itemView.findViewById(R.id.txt_store_name);
            Time = itemView.findViewById(R.id.txt_date);
            State = itemView.findViewById(R.id.txt_state);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
