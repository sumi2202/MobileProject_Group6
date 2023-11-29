package com.example.fooddelivery_mobileproject_group6;

public class RestaurantMod {

    private String branchName;
    private double latitude;
    private double longitude;

    public RestaurantMod() {
    }
    public RestaurantMod(String branchName, double latitude, double longitude){
        this.branchName = branchName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBranchName(){
        return branchName;
    }

    public void setBranchName(String branchName){
        this.branchName = branchName;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
}
