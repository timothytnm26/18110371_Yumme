package hcmute.edu.vn.a18110371yumme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;
    Animation topAnimation, botAnimation;
    ImageView imageBG, imageAppName;
    TextView slogan, hcmute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_amination);
        botAnimation = AnimationUtils.loadAnimation(this, R.anim.bot_amination);

        // Hooks
        imageBG = findViewById(R.id.firstBg);
        imageAppName = findViewById(R.id.appName);
        slogan = findViewById(R.id.slogan);
        hcmute = findViewById(R.id.hcmute);

        //Set Amination
        imageBG.setAnimation(topAnimation);
        imageAppName.setAnimation(topAnimation);
        slogan.setAnimation(botAnimation);
        hcmute.setAnimation(botAnimation);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_SCREEN);
    }
}