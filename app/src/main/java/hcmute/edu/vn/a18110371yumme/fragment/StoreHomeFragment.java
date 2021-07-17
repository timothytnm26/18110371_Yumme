package hcmute.edu.vn.a18110371yumme.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.adapter.CategoriesAdapter1;
import hcmute.edu.vn.a18110371yumme.adapter.PlaceAdapterStore;
import hcmute.edu.vn.a18110371yumme.adapter.SliderAdapter1;
import hcmute.edu.vn.a18110371yumme.models.Category;

public class StoreHomeFragment extends Fragment {
    SliderView sliderView;
    RecyclerView rcvCategories, rcvQuanGoiY;
    PlaceAdapterStore placeAdapter;
    public static CategoriesAdapter1 categoriesAdapter;
    ArrayList<Category> list = new ArrayList<>();
    public StoreHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_home, container, false);

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sliderView = view.findViewById(R.id.imgSlider);
        rcvCategories = (RecyclerView)view.findViewById(R.id.trending_recycler_view);
        LinearLayoutManager llmTrending = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rcvCategories.setLayoutManager(llmTrending);
        categoriesAdapter = new CategoriesAdapter1(getActivity());
        rcvCategories.setAdapter(categoriesAdapter);
        rcvQuanGoiY = view.findViewById(R.id.place_recycler_view);
        LinearLayoutManager place = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvQuanGoiY.setLayoutManager(place);
        placeAdapter = new PlaceAdapterStore(getActivity());
        rcvQuanGoiY.setAdapter(placeAdapter);
        //custom slider
        SliderAdapter1 adapter = new SliderAdapter1(getActivity());
        sliderView.setSliderAdapter(adapter);
        //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setIndicatorAnimation(IndicatorAnimations.FILL); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINDEPTHTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }
}