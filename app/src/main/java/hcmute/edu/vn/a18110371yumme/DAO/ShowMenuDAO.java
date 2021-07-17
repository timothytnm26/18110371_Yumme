package hcmute.edu.vn.a18110371yumme.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import hcmute.edu.vn.a18110371yumme.fragment.ShowMenuStoreFragment;
import hcmute.edu.vn.a18110371yumme.models.Good;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowMenuDAO {
    DatabaseReference mDatabase;
    Context context;
    String StoreID;
    public ShowMenuDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("Good");
        this.context = context;
    }

    public ArrayList<Good> getGoodsByStoreID(String StoreID) {
        final ArrayList<Good> list = new ArrayList<Good>();
        mDatabase.orderByChild("storeID").equalTo(StoreID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ds.getKey();
                    Good good = ds.getValue(Good.class);
                    Log.d("ab1", good.getGoodID());
                    list.add(good);

                }
                ShowMenuStoreFragment.showMenuStoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }
}
