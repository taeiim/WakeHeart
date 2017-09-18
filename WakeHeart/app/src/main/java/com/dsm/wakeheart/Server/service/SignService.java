package com.dsm.wakeheart.Server.service;

import android.content.Context;

import com.dsm.wakeheart.Server.core.APIAdapter;
import com.dsm.wakeheart.Server.resource.APIUrl;
import com.dsm.wakeheart.Server.response.ResData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by parktaeim on 2017. 9. 13..
 */

public class SignService extends APIAdapter{
    public static SignAPI getRetrofit(Context context) {
        // 현재 서비스객체의 이름으로 Retrofit 객체를 초기화 하고 반환
        return (SignAPI)retrofit(context, SignAPI.class);
    }

    // SignAPI 인터페이스
    public interface SignAPI {
        /**
         * 회원가입 메소드
         *
         * @param id
         * @param pw
         * @param gender
         * @param age
         * @return
         */
        @FormUrlEncoded
        @POST(APIUrl.SIGN_UP_URL)
        Call<JsonObject> up(
                @Field("id") String id,   // 변수 이름 , 보낼 변수
                @Field("pw") String pw,
                @Field("gender") int gender,
                @Field("age") int age
        );

        /**
         * 로그인 메소드
         *
         * @param id
         * @param pw
         * @return
         */
        @FormUrlEncoded
        @POST(APIUrl.SIGN_IN_URL)
        Call<ResData> in(
                @Field("id") String id,
                @Field("pw") String pw
        );
    }
}
