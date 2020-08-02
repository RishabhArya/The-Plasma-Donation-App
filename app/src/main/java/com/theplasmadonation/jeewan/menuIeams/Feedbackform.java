package com.theplasmadonation.jeewan.menuIeams;

public class Feedbackform {
    private String Rating;
    private String message;

    public Feedbackform(String rating, String message) {
        Rating = rating;
        this.message = message;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}