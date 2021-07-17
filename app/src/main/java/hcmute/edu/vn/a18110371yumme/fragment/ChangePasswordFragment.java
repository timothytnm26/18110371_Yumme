package hcmute.edu.vn.a18110371yumme.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import hcmute.edu.vn.a18110371yumme.Login;
import hcmute.edu.vn.a18110371yumme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static hcmute.edu.vn.a18110371yumme.fragment.SettingFragment.UserEmail;
import static hcmute.edu.vn.a18110371yumme.fragment.SettingFragment.UserPassword;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class ChangePasswordFragment extends Fragment {
    EditText Email, Password, RePassword, OldPassword;
    Button Confirm;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference1 = firebaseDatabase.getReference("User");
    public ChangePasswordFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_fragment, container, false);
        Email = view.findViewById(R.id.edt_user_email);
        Password = view.findViewById(R.id.edt_password);
        RePassword = view.findViewById(R.id.edt_re_passWord);
        OldPassword = view.findViewById(R.id.edt_old_password);
        Confirm = view.findViewById(R.id.btn_confirm);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String re_password = RePassword.getText().toString();
                String old_password = OldPassword.getText().toString();
                if(email.isEmpty() || password.isEmpty() || re_password.isEmpty()){
                    Toast.makeText(getActivity(), "You must fill all Info", Toast.LENGTH_SHORT).show();
                }else if (!email.equals(UserEmail)) {
                    Toast.makeText(getActivity(), "Email is not correct !!", Toast.LENGTH_SHORT).show();
                }else if (!old_password.equals(UserPassword)){
                    Toast.makeText(getActivity(), "Old password is not matched !!", Toast.LENGTH_SHORT).show();
                }else if(!password.equals(re_password)){
                    Toast.makeText(getActivity(), "Password is not matched !!", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    View view1 = layoutInflater.inflate(R.layout.change_pass_alert_dialog,null);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            auth.sendPasswordResetEmail(email)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Login again to continues !!", Toast.LENGTH_SHORT).show();
                                                FirebaseAuth.getInstance().signOut();
                                                startActivity(new Intent(getApplicationContext(), Login.class));
                                            }
                                        }
                                    });

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
        });
        return view;
    }
}