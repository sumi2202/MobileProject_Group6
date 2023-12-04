package com.example.fooddelivery_mobileproject_group6;

import java.util.ArrayList;
import java.util.Arrays;

public class Dish {
    private String dishId;
    private String dishName;
    private String dishPrice;
    private byte[] image;
    private String resterauntId;

    public int getNumberinCart() {
        return numberinCart;
    }

    private int numberinCart;


    public void setNumberinCart(int numberinCart) {
        this.numberinCart = numberinCart;
    }

    public String getDishId() {
        return dishId;
    }

    public String getResterauntId() {
        return resterauntId;
    }

    public void setResterauntId(String resterauntId) {
        this.resterauntId = resterauntId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getImage() {
        return Arrays.toString(image);
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Dish(String name, String price, boolean online) {
        dishName = name;
        dishPrice = price;
    }

    private static int lastDishId = 0;

    public static ArrayList<Dish> createDishesList(int numOfDishes) {
        ArrayList<Dish> contacts = new ArrayList<Dish>();
        String price = "$";

        for (int i = 1; i <= numOfDishes; i++) {
            contacts.add(new Dish("Menu Item " + ++lastDishId, price + i,i <= numOfDishes / 2));
        }

        return contacts;
    }
}
