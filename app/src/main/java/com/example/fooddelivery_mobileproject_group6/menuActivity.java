package com.example.fooddelivery_mobileproject_group6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class menuActivity extends AppCompatActivity {

    ArrayList<Dish> dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Lookup the recyclerview in activity layout
        RecyclerView rvDishes = (RecyclerView) findViewById(R.id.rvDishes);

        // Initialize dishes
        dishes = Dish.createDishesList(20);
        // Create adapter passing in the sample user data
        DishAdapter adapter = new DishAdapter(dishes);
        // Attach the adapter to the recyclerview to populate items
        rvDishes.setAdapter(adapter);
        // Set layout manager to position the items
        rvDishes.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }
}