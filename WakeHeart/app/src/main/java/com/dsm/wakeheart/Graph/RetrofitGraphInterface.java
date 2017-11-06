package com.dsm.wakeheart.Graph;

import com.dsm.wakeheart.Network.APIUrl;
import com.google.gson.JsonPrimitive;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by admin on 2017-10-08.
 */

public interface RetrofitGraphInterface {
    @FormUrlEncoded
    @POST(APIUrl.SEND_HEARTRATE)
    Call<JsonPrimitive> updateData(
            @Header("Authorization") String authorization,
            @Field("rate") int rate
    );


    //    @Headers({"Authorization: JWT " + tempToken})
    @GET(APIUrl.GET_HEARTRATE)
    Call<List<MyHeartRateItem>> getData(
            @Header("Authorization") String authorization,
            @Query("start_date") String start_date,
            @Query("end_date") String end_date
    );

    @GET(APIUrl.A_RATE)
    Call<JsonPrimitive> getAData(
            @Header("Authorization") String authorization,
            @Query("date") String date
    );
}
