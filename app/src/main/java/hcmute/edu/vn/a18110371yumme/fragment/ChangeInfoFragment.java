package hcmute.edu.vn.a18110371yumme.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ChangeInfoFragment extends Fragment {
    EditText UserName, UserPhone, UserAddress;
    TextView DateOfBirth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference1 = firebaseDatabase.getReference("User");
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String UserID, UserPassword, UserEmail;
    Button Confirm;
    DatePickerDialog datePickerDialog;
    public ChangeInfoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_change_info, container, false);
       UserName = view.findViewById(R.id.ed_ChangeName);
       UserPhone = view.findViewById(R.id.ed_ChangePhone);
       UserAddress = view.findViewById(R.id.ed_ChangeAddress);
       DateOfBirth = view.findViewById(R.id.ed_ChangeNgaySinh);
       Confirm = view.findViewById(R.id.btn_confirm);
       UserID = fAuth.getCurrentUser().getUid();
       databaseReference1.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                UserName.setText(user.getUserName());
                UserAddress.setText(user.getUserAddress());
                UserPassword = user.getUserPassword();
                UserPhone.setText(user.getUserPhone());
                DateOfBirth.setText(user.getUserBirthday());
                UserEmail = user.getUserEmail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       DateOfBirth.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final Calendar calendar = Calendar.getInstance();
               int d = calendar.get(Calendar.DAY_OF_MONTH);
               int m = calendar.get(Calendar.MONTH);
               int y = calendar.get(Calendar.YEAR);
               datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       final String startDay = dayOfMonth + "/" + (month + 1) + "/" + year;
                       DateOfBirth.setText(startDay);
                   }
               }, y, m, d);
               datePickerDialog.show();
           }
       });

       Confirm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String userName = UserName.getText().toString();
               String userPhone = UserPhone.getText().toString();
               String userAddress = UserAddress.getText().toString();
               String userBirthday = DateOfBirth.getText().toString();
               if (userName.length() > 0 && userPhone.length() >0 && userAddress.length() >0 && userBirthday.length()>0) {
                       databaseReference1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userName").setValue(userName);
                       databaseReference1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userPhone").setValue(userPhone);
                       databaseReference1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userAddress").setValue(userAddress);
                       databaseReference1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userBirthday").setValue(userBirthday);
                       Toast.makeText(getActivity(), "Change Info Successfully !!", Toast.LENGTH_SHORT).show();
                   Fragment newFragment = new SettingFragment();
                   FragmentTransaction transaction = getFragmentManager().beginTransaction();
                   transaction.replace(R.id.frame_layout, newFragment);
                   transaction.addToBackStack(null);
                   transaction.commit();
               } else {
                   Toast.makeText(getActivity(), "Please fill all Info !!", Toast.LENGTH_SHORT).show();
               }
           }
       });
        return view;
    }
}