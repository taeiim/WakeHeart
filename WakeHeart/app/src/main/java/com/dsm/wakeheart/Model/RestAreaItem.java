package com.dsm.wakeheart.Model;

/**
 * Created by parktaeim on 2017. 9. 23..
 */

public class RestAreaItem {
    public String restAreaName;
    public String routeName;

    public RestAreaItem(String restAreaName, String routeName) {

        this.restAreaName = restAreaName;
        this.routeName = routeName;
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
