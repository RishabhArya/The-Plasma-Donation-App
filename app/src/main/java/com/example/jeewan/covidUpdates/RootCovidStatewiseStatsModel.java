package com.example.jeewan.covidUpdates;

import com.example.jeewan.covidUpdates.CovidStateWiseStatsModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootCovidStatewiseStatsModel {
    @SerializedName("statewise")
    public List<CovidStateWiseStatsModel> statewiselist;

}