package com.dsm.wakeheart;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jieunpark on 2017. 10. 17..
 */

public class DataManager {
    private DataManager(){}

    public static DataManager getDataManager(){
        return dataManager;
    }

    private static DataManager dataManager  = new DataManager();

    private SharedPreferences getPref(Context context){
        SharedPreferences preferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return preferences;
    }

    public void saveData(Context context,String key, String value){
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.remove(key);
        editor.putString(key, value);
        editor.commit();
    }

    public String getData(Context context, String key){
        return getPref(context).getString(key, "");
    }

}
