package com.example.fooddelivery_mobileproject_group6.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.fooddelivery_mobileproject_group6.Review;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

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

    // Database version
    static final int DB_VERSION = 2;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DISH_ID + " INTEGER NOT NULL, "
            + RATING + " FLOAT NOT NULL, "
            + TEXT + " TEXT, "
            + IMAGE + " BLOB);";

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

    public void addNewReview(String rating, String reviewText, Bitmap imageBitmap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RATING, rating);
        values.put(TEXT, reviewText);
        values.put(IMAGE, bitmapToByteArray(imageBitmap)); // Convert Bitmap to byte array

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Review> readReviews() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{REVIEW_ID, DISH_ID, RATING, TEXT, IMAGE};
        Cursor cursorReviews = db.query(TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Review> reviewList = new ArrayList<>();

        if (cursorReviews.moveToFirst()) {
            do {
                Review review = new Review();
                review.setReviewId(cursorReviews.getLong(cursorReviews.getColumnIndex(REVIEW_ID)));
                review.setDishId(cursorReviews.getLong(cursorReviews.getColumnIndex(DISH_ID)));
                review.setRating(String.valueOf(cursorReviews.getFloat(cursorReviews.getColumnIndex(RATING))));
                review.setReviewText(cursorReviews.getString(cursorReviews.getColumnIndex(TEXT)));

                // Retrieve the image as byte array from the cursor
                byte[] imageBytes = cursorReviews.getBlob(cursorReviews.getColumnIndex(IMAGE));

                // Set the image using the new setImage method
                review.setImage(imageBytes);

                reviewList.add(review);
            } while (cursorReviews.moveToNext());
        }

        cursorReviews.close();
        return reviewList;
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}