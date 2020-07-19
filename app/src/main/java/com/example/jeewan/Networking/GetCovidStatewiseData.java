package com.example.jeewan.Networking;

import com.example.jeewan.Model.RootCovidStatewiseStats;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetCovidStatewiseData {
    @GET("/data.json")
    Call<RootCovidStatewiseStats> getStatewiseData();
}
