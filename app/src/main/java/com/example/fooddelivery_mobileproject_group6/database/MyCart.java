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

    public MyCart(Context context) {
        this.context = context;
        this.cartDBHelper = new CartDBHelper(context);
    }

    public void insertDish(Dish dish) {
        SQLiteDatabase db = cartDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CartDBHelper.COLUMN_TITLE, dish.getDishName());
        values.put(CartDBHelper.COLUMN_PRICE, dish.getDishPrice());
        values.put(CartDBHelper.COLUMN_QUANTITY, 1); // Initial quantity is set to 1

        long newRowId = db.insert(CartDBHelper.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public ArrayList<Dish> getListCart() {
        ArrayList<Dish> listDishes = new ArrayList<>();
        SQLiteDatabase db = cartDBHelper.getReadableDatabase();

        String[] projection = {
                CartDBHelper.COLUMN_TITLE,
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

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(CartDBHelper.COLUMN_TITLE));
            String price = cursor.getString(cursor.getColumnIndexOrThrow(CartDBHelper.COLUMN_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(CartDBHelper.COLUMN_QUANTITY));

            Dish dish = new Dish(title, price, false); // Change false to appropriate value based on your requirement
            listDishes.add(dish);
        }

        cursor.close();
        db.close();

        return listDishes;
    }

    //  methods like minusNumberDish, plusNumberDish, getTotalFee.
    public void minusNumberDish(ArrayList<Dish> listfood, int position, OrderQuantityListener orderQuantityListener) {
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

        orderQuantityListener.changed();
    }


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
    }
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
        while (cursor.moveToNext()) {
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(CartDBHelper.COLUMN_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(CartDBHelper.COLUMN_QUANTITY));

            totalFee += price * quantity;
        }

        cursor.close();
        db.close();

        return totalFee;
    }

}
