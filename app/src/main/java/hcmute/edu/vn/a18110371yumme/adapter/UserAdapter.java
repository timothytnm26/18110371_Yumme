package hcmute.edu.vn.a18110371yumme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.edu.vn.a18110371yumme.DAO.UserDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context context;
    ArrayList<User> list;
    UserDAO userDAO;



    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
        userDAO = new UserDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_one_item,parent,false);
        userDAO = new UserDAO(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.UserID.setText(list.get(position).getUserID());
        holder.UserBirthDay.setText(list.get(position).getUserBirthday());
        holder.UserName.setText(list.get(position).getUserName());
        holder.UserEmail.setText(list.get(position).getUserEmail());
        holder.UserPhoneNo.setText(list.get(position).getUserPhone());
        holder.UserAddress.setText(list.get(position).getUserAddress());    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView UserID, UserBirthDay, UserName, UserEmail, UserPhoneNo, UserAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UserID = itemView.findViewById(R.id.edt_user_id);
            UserBirthDay = itemView.findViewById(R.id.edt_date_of_birth);
            UserName = itemView.findViewById(R.id.edt_user_name);
            UserEmail = itemView.findViewById(R.id.edt_user_email);
            UserPhoneNo = itemView.findViewById(R.id.edit_phone_number);
            UserAddress = itemView.findViewById(R.id.edt_address);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
