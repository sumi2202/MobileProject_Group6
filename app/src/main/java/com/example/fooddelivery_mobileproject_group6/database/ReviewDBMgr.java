package com.example.fooddelivery_mobileproject_group6.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.fooddelivery_mobileproject_group6.Resteraunt;
import com.example.fooddelivery_mobileproject_group6.Review;

public class ReviewDBMgr {
    private ReviewDBHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public ReviewDBMgr(Context c) {
        context = c;
    }

    public ReviewDBMgr open() throws SQLException {
        dbHelper = new ReviewDBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Review review) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(ReviewDBHelper.REVIEW_ID,  review.getReviewId());
        contentValue.put(ReviewDBHelper.RATING,     review.getRating());
        contentValue.put(ReviewDBHelper.TEXT,       review.getReviewText());
        contentValue.put(ReviewDBHelper.IMAGE,      review.getImage());
        contentValue.put(ReviewDBHelper.DISH_ID,    review.getDishId());
        database.insert(ReviewDBHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { ReviewDBHelper.REVIEW_ID, ReviewDBHelper.DISH_ID, ReviewDBHelper.RATING, ReviewDBHelper.TEXT, ReviewDBHelper.IMAGE };
        Cursor cursor = database.query(ReviewDBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(Review review) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ReviewDBHelper.REVIEW_ID,  review.getReviewId());
        contentValues.put(ReviewDBHelper.RATING,     review.getRating());
        contentValues.put(ReviewDBHelper.TEXT,       review.getReviewText());
        contentValues.put(ReviewDBHelper.IMAGE,      review.getImage());
        contentValues.put(ReviewDBHelper.DISH_ID,    review.getDishId());
        int i = database.update(ReviewDBHelper.TABLE_NAME, contentValues, ReviewDBHelper.REVIEW_ID + " = " + review.getReviewId(), null);
        return i;
    }

    public void delete(long _id) {
        database.delete(ReviewDBHelper.TABLE_NAME, ReviewDBHelper.REVIEW_ID + "=" + _id, null);
    }
}
