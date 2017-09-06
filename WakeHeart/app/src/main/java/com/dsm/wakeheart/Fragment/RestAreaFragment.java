package com.dsm.wakeheart.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapView;

import com.dsm.wakeheart.R;

/**
 * Created by parktaeim on 2017. 9. 5..
 */

public class RestAreaFragment extends Fragment {
    private NMapContext nMapContext;
    private static final String CLIENT_ID = "oPDj1y9Xk6B_IakJkQ0J";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_helper_restarea,container,false);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nMapContext = new NMapContext(super.getActivity());
        nMapContext.onCreate();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NMapView mapView = (NMapView) getView().findViewById(R.id.mapView);
        mapView.setClientId(CLIENT_ID);
        nMapContext.setupMapView(mapView);
    }

    @Override
    public void onStart() {
        super.onStart();
        nMapContext.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        nMapContext.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        nMapContext.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        nMapContext.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        nMapContext.onDestroy();
        super.onDestroy();
    }
}
