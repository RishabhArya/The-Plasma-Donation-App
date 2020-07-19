package com.example.jeewan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootCovidStatewiseStats {
    @SerializedName("statewise")
    public List<CovidStateWiseStats> statewiselist;

}