package hcmute.edu.vn.a18110371yumme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
    Button login, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btlogin);
        signUp = (Button) findViewById(R.id.bt_signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });
    }

    public void openHomePage() {
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
    }

    public void openSignUp() {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }
}
