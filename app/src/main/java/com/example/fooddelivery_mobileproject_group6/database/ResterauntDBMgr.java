package com.example.fooddelivery_mobileproject_group6.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.fooddelivery_mobileproject_group6.Dish;

public class ResterauntDBMgr {
    private ResterauntDBHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public ResterauntDBMgr(Context c) {
        context = c;
    }

    public ResterauntDBMgr open() throws SQLException {
        dbHelper = new ResterauntDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Dish dish) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(ResterauntDBHelper.RESTERAUNT_ID,    dish.getDishName());
        contentValue.put(ResterauntDBHelper.RESTERAUNT_NAME,  dish.getDishPrice());
        contentValue.put(ResterauntDBHelper.IMAGE,            dish.getImage());
        database.insert(ResterauntDBHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { ResterauntDBHelper.RESTERAUNT_ID, ResterauntDBHelper.RESTERAUNT_NAME, ResterauntDBHelper.IMAGE };
        Cursor cursor = database.query(ResterauntDBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(Dish dish) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ResterauntDBHelper.RESTERAUNT_ID,   dish.getDishId());
        contentValues.put(ResterauntDBHelper.RESTERAUNT_NAME, dish.getDishPrice());
        contentValues.put(ResterauntDBHelper.IMAGE,           dish.getImage());
        int i = database.update(ResterauntDBHelper.TABLE_NAME, contentValues, ResterauntDBHelper.RESTERAUNT_ID + " = " + dish.getDishId(), null);
        return i;
    }

    public void delete(long _id) {
        database.delete(ResterauntDBHelper.TABLE_NAME, ResterauntDBHelper.RESTERAUNT_ID + "=" + _id, null);
    }
}
