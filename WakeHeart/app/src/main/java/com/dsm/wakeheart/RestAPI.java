package com.dsm.wakeheart;

import com.dsm.wakeheart.Model.UserDTO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by parktaeim on 2017. 9. 12..
 */

public interface RestAPI {
    @POST("/API/register")
//    Call<Boolean> signUp(@Field("id") String id ,@Field("password") String password, @Field("gender") int gender, @Field("age") int age);    Call<Boolean> signUp(@Field("id") String id ,@Field("password") String password, @Field("gender") int gender, @Field("age") int age);
    Call<Boolean> signUp(@Body String id, String password, int gender, int age);


}
