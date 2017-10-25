package com.dsm.wakeheart.Network;

import com.dsm.wakeheart.Model.LoginObjectModel;
import com.dsm.wakeheart.Network.APIUrl;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<Void> signUp(@Field("id") String id ,@Field("pw") String pw, @Field("position") int position, @Field("sex") String sex, @Field("age") int age);

    @POST(APIUrl.LOGIN_URL)
    Call<JsonObject> logIn(@Body LoginObjectModel jsonObject);

    @GET(APIUrl.WISE_SAYING_URL)
    Call<JsonObject> wiseSaying();

    @GET(APIUrl.REST_AREA_URL)
    Call<JsonObject> restArea();

}
