package hcmute.edu.vn.a18110371yumme.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.edu.vn.a18110371yumme.DAO.TypeDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.Type;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    Context context;
    ArrayList<Type> Types;
    TypeDAO TypeDAO;

    public TypeAdapter(ArrayList<Type> types, Context context){
        this.Types =types;
        this.context = context;
        TypeDAO = new TypeDAO(context);
    }

    public TypeAdapter(Context context) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.type_one_item,parent,false);
        TypeDAO = new TypeDAO (context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.TypeID.setText(Types.get(position).getTypeID());
        holder.TypeName.setText(Types.get(position).getTypeName());
        holder.TypeDescription.setText(Types.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return Types.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView TypeID, TypeName, TypeDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TypeID = itemView.findViewById(R.id.txt_type_id);
            TypeName = itemView.findViewById(R.id.edt_type_name);
            TypeDescription = itemView.findViewById(R.id.edt_type_description);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int position = getLayoutPosition();
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
            final Type type = Types.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view1 = layoutInflater.inflate(R.layout.edit_type,null);
            final TextView TypeName = view1.findViewById(R.id.edt_type_name);
            final TextView TypeDescription = view1.findViewById(R.id.edt_type_description);
            final TextView Image = view1.findViewById(R.id.edt_image);
            TypeName.setText(type.getTypeName());
            TypeDescription.setText(type.getDescription());




            builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    final String typeName = TypeName.getText().toString();
                    final String typeDescription = TypeDescription.getText().toString();
                    final String image = Image.getText().toString();
                    Type s = new Type(type.getTypeID(),typeName,typeDescription,image);
                    TypeDAO.update(s);
                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setView(view1);
            builder.show();
        }
        @Override
        public boolean onLongClick(View view) {
            final int position = getLayoutPosition();
            if (getAdapterPosition() == RecyclerView.NO_POSITION);
            final Type type =Types.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view1 = layoutInflater.inflate(R.layout.delete_alert_dialog,null);


            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Type s = new Type(type.getTypeID(),type.getTypeName(),type.getDescription(),type.getImage());
                    TypeDAO.delete(s);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setView(view1);
            builder.show();

            return true;
        }
    }

}
