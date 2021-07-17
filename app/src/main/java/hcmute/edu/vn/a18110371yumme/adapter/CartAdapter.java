package hcmute.edu.vn.a18110371yumme.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.fragment.CartFragment;
import hcmute.edu.vn.a18110371yumme.localdb.DbHelper;
import hcmute.edu.vn.a18110371yumme.models.CartDetail;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<CartDetail> list;
    Context context;

    public CartAdapter(ArrayList<CartDetail> list, Context context){
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_one_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.GoodName.setText(list.get(position).getGoodName());
        holder.Number.setText("Số lượng: "+list.get(position).getNumber()+"");
        holder.Price.setText("Đơn giá: "+formatter.format(list.get(position).getGoodPrice()) + "VNĐ");
        holder.TotalPrice.setText("Tổng: "+formatter.format(list.get(position).getNumber() * list.get(position).getGoodPrice())+ "VNĐ");
        Picasso.with(context).load(list.get(position).getImage()).into(holder.Image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView GoodName, Number, TotalPrice, Price;
        public ImageView Image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            GoodName = itemView.findViewById(R.id.txt_good_name);
            Number = itemView.findViewById(R.id.txt_number);
            TotalPrice = itemView.findViewById(R.id.txt_total_price);
            Image = itemView.findViewById(R.id.cart_image);
            Price = itemView.findViewById(R.id.txt_good_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            final int position = getLayoutPosition();
            if (getAdapterPosition() == RecyclerView.NO_POSITION);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view1 = layoutInflater.inflate(R.layout.delete_alert_dialog,null);


            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String s = list.get(position).getGoodId();
                    DbHelper dbHelper = new DbHelper(context);
                    Log.d("1122",s);
                    dbHelper.delete(s);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    CartFragment cartFragment = new CartFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, cartFragment).addToBackStack(null).commit();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setView(view1);
            builder.show();

        }
    }

}
