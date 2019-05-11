package com.example.assignment7;

public class Place {
    private String location_name;
    private String location_description;
    private String latitude;
    private String longitude;

    public Place(String location_name, String location_description, String latitude, String longitude) {
        this.location_name = location_name;
        this.location_description = location_description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation_name() {
        return location_name;
    }

    public String getLocation_description() {
        return location_description;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public void setLocation_description(String location_description) {
        this.location_description = location_description;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
