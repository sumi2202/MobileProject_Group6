package com.example.fooddelivery_mobileproject_group6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.SearchView;
import com.google.android.gms.maps.*;                           //Imports google maps libraries
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.example.fooddelivery_mobileproject_group6.database.DBHandler;

public class RestaurantLocActivity extends AppCompatActivity {
private MapView locmapview;            //Variable for mapview
private GoogleMap googmap;          //Google Maps object
private List<RestaurantMod> locArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        RecyclerView recyclerView;          //Defines variables
        RestaurantLocAdapter adapter;
        DBHandler db;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_loc);

        locmapview = (MapView) findViewById(R.id.mapView);      //Finds the map view by id
        locmapview.onCreate(savedInstanceState);

        locmapview.getMapAsync(new OnMapReadyCallback(){        //Initializes the google map
            @Override
            public void onMapReady(GoogleMap googlemap){        //When map is ready, onMapReady() method is called
                googmap = googlemap;        //Google map is assigned to the variable

                /*Iterates through the location array list and adds the markers for the locations based on lat-long pairs */
                for (RestaurantMod location : locArrayList){
                    LatLng latlong = new LatLng(location.getLatitude(), location.getLongitude());
                    googmap.addMarker(new MarkerOptions().position(latlong).title(location.getBranchName()));

                }
                if (!locArrayList.isEmpty()){       //If location array list isn't empty
                    LatLng firstloc = new LatLng(locArrayList.get(0).getLatitude(), locArrayList.get(0).getLongitude());    //Moves camera to first location
                    googmap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstloc, 10));    //Zoom level set to 10
                }
            }
        });

        db = new DBHandler(this);
        locArrayList = db.getAllLocations();        //Gets the locations from the database

        if (db.getAllLocations().isEmpty()) {   //If empty, insert the location, and latitude and longitude pairs
            //The data to be inserted, which are the 5 branches and their lat-long pairs
            db.insertLocationData("Whitby1", 43.87275321593204, -78.90286850353485);
            db.insertLocationData("Whitby2", 43.87832884405458, -78.94605855384832);
            db.insertLocationData("Toronto1", 43.82111003511183, -79.18271778819522);
            db.insertLocationData("Toronto2", 43.6571425113685, -79.38040463238148);
            db.insertLocationData("Oshawa", 43.94581271525949, -78.89680806120221);
        }

        locArrayList = db.getAllLocations();

        recyclerView = findViewById(R.id.recycleView);      //Finds the recyclerview by id
        recyclerView.setLayoutManager(new LinearLayoutManager(this));       //Sets the linear layout manager
        adapter = new RestaurantLocAdapter(locArrayList, this);
        recyclerView.setAdapter(adapter);       //Sets up the adapter

        SearchView searchView = findViewById(R.id.locsearchview);       //Finds the searchview by id
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /*Listens for the text inputted (submitted) by the user in the search bar*/
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            /*Method for when the text in the search bar changes*/
            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<RestaurantMod> filtLocList = new ArrayList<>();       //Makes new array list
                for (int i = 0; i < locArrayList.size(); i++){      //Iterates through the location array list
                    RestaurantMod location = locArrayList.get(i);   //Gets the current location

                    if (location.getBranchName().toLowerCase().contains(newText.toLowerCase())){        //If branch name contains the text inputted
                        filtLocList.add(location);      //Adds to the list
                    }
                }
                adapter.updateList(filtLocList);        //Updates the recycler view
                return false;
            }
        });
    }

/*OnResume method for map view*/
    @Override
    protected void onResume(){
        super.onResume();
        locmapview.onResume();
    }
    /*OnPause method for map view*/
    @Override
    protected void onPause(){
        locmapview.onPause();
        super.onPause();
    }
    /*OnDestroy method for map view*/
    @Override
    protected void onDestroy(){
        locmapview.onDestroy();
        super.onDestroy();
    }

    /*OnSaveInstanceState method for map view*/
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        locmapview.onSaveInstanceState(outState);
    }

    /*OnLowMemory method for map view*/
    @Override
    public void onLowMemory(){
        super.onLowMemory();
        locmapview.onLowMemory();
    }
}
