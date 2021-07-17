package hcmute.edu.vn.a18110371yumme;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import hcmute.edu.vn.a18110371yumme.fragment.OrdersFragment;
import hcmute.edu.vn.a18110371yumme.fragment.CartFragment;
import hcmute.edu.vn.a18110371yumme.fragment.HomeFragment;
import hcmute.edu.vn.a18110371yumme.fragment.ListStoreFragment;
import hcmute.edu.vn.a18110371yumme.fragment.SettingFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Bottom_Navigation extends AppCompatActivity {
    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // set fragment home đầu tiên
        if (savedInstanceState == null) {
            HomeFragment gt  = new HomeFragment();
            FragmentManager mn = getSupportFragmentManager();
            mn.beginTransaction()
                    .add(R.id.frame_layout, gt)
                    .commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.ic_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                    return true;
                case R.id.ic_star:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ListStoreFragment()).commit();
                    return true;
                case R.id.ic_bill:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new OrdersFragment()).commit();
                    return true;
                case R.id.ic_cart:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CartFragment()).commit();
                    return true;
                case R.id.ic_setting:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new SettingFragment()).commit();
                    return true;
            }
            return false;
        }
    };
}
