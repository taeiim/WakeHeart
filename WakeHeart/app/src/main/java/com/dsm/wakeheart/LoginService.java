package com.dsm.wakeheart;

import android.content.Context;

import com.dsm.wakeheart.Server.core.APIAdapter;
import com.dsm.wakeheart.Server.resource.APIUrl;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by parktaeim on 2017. 10. 2..
 */

public class LoginService extends APIAdapter {

    public static LoginAPI getRetrofit(Context context){
        return (LoginAPI) retrofit(context,LoginAPI.class);
    }

    public interface LoginAPI{
        @FormUrlEncoded
        @POST(APIUrl.LOGIN_URL)
        Call<JsonObject> logIn(@Field("id") String id , @Field("password") String password);

    }
}
