package com.thoughtsquare.domain;

public class LocationEvent {
    private String title;
    private String message;

    public LocationEvent(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }


    public String getMessage() {
        return message;
    }
}
