package com.example.fooddelivery_mobileproject_group6;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;

public class MainPage_Activity extends AppCompatActivity {

    // creating object of ViewPager
    ViewPager mViewPager;

    // images array
    int[] images = {R.drawable.pizza1, R.drawable.chicken_wings1, R.drawable.thai_curry1, R.drawable.burgers_fries1,
            R.drawable.burritos1, R.drawable.biryani1, R.drawable.sushi1, R.drawable.shawarma1};

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*Cosmetic purpose; It sets the task bar and status bar with the same colour as the colour
            theme I chose to work with*/
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.carmine));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.carmine));
        }

        // Initializing the ViewPager Object
        mViewPager = (ViewPager)findViewById(R.id.restaurantCarousel);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerAdapter(MainPage_Activity.this, images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
    }
}