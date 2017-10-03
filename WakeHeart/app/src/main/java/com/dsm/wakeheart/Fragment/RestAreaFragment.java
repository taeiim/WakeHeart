package com.dsm.wakeheart.Fragment;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsm.wakeheart.Adapter.RecyclerViewAdapter;
import com.dsm.wakeheart.Adapter.RestAreaRecyclerViewAdapter;
import com.dsm.wakeheart.GPSinfo;
import com.dsm.wakeheart.Model.RestAreaItem;
import com.dsm.wakeheart.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class RestAreaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSinfo gps;

    private RecyclerView recyclerView;
    private RestAreaRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RestAreaItem> restAreaItemArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addRestAreaData();
    }

    private void addRestAreaData() {
        restAreaItemArrayList.add(new RestAreaItem("대전 휴게소","대전"));
        restAreaItemArrayList.add(new RestAreaItem("대전 휴게소","대전"));
        restAreaItemArrayList.add(new RestAreaItem("대전 휴게소","대전"));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_helper_restarea,container,false);

        //지도
        SupportMapFragment mapFragment = ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapView));
        mapFragment.getMapAsync(this);

        //Setting RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.scrollToPosition(0);
        adapter = new RestAreaRecyclerViewAdapter(getActivity(), restAreaItemArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double latitude =37;
        double longitude=126;
        gps = new GPSinfo(getActivity());

        //현재 위도, 경도 가져오기
        if(gps.isGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if(latitude==0 || longitude ==0){
                latitude = 36.391603;
                longitude= 127.363082;
            }
            System.out.println("위도 : "+latitude+ " 경도 : "+longitude);
        }else{
            gps.showSettingsAlert();
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude,longitude),15));  //Latitude(위도), Longitude(경도)


        mMap.addMarker(new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_car_icon))
            .anchor(0.5f,0.5f)
            .position(new LatLng(latitude,longitude))
            .title("현재 위치")
            .snippet("나는 지금 여기에!"));

    }
}