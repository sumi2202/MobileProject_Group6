package com.example.fooddelivery_mobileproject_group6.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.fooddelivery_mobileproject_group6.Review;

import java.io.ByteArrayOutputStream;

public class ReviewDBMgr {
    private ReviewDBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    public ReviewDBMgr(Context c) {
        context = c;
        dbHelper = new ReviewDBHelper(context);
    }

    public ReviewDBMgr open() throws SQLException {
        try {
            database = dbHelper.getWritableDatabase();
            return this;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(Review review) {
        ContentValues contentValue = getContentValuesFromReview(review);
        database.insert(ReviewDBHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{ReviewDBHelper.REVIEW_ID, ReviewDBHelper.DISH_ID, ReviewDBHelper.RATING, ReviewDBHelper.TEXT, ReviewDBHelper.IMAGE};
        return database.query(ReviewDBHelper.TABLE_NAME, columns, null, null, null, null, null);
    }

    public int update(Review review) {
        ContentValues contentValues = getContentValuesFromReview(review);
        return database.update(ReviewDBHelper.TABLE_NAME, contentValues, ReviewDBHelper.REVIEW_ID + " = " + review.getReviewId(), null);
    }

    public void delete(long _id) {
        database.delete(ReviewDBHelper.TABLE_NAME, ReviewDBHelper.REVIEW_ID + "=" + _id, null);
    }

    private ContentValues getContentValuesFromReview(Review review) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(ReviewDBHelper.REVIEW_ID, review.getReviewId());
        contentValue.put(ReviewDBHelper.RATING, review.getRating());
        contentValue.put(ReviewDBHelper.TEXT, review.getReviewText());

        // Convert Bitmap to byte array
        byte[] imageBytes = review.getImage();
        if (imageBytes != null) {
            contentValue.put(ReviewDBHelper.IMAGE, imageBytes);
        }

        contentValue.put(ReviewDBHelper.DISH_ID, review.getDishId());
        return contentValue;
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}