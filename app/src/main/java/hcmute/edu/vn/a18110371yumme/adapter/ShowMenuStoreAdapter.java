package hcmute.edu.vn.a18110371yumme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.edu.vn.a18110371yumme.DAO.StoreDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.Good;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShowMenuStoreAdapter extends RecyclerView.Adapter<ShowMenuStoreAdapter.ViewHolder> {
    Context context;
    private List<Good> Goods = new ArrayList<>();
    ArrayList<Good> Good;
    StoreDAO storeDAO;
    RecyclerView rcvMenu;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    private OnMenuClickListener mListener;
    public void setOnMenuItemClickListener (OnMenuClickListener onMenuItemClickListener){
        mListener = onMenuItemClickListener;
    }
    public ShowMenuStoreAdapter(ArrayList<Good> good, Context context){
        this.Good = good;
        this.context = context;
        storeDAO = new StoreDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.good_one_item,parent,false);
        storeDAO = new StoreDAO(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(Good.get(position).getGoodImage()).into(holder.GoodImage);
        holder.GoodName.setText(Good.get(position).getGoodName());
        holder.GoodDescription.setText(Good.get(position).getDescription());
        holder.GoodPrice.setText(formatter.format(Good.get(position).getGoodPrice())+" VND");
    }

    @Override
    public int getItemCount() {
        return Good.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView GoodName, GoodDescription, GoodPrice;
        ImageView GoodImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rcvMenu = itemView.findViewById(R.id.recyclerStoreMenu);
            GoodName = itemView.findViewById(R.id.edt_good_name);
            GoodDescription = itemView.findViewById(R.id.edt_good_description);
            GoodPrice = itemView.findViewById(R.id.edt_good_price);
            GoodImage = itemView.findViewById(R.id.good_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onMenuItemClick(getPosition());
                }
            });
        }
        @Override
        public void onClick(View v) {

        }
    }
    public interface OnMenuClickListener {
        void onMenuItemClick(int position);
    }
}
