package hcmute.edu.vn.a18110371yumme.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import hcmute.edu.vn.a18110371yumme.fragment.StoreGoodsFragment;
import hcmute.edu.vn.a18110371yumme.fragment.ShowMenuStoreFragment;
import hcmute.edu.vn.a18110371yumme.models.Good;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class GoodDAO {
    DatabaseReference mDatabase;
    Context context;
    String GoodID;
    public GoodDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("Good");
        this.context = context;
    }

    public ArrayList<Good> getAll(String idCuaHang) {
        final ArrayList<Good> list = new ArrayList<Good>();
        mDatabase.orderByChild("storeID").equalTo(idCuaHang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ds.getKey();
                    Good hd = ds.getValue(Good.class);
                    list.add(hd);

                }
                StoreGoodsFragment.goodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }


    public ArrayList<Good> getAllGoods() {

        final ArrayList<Good> list = new ArrayList<Good>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Good good = next.getValue(Good.class);
                        list.add(good);
                        StoreGoodsFragment.goodAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }
    public void update(final Good s, String id) {
        mDatabase.child(id).setValue(s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("update", "Update Successfully !!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("update", "Update Failed !!");
            }
        });
    }
    public  void delete( String id){
        mDatabase.child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("delete", "Delete Successfully !!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("delete", "Delete Failed");
            }
        });

    }
    public void insert(Good s) {
        GoodID = mDatabase.push().getKey();
        String goodID = mDatabase.child(GoodID).getKey();
        s.setGoodID(goodID);
        mDatabase.child(GoodID).setValue(s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d("insert", "Insert Successfully !!");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("insert", "Insert Failed !!");
            }
        });
    }

    public ArrayList<Good> getAllByGoodID(String idMonAn) {
        final ArrayList<Good> list = new ArrayList<Good>();
        mDatabase.orderByChild("GoodID").equalTo(idMonAn).addValueEventListener(new ValueEventListener() {
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
