package com.example.fooddelivery_mobileproject_group6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import com.example.fooddelivery_mobileproject_group6.database.MyCart;
import com.example.fooddelivery_mobileproject_group6.DishAdapter;
import com.example.fooddelivery_mobileproject_group6.Dish;


public class menuActivity extends AppCompatActivity {
    //declaring myCart object
    private MyCart myCart;
    private Button backBtn;

    ArrayList<Dish> dishes;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*Cosmetic purpose; It sets the task bar and status bar with the same colour as the colour
            theme I chose to work with*/
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.carmine));
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.carmine));
        }
        setContentView(R.layout.activity_menu);
        //Initializing myCart object
        myCart=new MyCart(this);

        //setting an onclick listener for the back button
        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainPage_Activity.class);
                startActivity(intent);
            }
        });

        //connecting the menu and the cart


        // Lookup the recyclerview in activity layout
        RecyclerView rvDishes = (RecyclerView) findViewById(R.id.rvDishes);

        // Initialize dishes
        dishes = Dish.createDishesList(20);
        // Create adapter passing in the sample user data
        DishAdapter adapter = new DishAdapter(dishes, new DishAdapter.OnAddButtonClickListener() {
            @Override
            public void onAddButtonClick(int position) {
                Dish selectedDish = dishes.get(position);
                myCart.insertDish(selectedDish);
            }
        });

        // Attach the adapter to the recyclerview to populate items
        rvDishes.setAdapter(adapter);
        // Set layout manager to position the items
        rvDishes.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }
}