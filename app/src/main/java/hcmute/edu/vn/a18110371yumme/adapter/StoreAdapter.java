package hcmute.edu.vn.a18110371yumme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.edu.vn.a18110371yumme.DAO.StoreDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.Store;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    Context context;
    ArrayList<Store> Store;
    StoreDAO storeDAO;

    public StoreAdapter(ArrayList<Store> store, Context context){
        this.Store = store;
        this.context = context;
        storeDAO = new StoreDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.store_one_item,parent,false);
        storeDAO = new StoreDAO(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.StoreID.setText(Store.get(position).getStoreID());
        holder.StoreEmail.setText(Store.get(position).getStoreMail());
        holder.StoreName.setText(Store.get(position).getStoreName());
        holder.StorePassword.setText(Store.get(position).getStorePassword());
        holder.StoreAddress.setText(Store.get(position).getStoreAddress());

    }

    @Override
    public int getItemCount() {return Store.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView StoreID, StoreEmail, StoreName, StorePassword, StoreAddress;
            public ViewHolder(@NonNull View itemView) {
            super(itemView);
                StoreID = itemView.findViewById(R.id.txt_store_id);
                StoreEmail = itemView.findViewById(R.id.txt_store_email);
                StoreName = itemView.findViewById(R.id.txt_store_name);
                StorePassword = itemView.findViewById(R.id.txt_password);
                StoreAddress = itemView.findViewById(R.id.txt_store_address);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
