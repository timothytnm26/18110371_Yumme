package hcmute.edu.vn.a18110371yumme;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import hcmute.edu.vn.a18110371yumme.fragment.TypeFragment;
import hcmute.edu.vn.a18110371yumme.fragment.StoreManagementFragment;
import hcmute.edu.vn.a18110371yumme.fragment.UserManagementFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationAdmin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_admin);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_2);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // set fragment home đầu tiên
        if (savedInstanceState == null) {
            StoreManagementFragment gt  = new StoreManagementFragment();
            FragmentManager mn = getSupportFragmentManager();
            mn.beginTransaction()
                    .add(R.id.fragment_2, gt)
                    .commit();
        }

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.icon_store:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_2, new StoreManagementFragment()).commit();
                    return true;
                case R.id.icon_customers:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_2, new UserManagementFragment()).commit();
                    return true;
                case R.id.icon_goods_type:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_2, new TypeFragment()).commit();
                    return true;

            }
            return false;
        }
    };
}