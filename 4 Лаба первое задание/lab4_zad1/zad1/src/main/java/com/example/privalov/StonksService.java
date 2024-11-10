package com.example.privalov;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StonksService {

    @GET("/scripts/XML_daily.asp")
    Call<DailyCurs> getDailyCurs(@Query("date_req") String date);
}
