package hcmute.edu.vn.a18110371yumme.DAO;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import hcmute.edu.vn.a18110371yumme.adapter.StoreAdapter_temp;
import hcmute.edu.vn.a18110371yumme.fragment.ListStoreFragment;
import hcmute.edu.vn.a18110371yumme.fragment.StoreManagementFragment;
import hcmute.edu.vn.a18110371yumme.fragment.StoreManagementFragment;
import hcmute.edu.vn.a18110371yumme.models.Store;
import hcmute.edu.vn.a18110371yumme.models.Store_temp;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static hcmute.edu.vn.a18110371yumme.fragment.HomeFragment.StoreAdapter_temp;

public class StoreDAO implements LocationListener {
    DatabaseReference mDatabase;
    Context context;
    String CuaHangID;
    List<Store_temp> temp = new ArrayList<>();
    public StoreDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("Store");
        this.context = context;
    }

    //đo khoảng cách giữa hai điểm
    public static double distanceBetween2Points(double la1, double lo1, double la2, double lo2) {
        double dLat = (la2 - la1) * (Math.PI / 180);
        double dLon = (lo2 - lo1) * (Math.PI / 180);
        double la1ToRad = la1 * (Math.PI / 180);
        double la2ToRad = la2 * (Math.PI / 180);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(la1ToRad) * Math.cos(la2ToRad) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6400 * c;
        return d;
    }

    public ArrayList<Store> getAll() {

        final ArrayList<Store> list = new ArrayList<Store>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Store store = next.getValue(Store.class);
                        list.add(store);
                       StoreManagementFragment.storeAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }
    public ArrayList<Store> getShowStore() {
        final ArrayList<Store> list = new ArrayList<Store>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Store store = next.getValue(Store.class);
                        list.add(store);
                        ListStoreFragment.showStoreAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public void update(final Store s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("storeID").getValue(String.class).equalsIgnoreCase(s.getStoreID())) {
                        CuaHangID = data.getKey();
                        Log.d("getKey", "onCreate: key :" + CuaHangID);
                        mDatabase.child(CuaHangID).setValue(s)
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

    //get Store within 10km
    @SuppressLint("MissingPermission")
    public List<Store_temp> getTemp(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Permission already Granted
            //Do your work here
            //Perform operations here only which requires permission
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);
        @SuppressLint("MissingPermission") final Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                temp.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    //convert ra đối tượng Order
                    Store store = data.getValue(Store.class);
                    try{
                        if (location != null) {
                            double distance = distanceBetween2Points(location.getLatitude(), location.getLongitude(), store.getStoreLatitude(), store.getStoreLongitude());
                            if (distance > 0) {
                                temp.add(new Store_temp(
                                        store.getStoreID(),
                                        store.getStoreName(),
                                        store.getStoreAddress(),
                                        store.getStoreRate(),
                                        store.getStoreImage(),
                                        store.getStoreLatitude(),
                                        store.getStoreLongitude(),
                                        distance
                                ));
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                //sort khoảng cách đến quán ăn theo thứ tự tăng dần
                Collections.sort(temp, new Comparator<Store_temp>() {
                    @Override
                    public int compare(Store_temp o1, Store_temp o2) {
                        return Double.valueOf(o1.getStoreDistance()).compareTo(o2.getStoreDistance());
                    }
                });

                StoreAdapter_temp.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return temp;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
