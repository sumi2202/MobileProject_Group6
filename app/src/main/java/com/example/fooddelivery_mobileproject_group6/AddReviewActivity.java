package com.example.fooddelivery_mobileproject_group6;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.fooddelivery_mobileproject_group6.database.ReviewDBMgr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class AddReviewActivity extends AppCompatActivity {
    private EditText titleET, descET;
    private String noteColor = "#FFFFFF";
    private Button saveNoteBtn, uploadBtn, captureBtn, backBtn;
    private ReviewDBMgr dbhandler;
    private ImageView imageView;
    private String imagePath;
    private ActivityResultLauncher<String> mUpload;
    private ActivityResultLauncher<Void> mCapture;

    Review review = new Review();

    private static final int PERMISSION_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review_layout);

        titleET = findViewById(R.id.viewtitle);
        descET = findViewById(R.id.viewdescription);
        saveNoteBtn = findViewById(R.id.savenotebtn);
        imageView = findViewById(R.id.imageview);
        uploadBtn = findViewById(R.id.uploadbtn);
        captureBtn = findViewById(R.id.capturebtn);
        backBtn = findViewById(R.id.backbtn);

        dbhandler = new ReviewDBMgr(AddReviewActivity.this);
        dbhandler.open();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    PERMISSION_REQ_CODE
            );
        }

        // ... (Color buttons and onClickListener)

        saveNoteBtn.setOnClickListener(view -> {
            String noteTitle = titleET.getText().toString();
            String noteDesc = descET.getText().toString();

            if (noteTitle.isEmpty()) {
                Toast.makeText(AddReviewActivity.this, "Please enter a title", Toast.LENGTH_SHORT).show();
                return;
            }

            review.setRating(noteTitle);
            review.setReviewText(noteDesc);

            if (imagePath != null) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
                review.setImage(Review.bitmapToByteArray(imageBitmap));
            }


            dbhandler.insert(review);

            Toast.makeText(AddReviewActivity.this, "Review has been added", Toast.LENGTH_SHORT).show();

            setResult(RESULT_OK);
            finish();
        });

        mUpload = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                    imagePath = bitmapToFile(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mCapture = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap -> {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                imagePath = bitmapToFile(bitmap);
            } else {
                Toast.makeText(AddReviewActivity.this, "Camera capture cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        uploadBtn.setOnClickListener(view -> mUpload.launch("image/*"));

        captureBtn.setOnClickListener(view -> mCapture.launch(null));

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(AddReviewActivity.this, ReviewListActivity.class);
            startActivity(intent);
        });
    }

    private String bitmapToFile(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e(getClass().getSimpleName(), "Bitmap is null");
            return null;
        }

        File folder = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (folder == null) {
            Log.e(getClass().getSimpleName(), "External storage directory is null");
            return null;
        }

        File imgFile = new File(folder, "image" + new Date().getTime() + ".png");

        try (OutputStream outStream = new FileOutputStream(imgFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Exception", e);
            return null;
        }

        return imgFile.getAbsolutePath();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted
            } else {
                Toast.makeText(this, "No permission given, please allow permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (dbhandler != null) {
            dbhandler.close();
        }
        super.onDestroy();
    }
}
