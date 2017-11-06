package com.dsm.wakeheart.Graph;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.dsm.wakeheart.Network.APIUrl.API_BASE_URL;


/**
 * Created by admin on 2017-10-31.
 */

public class APIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if(retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
