package com.plasmadonation.jeewan.request;

//class to describe request data attributes
public class RequestModel {
    private String name;
    private String req_type;
    private String blood_group;
    private String amount;
    private String date;
    private String hospital_name;
    private String city;
    private String phone_number;
    private String description;

    public RequestModel() {
    }

    //constructor
    public RequestModel(String amount, String blood_group, String city, String date, String description, String hospital_name, String name,
                        String phone_number, String req_type) {
        this.name = name;
        this.req_type = req_type;
        this.blood_group = blood_group;
        this.amount = amount;
        this.date = date;
        this.hospital_name = hospital_name;
        this.city = city;
        this.phone_number = phone_number;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReq_type() {
        return req_type;
    }

    public void setReq_type(String req_type) {
        this.req_type = req_type;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
