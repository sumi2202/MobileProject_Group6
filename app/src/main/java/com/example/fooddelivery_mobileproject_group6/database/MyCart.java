package com.example.fooddelivery_mobileproject_group6.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.fooddelivery_mobileproject_group6.Dish;

import java.util.ArrayList;

public class MyCart {
    private Context context;
    private CartDBHelper cartDBHelper;

    //add a constructor initializing the context and DB helper
    public MyCart(Context context) {
        this.context = context;
        this.cartDBHelper = new CartDBHelper(context);
    }

    //insert dish into the cart database
    public void insertDish(Dish dish) {
        SQLiteDatabase db = cartDBHelper.getWritableDatabase();

        //prepare values to be inserted into the cart table
        ContentValues values = new ContentValues();
        values.put(CartDBHelper.COLUMN_TITLE, dish.getDishName());
        String formattedPrice = dish.getDishPrice().replaceAll("[^\\d.]", "");

        values.put(CartDBHelper.COLUMN_PRICE, dish.getDishPrice());
        values.put(CartDBHelper.COLUMN_QUANTITY, 1); // Initial quantity is set to 1


        //insert values
        long newRowId = db.insert(CartDBHelper.TABLE_NAME, null, values);

        //if added successfully, show toast message
        if (newRowId != -1) {
            Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    //get list of items in the database
    public ArrayList<Dish> getListCart() {
        ArrayList<Dish> listDishes = new ArrayList<>();
        SQLiteDatabase db = cartDBHelper.getReadableDatabase();

        //columns to be retrieved from cart table
        String[] projection = {
                CartDBHelper.COLUMN_TITLE,
                CartDBHelper.COLUMN_PRICE,
                CartDBHelper.COLUMN_QUANTITY
        };

        //perform a query to get data from the cart table
        Cursor cursor = db.query(
                CartDBHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        //iterate through query results, populating listDishes arraylist
        cursor.moveToFirst();
        do {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(CartDBHelper.COLUMN_TITLE));
            String priceString = cursor.getString(cursor.getColumnIndexOrThrow(CartDBHelper.COLUMN_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(CartDBHelper.COLUMN_QUANTITY));

            double price = Double.parseDouble(priceString.replace("$", ""));

            //creating dish object then adding it to the list
            Dish dish = new Dish(title, String.valueOf(price), false); // Change false to appropriate value based on your requirement
            listDishes.add(dish);
        } while (cursor.moveToNext());

        cursor.close();
        db.close();

        //returning list of dishes in cart
        return listDishes;
    }

    //  methods like minusNumberDish, plusNumberDish, getTotalFee.

    //decrease quantity of a dish in the cart
    public void minusNumberDish(ArrayList<Dish> listfood, int position) {
        Dish dish = listfood.get(position);

        if (dish.getNumberinCart() == 1) {
            // Remove the dish from the database
            SQLiteDatabase db = cartDBHelper.getWritableDatabase();
            db.delete(CartDBHelper.TABLE_NAME, CartDBHelper.COLUMN_TITLE + "=?", new String[]{dish.getDishName()});
            db.close();

            // Remove the dish from the list
            listfood.remove(position);
        } else {
            // Decrease the quantity in the database
            SQLiteDatabase db = cartDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CartDBHelper.COLUMN_QUANTITY, dish.getNumberinCart() - 1);
            db.update(
                    CartDBHelper.TABLE_NAME,
                    values,
                    CartDBHelper.COLUMN_TITLE + "=?",
                    new String[]{dish.getDishName()}
            );
            db.close();

            // Decrease the quantity in the list
            dish.setNumberinCart(dish.getNumberinCart() - 1);
        }
    }

    //increase quantity of a dish in the cart
    public void plusNumberDish(ArrayList<Dish> listFoodSelected, int position) {
        Dish dish = listFoodSelected.get(position);
        SQLiteDatabase db = cartDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        int newQuantity = dish.getNumberinCart() + 1;
        values.put(CartDBHelper.COLUMN_QUANTITY, newQuantity);

        db.update(
                CartDBHelper.TABLE_NAME,
                values,
                CartDBHelper.COLUMN_TITLE + "=?",
                new String[]{dish.getDishName()}
        );

        db.close();
        // Update the quantity in the list
        dish.setNumberinCart(newQuantity);
    }

    //get the total fee of the dishes 
    public double getTotalFee() {
        SQLiteDatabase db = cartDBHelper.getReadableDatabase();

        String[] projection = {
                CartDBHelper.COLUMN_PRICE,
                CartDBHelper.COLUMN_QUANTITY
        };

        Cursor cursor = db.query(
                CartDBHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        double totalFee = 0;
        cursor.moveToFirst();

        do {
            double price = Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(CartDBHelper.COLUMN_PRICE)).replace("$", ""));;
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(CartDBHelper.COLUMN_QUANTITY));

            totalFee += price * quantity;
        } while (cursor.moveToNext());

            cursor.close();
        db.close();

        return totalFee;
    }

}
