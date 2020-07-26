package com.plasmadonation.jeewan.covidUpdates;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootCovidStatewiseStatsModel {
    @SerializedName("statewise")
    public List<CovidStateWiseStatsModel> statewiselist;

}