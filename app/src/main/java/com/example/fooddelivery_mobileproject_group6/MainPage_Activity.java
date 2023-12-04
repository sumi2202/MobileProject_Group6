package com.example.fooddelivery_mobileproject_group6;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainPage_Activity extends AppCompatActivity {

    // creating object of ViewPager
    ViewPager mViewPager;
    CardView nearbyBtn, recentBtn, cartBtn, favBtn, pointsBtn, accountBtn;

    // images array
    int[] images = {R.drawable.pizza1, R.drawable.chicken_wings1, R.drawable.thai_curry1, R.drawable.burgers_fries1,
            R.drawable.burritos1, R.drawable.biryani1, R.drawable.sushi1, R.drawable.shawarma1};

    // Creating Object of ViewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        nearbyBtn = (CardView) findViewById(R.id.nearby);
        recentBtn = (CardView) findViewById(R.id.recent);
        cartBtn = (CardView) findViewById(R.id.cart);
        favBtn = (CardView) findViewById(R.id.favourites);
        pointsBtn = (CardView) findViewById(R.id.points);
        accountBtn = (CardView) findViewById(R.id.account);


        //navigating user to their cart
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        //navigating the user to the restaurant location page
        nearbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RestaurantLocActivity.class);
                startActivity(intent);
            }
        });




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

        nearbyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RestaurantLocActivity.class);    //Brings user to main page
                startActivity(intent);
            }
        });

        }
    }



