package com.dsm.wakeheart;

import com.dsm.wakeheart.Model.UserDTO;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by parktaeim on 2017. 9. 12..
 */

public interface RestAPI {
    @FormUrlEncoded
    @POST("/API/register")
    Call<JsonObject> signUp(@Field("id") String id ,@Field("password") String password, @Field("gender") int gender, @Field("age") int age);


}
