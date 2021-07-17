package hcmute.edu.vn.a18110371yumme.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.models.Store_temp;


public class StoreAdapter_temp extends ArrayAdapter<Store_temp> {
    Activity context;
    int resource;
    List<Store_temp> objects;

    private OnStoreTempClickListener mListener;

    public void setOnStoreTempItemClickListener (OnStoreTempClickListener onStoreItemClickListener){
        mListener = onStoreItemClickListener;
    }

    public StoreAdapter_temp(Activity context, int resource, List<Store_temp> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = this.context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_store_temp, null);
            viewHolder.StoreImage = convertView.findViewById(R.id.store_image);
            viewHolder.StoreName = convertView.findViewById(R.id.txt_store_name);
            viewHolder.StoreAddress = convertView.findViewById(R.id.txt_store_address);
            viewHolder.StoreRate = convertView.findViewById(R.id.txt_rating);
            viewHolder.StoreDistance = convertView.findViewById(R.id.txt_distance);
            viewHolder.LinearLayoutStore = convertView.findViewById(R.id.item_store_temp);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Store_temp storeTemp = this.objects.get(position);
        if (storeTemp.getStoreName().length() > 20) {
            viewHolder.StoreName.setText(storeTemp.getStoreName().substring(0, 20) + " ...");
        } else {
            viewHolder.StoreName.setText(storeTemp.getStoreName());
        }
        if (storeTemp.getStoreAddress().length() > 30) {
            viewHolder.StoreAddress.setText(storeTemp.getStoreAddress().substring(0, 30) + " ...");
        } else {
            viewHolder.StoreAddress.setText(storeTemp.getStoreAddress());
        }
        viewHolder.StoreRate.setText(String.valueOf(storeTemp.getStoreRate()));
        try {
            Picasso.with(context).load(storeTemp.getStoreImage()).into(viewHolder.StoreImage);
        } catch (Exception e) {

        }
        viewHolder.LinearLayoutStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCuaHangGanItemClick(position);
            }
        });

        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        @SuppressLint("MissingPermission") Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            viewHolder.StoreDistance.setText(distanceBetween2Points(location.getLatitude(), location.getLongitude(), storeTemp.getStoreLatitude(), storeTemp.getStoreLongitude()) + " km");
        }
        return convertView;
    }


    static class ViewHolder implements OnStoreTempClickListener {
        ImageView StoreImage;
        TextView StoreName, StoreAddress, StoreRate, StoreDistance;
        LinearLayout LinearLayoutStore;

        @Override
        public void onCuaHangGanItemClick(int position) {

        }
    }

    public static String distanceBetween2Points(double la1, double lo1, double la2, double lo2) {
        double dLat = (la2 - la1) * (Math.PI / 180);
        double dLon = (lo2 - lo1) * (Math.PI / 180);
        double la1ToRad = la1 * (Math.PI / 180);
        double la2ToRad = la2 * (Math.PI / 180);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(la1ToRad) * Math.cos(la2ToRad) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6400 * c;

        //format number
        NumberFormat formatter = new DecimalFormat("#0.0");
        return formatter.format(d);
    }
    public interface OnStoreTempClickListener {
        void onCuaHangGanItemClick(int position);
//        void onPlaceFavoriteClick(Place place);
    }
}
