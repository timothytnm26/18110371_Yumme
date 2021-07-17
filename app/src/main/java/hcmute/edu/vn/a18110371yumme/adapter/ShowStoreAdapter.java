package hcmute.edu.vn.a18110371yumme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.a18110371yumme.DAO.StoreDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.Store;

public class ShowStoreAdapter extends RecyclerView.Adapter<ShowStoreAdapter.ViewHolder> {

    Context context;
    private List<Store> Stores = new ArrayList<>();
    ArrayList<Store> Store;
    StoreDAO storeDAO;
    private OnStoreClickListener mListener;
    public void setOnStoreItemClickListener (OnStoreClickListener onStoreItemClickListener){
        mListener = onStoreItemClickListener;
    }
    public ShowStoreAdapter(ArrayList<Store> store, Context context){
        this.Store = store;
        this.context = context;
        storeDAO = new StoreDAO(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.store_item,parent,false);
        storeDAO = new StoreDAO(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Picasso.with(context).load(Store.get(position).getStoreImage()).into(holder.ivStorePicture);
            holder.storeName.setText(Store.get(position).getStoreName());
            holder.storeLocation.setText(Store.get(position).getStoreAddress());
            holder.storeRating.setText(String.valueOf(Store.get(position).getStoreRate()));
        }catch (Exception ex){

        }
    }
    @Override
    public int getItemCount() {
        return Store.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView storeName, storeLocation, storeRating ;
        public ImageView ivStorePicture;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeName = itemView.findViewById(R.id.place_name);
            storeLocation = itemView.findViewById(R.id.place_location);
            storeRating = itemView.findViewById(R.id.place_rating);
            ivStorePicture = itemView.findViewById(R.id.ivStorePicture);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onStoreItemClick(getPosition());
                }
            });
        }
        @Override
        public void onClick(View v) {

        }
    }
    public interface OnStoreClickListener {
        void onStoreItemClick(int position);
//        void onPlaceFavoriteClick(Place place);
    }

}
