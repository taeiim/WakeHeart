package com.dsm.wakeheart;

import com.dsm.wakeheart.Network.RestAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by parktaeim on 2017. 9. 12..
 */

public class RestRequestHelper {
    private static final String API_URL = "http://esplay.xyz:21218/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static RestAPI getRetrofit(){
        return retrofit.create(RestAPI.class);
    }
}
