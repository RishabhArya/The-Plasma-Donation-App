package com.example.jeewan.profile;

public class ProfileModel {
    String name;
    String age;
    String contact;
    String city;
    String state;

    public ProfileModel() {

    }

    public ProfileModel(String age, String city, String contact, String name, String state) {
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.city = city;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
