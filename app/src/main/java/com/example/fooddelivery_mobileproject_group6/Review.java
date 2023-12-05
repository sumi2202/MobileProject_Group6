// Review.java
package com.example.fooddelivery_mobileproject_group6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

public class Review implements Parcelable {
    private long reviewId;
    private long dishId;
    private String rating;
    private String reviewText;
    private byte[] imageBytes; // Store image data as byte array

    public Review() {
        // Default constructor
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public byte[] getImage() {
        return imageBytes;
    }

    public void setImage(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public Bitmap getImageBitmap() {
        if (imageBytes != null) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return null;
        }
    }

    // Parcelable implementation
    protected Review(Parcel in) {
        reviewId = in.readLong();
        dishId = in.readLong();
        rating = in.readString();
        reviewText = in.readString();
        imageBytes = in.createByteArray();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(reviewId);
        dest.writeLong(dishId);
        dest.writeString(rating);
        dest.writeString(reviewText);
        dest.writeByteArray(imageBytes);
    }

    // Helper method to convert Bitmap to byte array
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}

