package com.example.fooddelivery_mobileproject_group6;

public class RestaurantMod {

    private String branchName;
    private double latitude;
    private double longitude;

    public RestaurantMod() {            //Empty constructor
    }
    
    /*Constructor that has the branch name, latitude, and longitude as parameters*/
    public RestaurantMod(String branchName, double latitude, double longitude){
        this.branchName = branchName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //Getter for branch name
    public String getBranchName(){
        return branchName;
    }

    //Setter for branch name
    public void setBranchName(String branchName){
        this.branchName = branchName;
    }

    //Getter for latitude 
    public double getLatitude(){
        return latitude;
    }

    //Setter for latitude
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    //Getter for longitude
    public double getLongitude(){
        return longitude;
    }

    //Setter for longitude
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
}
