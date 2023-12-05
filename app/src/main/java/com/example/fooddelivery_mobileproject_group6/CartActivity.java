package com.example.fooddelivery_mobileproject_group6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fooddelivery_mobileproject_group6.database.MyCart;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
//    private RecyclerView.Adapter adapter;
//    private RecyclerView recyclerViewList;
    private MyCart myCart;
    private TextView totalFeetxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;
    private ImageView backbtn;
    private CartListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        myCart = new MyCart(this);

        // Lookup the recyclerview in activity layout
        RecyclerView rvDishes = (RecyclerView) findViewById(R.id.cartView1);

        // Initialize dishes
        // Create adapter passing in the sample user data
         adapter = new CartListAdapter(myCart.getListCart(),this, new CartListAdapter.OrderQuantityListener() {
             @Override
             public void onPlusButtonClick(ArrayList<Dish> listFoodSelected, int position, MyCart myCart, CartListAdapter.OrderQuantityListener orderQuantityListener) {
                 myCart.plusNumberDish(listFoodSelected, position);
                 adapter.notifyDataSetChanged();
                 calculateOrder();
             }

             @Override
             public void onMinusButtonClick(ArrayList<Dish> listFoodSelected, int position, MyCart myCart, CartListAdapter.OrderQuantityListener orderQuantityListener) {
                 myCart.minusNumberDish(listFoodSelected, position);
                 adapter.notifyDataSetChanged();
                 calculateOrder();

             }

//             @Override
//            public void onPlusButtonClick(ArrayList<Dish> listFoodSelected, int position, MyCart myCart) {
//                myCart.plusNumberDish(listFoodSelected, position);
//                adapter.notifyDataSetChanged();
//                calculateOrder();
//            }

//            @Override
//            public void onMinusButtonClick(ArrayList<Dish> listFoodSelected, int position) {
//                myCart.minusNumberDish(listFoodSelected, position);
//                adapter.notifyDataSetChanged();
//                calculateOrder();
//            }

            public void changed() {
                calculateOrder();
            }
        });

        // Attach the adapter to the recyclerview to populate items
        rvDishes.setAdapter(adapter);
        // Set layout manager to position the items
        rvDishes.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

        initView();
        intitList();
        calculateOrder();
        setVariable();
    }

    private void setVariable() {
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void intitList() {

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerViewList.setLayoutManager(linearLayoutManager);
//        adapter = new CartListAdapter(myCart.getListCart(), this, new OrderQuantityListener() {
//            @Override
//            public void changed() {
//                calculateOrder();
//            }
//        });

//        recyclerViewList.setAdapter(adapter);
        if (myCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    //method to calculate the total order charges
    private void calculateOrder() {
        double percentTax = 0.05;
        double delivery = 5;
        tax = Math.round(myCart.getTotalFee() * percentTax * 100.0) / 100.0;
        double total = Math.round((myCart.getTotalFee() + tax + delivery) * 100.0) / 100;
        double itemTotal = Math.round(myCart.getTotalFee() * 100.0) / 100.0;
        totalFeetxt.setText("$" + itemTotal);
        taxTxt.setText("$" + tax);
        deliveryTxt.setText("$" + delivery);
        totalTxt.setText("$" + total);


    }

    private void initView() {
        totalFeetxt = findViewById(R.id.subtotalVal);
        taxTxt = findViewById(R.id.taxVal);
        deliveryTxt = findViewById(R.id.deliveryVal);
        totalTxt = findViewById(R.id.totalVal);
//        recyclerViewList = findViewById(R.id.cartView1);
        scrollView = findViewById(R.id.myScrollView);
        backbtn = findViewById(R.id.btnBack);
        emptyTxt = findViewById(R.id.emptyText);
    }
}