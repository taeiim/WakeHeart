package com.dsm.wakeheart;

import com.dsm.wakeheart.Server.resource.APIUrl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by parktaeim on 2017. 9. 12..
 */

public interface RestAPI {
    @FormUrlEncoded
    @POST(APIUrl.SIGN_UP_URL)
    Call<JsonObject> signUp(@Field("id") String id ,@Field("password") String password, @Field("gender") int gender, @Field("age") int age);

    @GET(APIUrl.WISE_SAYING_URL)
    Call<JsonObject> wiseSaying();

    @FormUrlEncoded
    @POST(APIUrl.LOGIN_URL)
    Call<JsonObject> logIn(@Field("id") String id ,@Field("password") String password);

    @FormUrlEncoded
    @POST(APIUrl.REST_AREA_URL)
    Call<JsonPrimitive> restArea(@Field("latitude") float latitude, @Field("longitude") float longitude);

}
