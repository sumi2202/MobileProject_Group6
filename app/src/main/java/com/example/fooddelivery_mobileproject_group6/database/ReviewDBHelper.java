package com.example.fooddelivery_mobileproject_group6.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReviewDBHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "reviews";

    // Table columns
    public static final String REVIEW_ID = "reviewId";
    public static final String RATING = "rating";
    public static final String TEXT = "reviewText";
    public static final String IMAGE = "image";
    public static final String DISH_ID = "dishId";

    // Database Information
    static final String DB_NAME = "foodorderdb";

    // database version
    static final int DB_VERSION = 2;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + REVIEW_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DISH_ID + " INTEGER NOT NULL, "
            + RATING + " FLOAT NOT NULL, " + TEXT + " TEXT, " + IMAGE + " BLOB);";

    public ReviewDBHelper(Context context) {
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
