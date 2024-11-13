package com.googleMapScapper.googleMapScapper.DTO;

public class NearbyPlace {
    private String name;
    private double latitude;
    private double longitude;

    public NearbyPlace(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public NearbyPlace() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // @Override
    // public String toString() {
    // return "{" +
    // "name='" + name + ", latitude=" + latitude +
    // ", longitude=" + longitude +
    // '}';
    // }
    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
