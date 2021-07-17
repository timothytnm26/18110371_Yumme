package hcmute.edu.vn.a18110371yumme.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.a18110371yumme.DAO.GoodDAO;
import hcmute.edu.vn.a18110371yumme.DAO.TypeDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.Good;
import hcmute.edu.vn.a18110371yumme.models.Type;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder> {
    List<Good> list;
    Context context;
    GoodDAO goodDAO;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference("Good");
    public GoodAdapter(List<Good> list, Context context){
        this.list = list;
        this.context = context;
        goodDAO = new GoodDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.good_one_item,parent,false);
        goodDAO = new GoodDAO(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        String i = list.get(position).getStoreID();

        //  Thêm ảnh
        Picasso.with(context).load(list.get(position).getGoodImage()).into(holder.GoodImage);
        holder.GoodName.setText(list.get(position).getGoodName());
        holder.GoodPrice.setText(formatter.format(list.get(position).getGoodPrice())+" VND");
        holder.Description.setText(list.get(position).getDescription());

        final GoodDAO goodDAO = new GoodDAO(context);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ArrayList<Type> listPL = new TypeDAO(context).getAllspn();
        //delete
        holder.ItemGood.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("NOTIFICATION !!");
                builder.setMessage("Do you want to delete ?");

                //btn Yes
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String id = list.get(position).getGoodID();
                        goodDAO.delete(id);

                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                //btn No
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog myAlert = builder.create();
                myAlert.show();
                return false;
            }
        });

        // edit
        holder.ItemGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.add_good,null);

                final EditText Good_name = view1.findViewById(R.id.edt_good_name);
                final Spinner Spinner = view1.findViewById(R.id.spinner_type);
                final EditText Description = view1.findViewById(R.id.edt_description);
                final EditText Good_price = view1.findViewById(R.id.edt_price);
                final EditText Image = view1.findViewById(R.id.edt_image);

                Good good = list.get(position);
                Good_name.setText(good.getGoodName());
                Description.setText(good.getDescription());
                Good_price.setText(String.valueOf(good.getGoodPrice()));
                Image.setText(good.getGoodImage());

                //Test
                ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, listPL);
                Spinner.setAdapter(adapter);

                int idxLS = -1;
                for (int i = 0; i < listPL.size(); i++){
                    if(listPL.get(i).getTypeID().toString().equalsIgnoreCase(good.getTypeID())){
                        idxLS = i;
                        break;
                    }
                }
                Spinner.setSelection(idxLS);

                builder.setView(view1);
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String goodName = Good_name.getText().toString();
                        String description = Description.getText().toString();
                        int price = Integer.parseInt(Good_price.getText().toString());
                        String image = Image.getText().toString();
                        Type type = (Type) Spinner.getSelectedItem();
                        String typeID = type.getTypeID();
                        String goodID = list.get(position).getGoodID();
                        String a = mAuth.getCurrentUser().getUid();
                        Good good = new Good(goodID,goodName,price,image,a,typeID,description);
                        goodDAO.update(good, goodID);
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView GoodName, Description, GoodPrice;
        public ImageView GoodImage;
        LinearLayout ItemGood;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            GoodName = itemView.findViewById(R.id.edt_good_name);
            Description = itemView.findViewById(R.id.edt_good_description);
            GoodPrice = itemView.findViewById(R.id.edt_good_price);
            GoodImage = itemView.findViewById(R.id.good_image);
            ItemGood = itemView.findViewById(R.id.item_good);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
