package com.example.jeewan.Model;

import com.example.jeewan.Model.CovidStateWiseStats;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootCovidStatewiseStats {
    @SerializedName("statewise")
    public List<CovidStateWiseStats> statewiselist;

}