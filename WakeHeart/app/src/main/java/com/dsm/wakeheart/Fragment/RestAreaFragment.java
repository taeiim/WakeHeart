package com.dsm.wakeheart.Fragment;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsm.wakeheart.Adapter.RecyclerViewAdapter;
import com.dsm.wakeheart.Adapter.RestAreaRecyclerViewAdapter;
import com.dsm.wakeheart.GPSinfo;
import com.dsm.wakeheart.Model.RestAreaItem;
import com.dsm.wakeheart.Model.RestAreaItem;
import com.dsm.wakeheart.R;
import com.dsm.wakeheart.RestAPI;
import com.dsm.wakeheart.Server.resource.APIUrl;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAreaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSinfo gps;

    private double latitude, longitude;

    private RecyclerView recyclerView;
    private RestAreaRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RestAreaItem> restAreaItemArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(APIUrl.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RestAPI restAPI = builder.create(RestAPI.class);
        Call<JsonObject> call = restAPI.restArea();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("안뇽!");
                Log.d("rest area response----", response.body().toString());
                Log.d("rest area header----", response.headers().toString());
                Log.d("rest area code----", String.valueOf(response.code()));
                Log.d("rest area message----", response.message().toString());


//                JsonPrimitive jsonObject = response.body().getAsJsonObject().getAsJsonPrimitive("list");
//                Log.d("list jsonObject----",jsonObject.toString());

                try {
//                    jsonAr=jsonObject.getAsJsonArray("phrase");
                    JsonElement object = response.body().getAsJsonPrimitive("list");
                    String str = object.getAsString();
                    JSONObject jsonObject = new JSONObject(str);    // String으로 온 값을 JSON으로 변환
                    JSONArray array = jsonObject.getJSONArray("list");
                    Log.d("json array", array.toString());

                    ArrayList<RestAreaItem> arrayList = new ArrayList<RestAreaItem>();
                    arrayList = getArrayList(array);
                    Log.d("arrayList rest", arrayList.toString());

//                    adapter = new RestAreaRecyclerViewAdapter(getActivity(),arrayList);
//                    recyclerView.setAdapter(adapter);

                    gps = new GPSinfo(getActivity());  // 현재 위도 경도 가져오기
                    Double lat = gps.getLatitude();
                    Double lon = gps.getLongitude();
                    if (lat == 0 || lon == 0) {
                        lat = 36.391603;
                        lon = 127.363082;
                    }
                    math(lat, lon, arrayList);


                } catch (JsonIOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                JsonObject jsonObject2 = jsonObject.getAsJsonObject();
//                JsonPrimitive jsonPrimitive = jsonObject2.getAsJsonPrimitive("list");
//                Log.d("jsonObject2--------",jsonObject2.toString());
//                Log.d("jsonPrimitive--------",jsonPrimitive.toString());

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("실패ㅠㅠ----", t.toString());
            }
        });
    }

    private void setRecyclerView() {
//        Log.d("setRecycler restList",String.valueOf(restAreaItemArrayList.size()));
//        adapter = new RestAreaRecyclerViewAdapter(getActivity(), restAreaItemArrayList);
//        recyclerView.setAdapter(adapter);
    }

    private void math(Double lat, Double lon, ArrayList<RestAreaItem> arrayList) {
        //반경 안에 있는 휴게소 들 restAreaItemArrayList에 저장
        //arrayList 안에는 휴게소, 노선명, 경도 , 위도
        //lat,lon(현재 위치), lat2,lon2(비교할 휴게소 위치)
        //arrayList(모든 휴게소 정보), restAreaItemArrayList(반경 안에 있는 휴게소 들만 저장)

        for (int i = 0; i < arrayList.size()-1; i++) {
            try{
                Log.d("math currentLatitude",lat.toString());
                Log.d("math currentLongitude",lon.toString());
                Log.d("math restAreaname",arrayList.get(i).getRestAreaName().toString());
                Log.d("math getLatitude",arrayList.get(i).getLatitude().toString());
                Log.d("math getLonitude",arrayList.get(i).getLongitude().toString());
                Double lat2 = Double.valueOf(arrayList.get(i).getLatitude());
                Double lon2 = Double.valueOf(arrayList.get(i).getLongitude());

                if(lat2== null || lon2== null){
                    break;
                }

//                double dist = 6371 * Math.acos(
//                        Math.sin(lat) * Math.sin(lat2)
//                                + Math.cos(lat) * Math.cos(lat2) * Math.cos(lon2 - lon));
//
                double theta = lon - lon2;
                double dist = Math.sin(deg2rad(lat)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

                dist = Math.acos(dist);
                dist = rad2deg(dist);
                dist = dist * 60 * 1.1515;
                Log.d("dist------",String.valueOf(dist));

//        if (unit == "kilometer") {
            dist = dist * 1.609344;
//        } else if(unit == "meter"){
//                dist = dist * 1609.344;
                Log.d("km dist------",String.valueOf(dist));
//        }
                if(dist <= 20){  //20 km 안에 있는 휴게소들 restAreaItemArrayList에 저장
                    restAreaItemArrayList.add(new RestAreaItem(arrayList.get(i).restAreaName,arrayList.get(i).getRouteName(),String.valueOf(dist)));
                }
            }catch (NumberFormatException e){

            }


        }
        Log.d("math arrayList------", arrayList.toString());
        Log.d("restAreaItemArrayList--", restAreaItemArrayList.toString());

//        return (restAreaItemArrayList);
        Log.d("setRecycler restList",String.valueOf(restAreaItemArrayList.size()));
        adapter = new RestAreaRecyclerViewAdapter(getActivity(), restAreaItemArrayList);
        recyclerView.setAdapter(adapter);
//        setRecyclerView();
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    private ArrayList<RestAreaItem> getArrayList(JSONArray array) {
        ArrayList<RestAreaItem> arrayList = new ArrayList<>();
        try {
            for (int i = 0; i < array.length()-1; i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String restArea = jsonObject.get("unitName").toString();
                String route = jsonObject.get("routeName").toString();
                String latitude = jsonObject.get("yValue").toString();
                String longitude = jsonObject.get("xValue").toString();


                arrayList.add(new RestAreaItem(restArea, route, latitude, longitude));

                Log.i(arrayList.get(i).getRestAreaName(),arrayList.get(i).getLatitude().toString());
            }
        } catch (Exception e) {
        }
        return arrayList;
    }

//    private void addRestAreaData() {
//        restAreaItemArrayList.add(new RestAreaItem("대전 휴게소","대전"));
//        restAreaItemArrayList.add(new RestAreaItem("대전 휴게소","대전"));
//        restAreaItemArrayList.add(new RestAreaItem("대전 휴게소","대전"));
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_helper_restarea, container, false);

        //지도
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView));
        mapFragment.getMapAsync(this);

        //Setting RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
//
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
//
        recyclerView.scrollToPosition(0);
//        adapter = new RestAreaRecyclerViewAdapter(getActivity(), restAreaItemArrayList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        latitude = 37;
        longitude = 126;
        gps = new GPSinfo(getActivity());

        //현재 위도, 경도 가져오기
        if (gps.isGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if (latitude == 0 || longitude == 0) {
                latitude = 36.391603;
                longitude = 127.363082;
            }
            System.out.println("위도 : " + latitude + " 경도 : " + longitude);
        } else {
            gps.showSettingsAlert();
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), 15));  //Latitude(위도), Longitude(경도)


        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_car_icon))
                .anchor(0.5f, 0.5f)
                .position(new LatLng(latitude, longitude))
                .title("현재 위치"));

    }


}