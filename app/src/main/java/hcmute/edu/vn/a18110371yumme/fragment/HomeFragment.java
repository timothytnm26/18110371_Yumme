package hcmute.edu.vn.a18110371yumme.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.a18110371yumme.DAO.StoreDAO;
import hcmute.edu.vn.a18110371yumme.DAO.TypeDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.adapter.CategoriesAdapter;
import hcmute.edu.vn.a18110371yumme.adapter.PlaceAdapter;
import hcmute.edu.vn.a18110371yumme.adapter.SliderAdapter;
import hcmute.edu.vn.a18110371yumme.adapter.StoreAdapter_temp;
import hcmute.edu.vn.a18110371yumme.models.Store_temp;
import hcmute.edu.vn.a18110371yumme.models.Type;


public class HomeFragment extends Fragment implements LocationListener {
    SliderView SliderView;
    RecyclerView RcvCategories;
    ListView RcvStoreRecommend;
    public static StoreAdapter_temp StoreAdapter_temp;
    List<Store_temp> temp = new ArrayList<>();
    PlaceAdapter PlaceAdapter;
    public static CategoriesAdapter CategoriesAdapter;
    ArrayList<Type> list = new ArrayList<>();
    boolean GpsStatus;
    TypeDAO TypeDAO = new TypeDAO(getActivity());
    ImageView Reload;
    LocationManager LocationManager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);

     return view;
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Permission already Granted
            //Do your work here
            //Perform operations here only which requires permission
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);
        final Location location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        SliderView = view.findViewById(R.id.imgSlider);
        RcvCategories = (RecyclerView)view.findViewById(R.id.trending_recycler_view);
        RcvStoreRecommend = view.findViewById(R.id.place_recycler_view);
        Reload = view.findViewById(R.id.btn_reload);
        LinearLayoutManager llmTrending = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RcvCategories.setLayoutManager(llmTrending);
        list = TypeDAO.getAllMenu();
        CategoriesAdapter = new CategoriesAdapter(list,getActivity());
        RcvCategories.setAdapter(CategoriesAdapter);

        //custom slider
        SliderAdapter adapter = new SliderAdapter(getActivity());
        SliderView.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        SliderView.setIndicatorAnimation(IndicatorAnimations.FILL); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        SliderView.setSliderTransformAnimation(SliderAnimations.CUBEINDEPTHTRANSFORMATION);
        SliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        SliderView.setIndicatorSelectedColor(Color.WHITE);
        SliderView.setIndicatorUnselectedColor(Color.GRAY);
        SliderView.setScrollTimeInSec(3);
        SliderView.setAutoCycle(true);
        SliderView.startAutoCycle();

        Reload.setVisibility(View.INVISIBLE);
        getTemp();
        if (location == null) {
            Reload.setVisibility(View.VISIBLE);
        }

        Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                GpsStatus = LocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (GpsStatus == true) {
                    getTemp();
                    Reload.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getActivity(), "Turn on your GPS please !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        StoreAdapter_temp.setOnStoreTempItemClickListener(new StoreAdapter_temp.OnStoreTempClickListener() {
            @Override
            public void onCuaHangGanItemClick(int position) {
                Store_temp storeTemp = temp.get(position);
                String idStore = storeTemp.getStoreID();
                ShowMenuStoreFragment newFragment = new ShowMenuStoreFragment(idStore);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


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

    public void getTemp() {
        StoreDAO storeDAO = new StoreDAO(getActivity());
        temp = storeDAO.getTemp(getActivity());
        StoreAdapter_temp = new StoreAdapter_temp(getActivity(), R.layout.item_store_temp, temp);
        RcvStoreRecommend.setAdapter(StoreAdapter_temp);
        Log.d("size","temp: "+temp.size());
    }
}