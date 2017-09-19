package com.dsm.wakeheart.Server.core;

import android.content.Context;

import com.dsm.wakeheart.Server.core.interceptor.AddCookiesInterceptor;
import com.dsm.wakeheart.Server.core.interceptor.ReceivedCookiesInterceptor;
import com.dsm.wakeheart.Server.resource.APIUrl;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by parktaeim on 2017. 9. 13..
 */

public class APIAdapter {
    protected static Object retrofit(Context context, Class<?> serviceName){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .addNetworkInterceptor(new AddCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(serviceName);
    }
}
