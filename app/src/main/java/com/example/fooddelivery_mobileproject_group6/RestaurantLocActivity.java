package com.example.fooddelivery_mobileproject_group6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.example.fooddelivery_mobileproject_group6.database.DBHandler;

public class RestaurantLocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        RecyclerView recyclerView;
        RestaurantLocAdapter adapter;
        DBHandler db;
        List<RestaurantMod> locArrayList;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_loc);

        db = new DBHandler(this);

        if (db.getAllLocations().isEmpty()) {
            // Insert the latitude and longitude pairs
            db.insertLocationData("Whitby1", 43.87275321593204, -78.90286850353485);
            db.insertLocationData("Whitby2", 43.87832884405458, -78.94605855384832);
            db.insertLocationData("Toronto1", 43.82111003511183, -79.18271778819522);
            db.insertLocationData("Toronto2", 43.6571425113685, -79.38040463238148);
            db.insertLocationData("Oshawa", 43.94581271525949, -78.89680806120221);
        }

        locArrayList = db.getAllLocations();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RestaurantLocAdapter(locArrayList, this);
        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.locsearchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<RestaurantMod> filtLocList = new ArrayList<>();
                for (int i = 0; i < locArrayList.size(); i++){
                    RestaurantMod location = locArrayList.get(i);

                    if (location.getBranchName().toLowerCase().contains(newText.toLowerCase())){
                        filtLocList.add(location);
                    }
                }
                adapter.updateList(filtLocList);
                return false;
            }
        });
    }
}
