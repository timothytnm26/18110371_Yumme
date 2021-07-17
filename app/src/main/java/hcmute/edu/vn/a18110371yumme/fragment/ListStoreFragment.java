package hcmute.edu.vn.a18110371yumme.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.edu.vn.a18110371yumme.DAO.StoreDAO;
import hcmute.edu.vn.a18110371yumme.R;
import hcmute.edu.vn.a18110371yumme.adapter.ShowStoreAdapter;
import hcmute.edu.vn.a18110371yumme.models.Store;

import java.util.ArrayList;


public class ListStoreFragment extends Fragment {
    RecyclerView RecyclerStore;
    StoreDAO StoreDAO;
    ArrayList<Store> list = new ArrayList<>();
    public static ShowStoreAdapter showStoreAdapter;

    public ListStoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_stores, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StoreDAO = new StoreDAO(getActivity());
        RecyclerStore = view.findViewById(R.id.recycler_store);
        LinearLayoutManager place = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerStore.setLayoutManager(place);
        list = StoreDAO.getShowStore();
        showStoreAdapter = new ShowStoreAdapter(list,getActivity());
        RecyclerStore.setAdapter(showStoreAdapter);
        showStoreAdapter.setOnStoreItemClickListener(new ShowStoreAdapter.OnStoreClickListener() {
            @Override
            public void onStoreItemClick(int position) {
                Store store = list.get(position);
                String idStore = store.getStoreID();
                ShowMenuStoreFragment newFragment = new ShowMenuStoreFragment(idStore);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}