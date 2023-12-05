package com.example.fooddelivery_mobileproject_group6;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery_mobileproject_group6.database.ReviewDBHelper;
import com.example.fooddelivery_mobileproject_group6.database.ReviewDBMgr;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReviewListActivity extends AppCompatActivity {
    private ArrayList<Review> reviewList;
    private ReviewAdapter reviewAdapter;
    private RecyclerView reviewRV;
    private static final int PERMISSION_REQ_CODE = 1;
    private static final int ADD_REVIEW_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*Cosmetic purpose; It sets the task bar and status bar with the same colour as the colour
            theme I chose to work with*/
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.carmine));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.carmine));
        }

        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviewList, this);
        reviewRV = findViewById(R.id.noteView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        reviewRV.setLayoutManager(linearLayoutManager);
        reviewRV.setAdapter(reviewAdapter);

        FloatingActionButton btn1 = findViewById(R.id.fabplus);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ReviewListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(ReviewListActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReviewListActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQ_CODE);
                } else {
                    Intent intent = new Intent(ReviewListActivity.this, AddReviewActivity.class);
                    intent.putExtra("noteId", -1);
                    startActivityForResult(intent, ADD_REVIEW_REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ReviewDBMgr dbHandler = new ReviewDBMgr(this);

        try {
            dbHandler.open();
            List<Review> fetchedReviews = cursorToReviewList(dbHandler.fetch());

            reviewList.clear();
            reviewList.addAll(fetchedReviews);
            reviewAdapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception or show an error message
        } finally {
            dbHandler.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REVIEW_REQUEST_CODE && resultCode == RESULT_OK) {
            updateReviewList();
        }
    }

    private void updateReviewList() {
        ReviewDBMgr dbHandler = new ReviewDBMgr(this);
        try {
            dbHandler.open();
            List<Review> fetchedReviews = cursorToReviewList(dbHandler.fetch());
            reviewList.clear();
            reviewList.addAll(fetchedReviews);
            reviewAdapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception or show an error message
        } finally {
            dbHandler.close();
        }
    }

    private List<Review> cursorToReviewList(Cursor cursor) {
        List<Review> reviews = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long reviewId = cursor.getLong(cursor.getColumnIndex(ReviewDBHelper.REVIEW_ID));
                long dishId = cursor.getLong(cursor.getColumnIndex(ReviewDBHelper.DISH_ID));
                float rating = cursor.getFloat(cursor.getColumnIndex(ReviewDBHelper.RATING));
                String text = cursor.getString(cursor.getColumnIndex(ReviewDBHelper.TEXT));
                byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(ReviewDBHelper.IMAGE));

                Review review = new Review();
                review.setReviewId(reviewId);
                review.setDishId(dishId);
                review.setRating(String.valueOf(rating));
                review.setReviewText(text);
                review.setImage(imageBytes);

                reviews.add(review);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return reviews;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(ReviewListActivity.this, AddReviewActivity.class);
                intent.putExtra("noteId", -1);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Permissions not granted. Cannot add a new review.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}


