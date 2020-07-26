package com.plasmadonation.jeewan.covidUpdates;

import com.google.gson.annotations.SerializedName;

//class to confirm json data into java object with getter and setter methods
public class CovidStateWiseStatsModel {
    @SerializedName("active")
    private String active;
    @SerializedName("confirmed")
    private String confirmed;
    @SerializedName("deaths")
    private String deaths;
    @SerializedName("deltaconfirmed")
    private String deltaconfirmed;
    @SerializedName("deltadeaths")
    private String deltadeaths;
    @SerializedName("deltarecovered")
    private String deltarecovered;
    @SerializedName("lastupdatedtime")
    private String lastupdatedtime;
    @SerializedName("migratedother")
    private String migratedother;
    @SerializedName("recovered")
    private String recovered;
    @SerializedName("state")
    private String state;
    @SerializedName("statecode")
    private String statecode;
    @SerializedName("statenotes")
    private String statenotes;

    public CovidStateWiseStatsModel(String active, String confirmed, String deaths, String deltaconfirmed,
                                    String deltadeaths, String deltarecovered, String lastupdatedtime, String migratedother,
                                    String recovered, String state, String statecode, String statenotes) {
        this.active = active;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.deltaconfirmed = deltaconfirmed;
        this.deltadeaths = deltadeaths;
        this.deltarecovered = deltarecovered;
        this.lastupdatedtime = lastupdatedtime;
        this.migratedother = migratedother;
        this.recovered = recovered;
        this.state = state;
        this.statecode = statecode;
        this.statenotes = statenotes;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }
}
