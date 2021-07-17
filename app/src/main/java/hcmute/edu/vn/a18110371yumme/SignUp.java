package hcmute.edu.vn.a18110371yumme;

import android.content.Intent;
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

import hcmute.edu.vn.a18110371yumme.DAO.UserDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import hcmute.edu.vn.a18110371yumme.models.User;

public class SignUp extends AppCompatActivity {
    Button btn_signup, btn_login;
    private FirebaseAuth authentication;
    TextInputEditText fullname, phone, email, password, re_password;
    TextInputLayout layout_fullname, layout_phone, layout_email, layout_password, layout_re_password;
    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
    ProgressBar progressBar;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //firebase
        authentication = FirebaseAuth.getInstance();

        //button
        btn_signup = findViewById(R.id.button_signup);
        btn_login = findViewById(R.id.button_signin);

        //editText
        fullname = findViewById(R.id.full_name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        re_password = findViewById(R.id.re_password);

        //layout
        layout_fullname = findViewById(R.id.layout_full_name);
        layout_phone = findViewById(R.id.layout_phone);
        layout_email = findViewById(R.id.layout_email);
        layout_password = findViewById(R.id.layout_password);
        layout_re_password = findViewById(R.id.layout_re_password);

        //progressbar
        progressBar = findViewById(R.id.pbRegister);

        //validation
        phone.addTextChangedListener(new ValidationText(email));
        password.addTextChangedListener(new ValidationText(password));
        re_password.addTextChangedListener(new ValidationText(re_password));

//        if (authentication.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), BottomNavigation.class));
//            finish();
//        }

        btn_signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(validateEmail() & validatePassword() == true){
                final String etxt_email= email.getText().toString();
                final String etxt_password = password.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                authentication.createUserWithEmailAndPassword(etxt_email, etxt_password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.v(etxt_email,etxt_password);
                                    Toast.makeText(SignUp.this, "Sign Up Successfully !!",
                                            Toast.LENGTH_SHORT).show();
                                    userID = authentication.getCurrentUser().getUid();
//                                        String userId = mData.push().getKey();
                                    User s = new User(userID,"","",etxt_password,"", etxt_email,"",0);
                                    data.child("User").child(userID).push();
                                    data.child("User").child(userID).setValue(s);
//                                        KhachHangDAO khachHangDAO = new KhachHangDAO(SignUpActivity.this);
//                                        khachHangDAO.insert(s);
                                    Intent i = new Intent(SignUp.this,Login.class);
                                    startActivity(i);
                                    finish();

                                } else {
                                    Log.v(etxt_email, etxt_password);
                                    Toast.makeText(SignUp.this, "Please type email form correctly !! And password with 6 characters.",
                                            Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }

        }
    });
}

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatePassword() {
        if (password.getText().toString().trim().isEmpty()) {
            layout_password.setError("You must type password !");
            requestFocus(password);
            return false;
        }else if(password.getText().toString().length() < 6){
            layout_password.setError("Password must have 6 characters");
            requestFocus(password);
            return false;
        }else {
            layout_password.setErrorEnabled(false);
        }
        return true;
    }
    private  boolean validateRePassword(){
        String etxt_password = password.getText().toString();
        String etxt_re_password = re_password.getText().toString();
        if (re_password.getText().toString().trim().isEmpty()) {
            layout_re_password.setError("Type password again !!");
            requestFocus(re_password);
            return false;
        }
        else if (etxt_password.equals(etxt_password)){
            layout_re_password.setErrorEnabled(false);
            return true;
        }else {
            layout_re_password.setError("Password is not matched !!");
            requestFocus(re_password);

        }
        return false;
    }

    private boolean validateEmail() {
        if (email.getText().toString().trim().isEmpty()) {
            layout_email.setError("You must type email !!");
            requestFocus(email);
            return false;
        } else {
            String emailId = email.getText().toString();
            Boolean  isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                layout_email.setError("Email form is not correct !! Ex: abc@example.com");
                requestFocus(email);
                return false;
            } else {
                layout_email.setErrorEnabled(false);
            }
        }
        return true;
    }
private class ValidationText implements TextWatcher {

    private View view;

    private ValidationText(View view) {
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
            case R.id.re_password:
                validateRePassword();
                break;
        }
    }
}







}