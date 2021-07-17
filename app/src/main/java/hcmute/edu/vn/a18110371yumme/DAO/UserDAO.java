package hcmute.edu.vn.a18110371yumme.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import hcmute.edu.vn.a18110371yumme.fragment.StoreManagementFragment;
import hcmute.edu.vn.a18110371yumme.fragment.UserManagementFragment;
import hcmute.edu.vn.a18110371yumme.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class UserDAO {
    DatabaseReference mDatabase;
    Context context;
    String UserID;

    public UserDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("User");
        this.context = context;
    }


    public ArrayList<User> getAll() {
        final ArrayList<User> list = new ArrayList<User>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        User user = next.getValue(User.class);
                        list.add(user);
                        UserManagementFragment.userAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public void update(final User s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("userMail").getValue(String.class).equalsIgnoreCase(s.getUserEmail())) {
                        UserID = data.getKey();
                        Log.d("getKey", "onCreate: key :" + UserID);
                        mDatabase.child(UserID).setValue(s)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("update", "Update Successfully !!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("update", "Update Failed !!");
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

