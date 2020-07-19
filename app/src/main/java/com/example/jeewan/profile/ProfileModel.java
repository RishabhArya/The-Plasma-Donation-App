package com.example.jeewan.profile;

//public class to storing a user's details
public class ProfileModel {
    private String name;
    private String age;
    private String contact;
    private String city;
    private String state;

    //constructor
    public ProfileModel(String name, String age, String contact, String city, String state) {
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
