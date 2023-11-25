package com.example.fooddelivery_mobileproject_group6;

import java.util.ArrayList;

public class Dish {
    private String dishName;
    private String dishPrice;
    private boolean mOnline;

    public Dish(String name, String price, boolean online) {
        dishName = name;
        dishPrice = price;
        mOnline = online;
    }

    public String getName() {
        return dishName;
    }

    public String getPrice() {
        return dishPrice;
    }

    public boolean isOnline() {
        return mOnline;
    }

    private static int lastDishId = 0;

    public static ArrayList<Dish> createContactsList(int numContacts) {
        ArrayList<Dish> contacts = new ArrayList<Dish>();
        String price = "$";

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Dish("Menu Item " + ++lastDishId, price + i,i <= numContacts / 2));
        }

        return contacts;
    }
}
