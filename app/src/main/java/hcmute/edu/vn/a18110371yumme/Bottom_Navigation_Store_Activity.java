package hcmute.edu.vn.a18110371yumme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import hcmute.edu.vn.a18110371yumme.fragment.StoreActivityFragment;
import hcmute.edu.vn.a18110371yumme.fragment.StoreGoodsFragment;
import hcmute.edu.vn.a18110371yumme.fragment.StoreAccountFragment;
import hcmute.edu.vn.a18110371yumme.fragment.StoreHomeFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Bottom_Navigation_Store_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_store);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_1);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // set fragment home đầu tiên
        if (savedInstanceState == null) {
            StoreHomeFragment gt  = new StoreHomeFragment();
            FragmentManager mn = getSupportFragmentManager();
            mn.beginTransaction()
                    .add(R.id.fragment_1, gt)
                    .commit();
        }

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.Home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new StoreHomeFragment()).commit();
                    return true;
                case R.id.Activity:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new StoreActivityFragment()).commit();
                    return true;
                case R.id.Goods:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new StoreGoodsFragment()).commit();
                    return true;
                case R.id.Account:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_1, new StoreAccountFragment()).commit();
                    return true;

            }
            return false;
        }
    };
}
