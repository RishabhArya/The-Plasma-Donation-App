package com.example.jeewan.api;

import com.example.jeewan.model.RootCovidStatewiseStats;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetCovidStatewiseData {
    @GET("/data.json")
    Call<RootCovidStatewiseStats> getStatewiseData();
}
