package hcmute.edu.vn.a18110371yumme.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import hcmute.edu.vn.a18110371yumme.fragment.HomeFragment;
import hcmute.edu.vn.a18110371yumme.fragment.TypeFragment;
import hcmute.edu.vn.a18110371yumme.models.Type;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class TypeDAO {
    DatabaseReference mDatabase;
    Context context;
    String TypeID;
    public TypeDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("PhanLoai");
        this.context = context;
    }

    public void insert(Type s) {
        TypeID = mDatabase.push().getKey();
        String type = mDatabase.child(TypeID).getKey();
        s.setTypeID(type);
        mDatabase.child(TypeID).setValue(s)
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


    public ArrayList<Type> getAll() {
        final ArrayList<Type> list = new ArrayList<Type>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Type type = next.getValue(Type.class);
                        list.add(type);
                        TypeFragment.typeAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public ArrayList<Type> getAllMenu() {
        final ArrayList<Type> list = new ArrayList<Type>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Type type = next.getValue(Type.class);
                        list.add(type);
                        HomeFragment.CategoriesAdapter.notifyDataSetChanged();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }


    public ArrayList<Type> getAllspn() {
        final ArrayList<Type> list = new ArrayList<Type>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Type type = next.getValue(Type.class);
                        list.add(type);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public void delete(final Type s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("typeID").getValue(String.class).equalsIgnoreCase(s.getTypeID())){
                        TypeID = data.getKey();
                        Log.d("getKey", "onCreate: key :" + TypeID);
                        mDatabase.child(TypeID).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        TypeFragment.typeAdapter.notifyDataSetChanged();
                                        Log.d("delete","Delete Successfully !!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("delete","Delete Failed !!");
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
    public void update(final Type s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("typeID").getValue(String.class).equalsIgnoreCase(s.getTypeID())) {
                        TypeID = data.getKey();
                        Log.d("getKey", "onCreate: key :" + TypeID);
                        TypeFragment.typeAdapter.notifyDataSetChanged();
                        mDatabase.child(TypeID).setValue(s)
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
