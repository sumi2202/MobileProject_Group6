package com.example.fooddelivery_mobileproject_group6.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.fooddelivery_mobileproject_group6.Dish;

public class MenuDBMgr {
    private MenuDBHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public MenuDBMgr(Context c) {
        context = c;
    }

    public MenuDBMgr open() throws SQLException {
        dbHelper = new MenuDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Dish dish) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(MenuDBHelper.DISH_NAME,        dish.getDishName());
        contentValue.put(MenuDBHelper.DISH_PRICE,       dish.getDishPrice());
        contentValue.put(MenuDBHelper.IMAGE,            dish.getImage());
        contentValue.put(MenuDBHelper.RESTERAUNT_ID,    dish.getResterauntId());
        database.insert(MenuDBHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { MenuDBHelper.DISH_ID, MenuDBHelper.DISH_NAME, MenuDBHelper.DISH_PRICE, MenuDBHelper.RESTERAUNT_ID, MenuDBHelper.IMAGE };
        Cursor cursor = database.query(MenuDBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(Dish dish) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MenuDBHelper.DISH_NAME,       dish.getDishId());
        contentValues.put(MenuDBHelper.DISH_PRICE,      dish.getDishPrice());
        contentValues.put(MenuDBHelper.RESTERAUNT_ID,   dish.getResterauntId());
        contentValues.put(MenuDBHelper.IMAGE,           dish.getImage());
        int i = database.update(MenuDBHelper.TABLE_NAME, contentValues, MenuDBHelper.DISH_ID + " = " + dish.getDishId(), null);
        return i;
    }

    public void delete(long _id) {
        database.delete(MenuDBHelper.TABLE_NAME, MenuDBHelper.DISH_ID + "=" + _id, null);
    }
}
