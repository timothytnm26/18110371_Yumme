package hcmute.edu.vn.a18110371yumme.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import hcmute.edu.vn.a18110371yumme.Login;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class SettingFragment extends Fragment {
    TextView ChangeInfo, ChangePassword;
    ImageView Logout;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference1 = firebaseDatabase.getReference("User");
    CircularImageView profile_image;
    TextView NameProfile, MailProfile, PhoneProfile, AddressProfile, BirthDayProfile;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    public static String UserID;
    public static String UserEmail;
    public static String UserPassword;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        NameProfile = view.findViewById(R.id.txt_user_name);
        MailProfile = view.findViewById(R.id.txt_user_email);
        PhoneProfile = view.findViewById(R.id.edt_user_phone);
        AddressProfile = view.findViewById(R.id.tvDiaChiProfile);
        BirthDayProfile = view.findViewById(R.id.edt_date_of_birth);
        ChangeInfo = view.findViewById(R.id.txt_change_info);
        ChangePassword = view.findViewById(R.id.txt_change_password);
        profile_image = view.findViewById(R.id.profile_image);
        UserID = fAuth.getCurrentUser().getUid();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            UserEmail = user.getEmail();
            MailProfile.setText(UserEmail);
        databaseReference1.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                NameProfile.setText(user.getUserName());
                PhoneProfile.setText(user.getUserPhone());
                AddressProfile.setText(user.getUserAddress());
                BirthDayProfile.setText(user.getUserBirthday());
                UserPassword = user.getUserPassword();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Logout = view.findViewById(R.id.ivLogout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view1 = layoutInflater.inflate(R.layout.logout_alert_dialog,null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), Login.class));
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
        });

        ChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new ChangeInfoFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new ChangePasswordFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
    public void Logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(),Login.class));
    }
}