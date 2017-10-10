package com.dsm.wakeheart.Model;

import android.util.Log;

/**
 * Created by parktaeim on 2017. 9. 23..
 */

public class RestAreaItem {
    public String restAreaName;
    public String routeName;
    public String distance;
    public Double latitude;
    public Double longitude;


    public RestAreaItem(String restAreaName, String routeName, String distance, Double latitude, Double longitude) {
        this.restAreaName = restAreaName;
        this.routeName = routeName;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
        double per2 = Double.valueOf(distance);
//        double per = Double.parseDouble(String.format("%.2f",per2));


        String str = String.format("%.2f", per2);
        System.out.println("str = " + str);
        this.distance = str;
        Log.d("round dist",distance);
    }

    public RestAreaItem(String restAreaName, String routeName, Double latitude, Double longitude) {
        this.restAreaName = restAreaName;
        this.routeName = routeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public RestAreaItem(String restAreaName, String routeName, String distance) {
        this.restAreaName = restAreaName;
        this.routeName = routeName;
        this.distance = distance;

//        double per2 = Double.valueOf(distance);
////        double per = Double.parseDouble(String.format("%.2f",per2));
//
//
//        String str = String.format("%.2f", per2);
//        System.out.println("str = " + str);
//        this.distance = str;
//        Log.d("round dist",distance);

    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRestAreaName() {
        return restAreaName;
    }

    public void setRestAreaName(String restAreaName) {
        this.restAreaName = restAreaName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }


}
