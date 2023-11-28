package com.example.fooddelivery_mobileproject_group6.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.fooddelivery_mobileproject_group6.Dish;
import com.example.fooddelivery_mobileproject_group6.Resteraunt;

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

    public void insert(Resteraunt resteraunt) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(ResterauntDBHelper.RESTERAUNT_ID,    resteraunt.getResterauntId());
        contentValue.put(ResterauntDBHelper.RESTERAUNT_NAME,  resteraunt.getResterauntName());
        contentValue.put(ResterauntDBHelper.IMAGE,            resteraunt.getImage());
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

    public int update(Resteraunt resteraunt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ResterauntDBHelper.RESTERAUNT_ID,   resteraunt.getResterauntId());
        contentValues.put(ResterauntDBHelper.RESTERAUNT_NAME, resteraunt.getResterauntName());
        contentValues.put(ResterauntDBHelper.IMAGE,           resteraunt.getImage());
        int i = database.update(ResterauntDBHelper.TABLE_NAME, contentValues, ResterauntDBHelper.RESTERAUNT_ID + " = " + resteraunt.getResterauntId(), null);
        return i;
    }

    public void delete(long _id) {
        database.delete(ResterauntDBHelper.TABLE_NAME, ResterauntDBHelper.RESTERAUNT_ID + "=" + _id, null);
    }
}
