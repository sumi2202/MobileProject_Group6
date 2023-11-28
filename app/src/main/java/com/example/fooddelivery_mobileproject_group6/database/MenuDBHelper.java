package com.example.fooddelivery_mobileproject_group6.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MenuDBHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "menuitems";

    // Table columns
    public static final String DISH_ID = "dishId";
    public static final String DISH_NAME = "dishName";
    public static final String DISH_PRICE = "dishPrice";
    public static final String RESTERAUNT_ID = "resterauntId";
    public static final String IMAGE = "image";

    // Database Information
    static final String DB_NAME = "foodorderdb";

    // database version
    static final int DB_VERSION = 2;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + DISH_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DISH_NAME + " TEXT NOT NULL, " + DISH_PRICE + " TEXT, " + RESTERAUNT_ID + " TEXT NOT NULL, " + IMAGE + " TEXT);";

    public MenuDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
