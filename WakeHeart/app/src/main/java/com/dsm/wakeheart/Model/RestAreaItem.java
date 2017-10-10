package com.dsm.wakeheart.Model;

import android.util.Log;

/**
 * Created by parktaeim on 2017. 9. 23..
 */

public class RestAreaItem {
    public String restAreaName;
    public String routeName;
    public String distance;

    public RestAreaItem(String restAreaName, String routeName, String distance, String latitude, String longitude) {
        this.restAreaName = restAreaName;
        this.routeName = routeName;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public RestAreaItem(String restAreaName, String routeName, String latitude, String longitude) {
        this.restAreaName = restAreaName;
        this.routeName = routeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public RestAreaItem(String restAreaName, String routeName, String distance) {
        this.restAreaName = restAreaName;
        this.routeName = routeName;


        double per2 = Double.valueOf(distance);
//        double per = Double.parseDouble(String.format("%.2f",per2));


        String str = String.format("%.2f", per2);
        System.out.println("str = " + str);
        this.distance = str;
        Log.d("round dist",distance);

    }

    public String latitude;
    public String longitude;



    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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
