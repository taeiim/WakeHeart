package com.dsm.wakeheart.Server.core.interceptor;

import android.content.Context;
import android.util.Log;

import com.dsm.wakeheart.Server.core.preferences.CookieSharedPreferences;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by parktaeim on 2017. 9. 13..
 */
public class AddCookiesInterceptor implements Interceptor {
    // CookieSharedReferences 객체
    private CookieSharedPreferences cookieSharedPreferences;

    /**
     * 생성자
     *
     * @param context
     */
    public AddCookiesInterceptor(Context context){
        // CookieSharedReferences 객체 초기화
        cookieSharedPreferences = CookieSharedPreferences.getInstanceOf(context);
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        // 가져온 chain으로 부터 빌더 객체를 생성
        Request.Builder builder = chain.request().newBuilder();
        Log.d("request builder------",builder.toString());
        Log.d("request method--------",chain.request().method());
        Log.d("request header--------",chain.request().headers().toString());
        Log.d("request connection-----",chain.connection().toString());

        // CookieSharedPreferences에 저장되어있는 쿠키 값을 가져옴
        HashSet<String> cookies = (HashSet) cookieSharedPreferences.getHashSet(
                CookieSharedPreferences.COOKIE_SHARED_PREFERENCES_KEY,
                new HashSet<String>()
        );

        Log.d("cookies hashset-------",cookies.toString());   //[user=id]

        // 빌더 헤더 영역에 쿠키 값 추가
        for (String cookie : cookies) {
            builder.addHeader("Cookie", cookie);
            Log.d("cookie-----",cookie);
        }


        // 체인에 빌더를 적용 및 반환
        return chain.proceed(builder.build());
    }
}