package com.plasmadonation.jeewan.api;

import com.plasmadonation.jeewan.covidUpdates.RootCovidStatewiseStatsModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetCovidStatewiseData {
    @GET("/data.json")
    Call<RootCovidStatewiseStatsModel> getStatewiseData();
}
