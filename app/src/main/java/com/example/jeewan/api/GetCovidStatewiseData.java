package com.example.jeewan.api;

import com.example.jeewan.covidUpdates.RootCovidStatewiseStatsModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetCovidStatewiseData {
    @GET("/data.json")
    Call<RootCovidStatewiseStatsModel> getStatewiseData();
}
