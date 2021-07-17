package hcmute.edu.vn.a18110371yumme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hcmute.edu.vn.a18110371yumme.models.Store;
import hcmute.edu.vn.a18110371yumme.models.User;

public class Login extends AppCompatActivity {
    private FirebaseAuth authentication;
    Button btn_login, btn_signup;
    TextInputLayout layout_email,layout_password;
    TextInputEditText edt_email, edt_password;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth f_authentication = FirebaseAuth.getInstance();;
    ProgressBar progressBar;
    LocationManager locationManager;
    boolean GpsStatus;
    int PERMISSION_ALL = 1;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authentication = FirebaseAuth.getInstance();

        btn_login = findViewById(R.id.button_signin);
        btn_signup = findViewById(R.id.button_signup);
        edt_email = findViewById(R.id.email);
        edt_password = findViewById(R.id.password);
        layout_email = findViewById(R.id.layout_email);
        layout_password = findViewById(R.id.layout_password);
        progressBar = findViewById(R.id.pbLogin);

        edt_email.addTextChangedListener(new Login.ValidationTextWatcher(edt_email));
        edt_password.addTextChangedListener(new Login.ValidationTextWatcher(edt_password));

        //cấp quyền
        String[] PERMISSIONS = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        final ProgressDialog dialog=new ProgressDialog(Login.this);
        dialog.setMessage("Please Waiting !!");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();


        if (authentication.getCurrentUser() != null){
            final String userID= f_authentication.getCurrentUser().getUid();
            try {
                data.child("User").child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        try {
                            User user = dataSnapshot.getValue(User.class);
                            Log.d("abcxyz", String.valueOf(user));
                            int user_roll = user.getUserRoll();
                            if (user_roll == 0) {
                                Intent i = new Intent(Login.this, Bottom_Navigation.class);
                                startActivity(i);
                                finish();
                                dialog.hide();
                            }
                        } catch (Exception e) {
                            Intent i = new Intent(Login.this, Bottom_Navigation_Store_Activity.class);
                            startActivity(i);
                            finish();
                            dialog.hide();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }catch (Exception e){
                data.child("Store").child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Store user = dataSnapshot.getValue(Store.class);
                        Log.d("abcxyz", String.valueOf(user));
                        Intent i = new Intent(Login.this, Bottom_Navigation_Store_Activity.class);
                        startActivity(i);
                        finish();
                        dialog.hide();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

        }else {
            dialog.hide();
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmail() & validatePassword() == true){
                    final String email = edt_email.getText().toString();
                    final String password = edt_password.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    if (email.equals("admin@gmail.com")&&password.equals("admin")){
                        Intent i = new Intent(Login.this, BottomNavigationAdmin.class);
                        startActivity(i);
                        finish();
                    }else {
                        authentication.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Login.this, "Login Successfully !!",
                                                    Toast.LENGTH_SHORT).show();
                                            final String userId = f_authentication.getCurrentUser().getUid();
                                            try {
                                                data.child("User").child(userId).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                        try {
                                                            User user = dataSnapshot.getValue(User.class);
                                                            Log.d("abcxyz", String.valueOf(user));
                                                            int phanquyen = user.getUserRoll();
                                                            if (phanquyen == 0) {
                                                                CheckGpsStatus();
                                                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                                DatabaseReference databaseReference1 = firebaseDatabase.getReference("User");
                                                                databaseReference1.child(userId).child("userPass").setValue(password);
                                                                Intent i = new Intent(Login.this, Bottom_Navigation.class);
                                                                startActivity(i);
                                                                finish();
                                                            }
                                                        } catch (Exception e) {
                                                            Intent i = new Intent(Login.this, Bottom_Navigation_Store_Activity.class);
                                                            startActivity(i);
                                                            finish();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            } catch (Exception e) {
                                                data.child("Store").child(userId).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        Store user = dataSnapshot.getValue(Store.class);
                                                        Log.d("abcxyz", String.valueOf(user));
                                                        Intent i = new Intent(Login.this, Bottom_Navigation_Store_Activity.class);
                                                        startActivity(i);
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    }
                                                });
                                            }


                                        } else {
                                            Toast.makeText(Login.this, "Login Failed !!",
                                                    Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }

                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });
    }
    //check GPS
    public void CheckGpsStatus() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (GpsStatus == true) {
            Intent intent = new Intent(Login.this, Bottom_Navigation.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Turn on your GPS please !!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private boolean validatePassword() {
        if (edt_password.getText().toString().trim().isEmpty()) {
            layout_password.setError("You must type password !!");
            requestFocus(edt_password);
            return false;
        }else if(edt_password.getText().toString().length() < 6){
            layout_password.setError("Password must have 6 characters !!");
            requestFocus(edt_password);
            return false;
        }else {
            layout_password.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateEmail() {
        if (edt_email.getText().toString().trim().isEmpty()) {
            layout_email.setError("You must type Email");
            requestFocus(edt_email);
            return false;
        } else {
            String emailId = edt_email.getText().toString();
            Boolean  isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                layout_email.setError("Email form is not correct !! Ex: abc@example.com");
                requestFocus(edt_email);
                return false;
            } else {
                layout_email.setErrorEnabled(false);
            }
        }
        return true;
    }
    private class ValidationTextWatcher implements TextWatcher {

        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.email:
                    validateEmail();
                    break;
                case R.id.password:
                    validatePassword();
                    break;
            }
        }
    }

}


