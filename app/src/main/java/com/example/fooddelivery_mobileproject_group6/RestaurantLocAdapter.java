package com.example.fooddelivery_mobileproject_group6;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RestaurantLocAdapter extends RecyclerView.Adapter<RestaurantLocAdapter.ViewHolder> {

    private List<RestaurantMod> locations;        //Defines variables
    private Context context;

    /*Constructor for RestaurantLocAdapter*/
    public RestaurantLocAdapter(List<RestaurantMod> locations, Context context){
        this.locations = locations;
        this.context = context;
    }

    /*ViewHolder that uses the cardview layout (restaurant_locations.xml)*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_locations, parent, false);
        return new ViewHolder(view);
    }

    /*Method for displaying the data at the specific position*/

    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        RestaurantMod location = locations.get(position);

        holder.branchNameTV.setText(location.getBranchName());    //Sets text for the branch name textview
        String address = getAddressfromLL(location.getLatitude(), location.getLongitude());    //Gets the address from lat-long pairs
        holder.addressTV.setText(address);    //Sets the text for the address textview

        /*onClickListener for the order button*/
        holder.orderButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, mainpage.class);    //Intent to navigate to the specified class
                context.startActivity(intent);
            }
        });

    }

    /*Method to get the size of the locations*/
    public int getItemCount(){
        return locations.size();
    }

    /*ViewHolder class that extends the recyclerview*/
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView branchNameTV, addressTV;    //Defines variables
        private Button orderButton;

        public ViewHolder (@NonNull View itemView){
            super(itemView);

            //Finds the specified textviews and buttons by id
            branchNameTV = itemView.findViewById(R.id.pinned_location);
            addressTV = itemView.findViewById(R.id.pinned_addr);
            orderButton = itemView.findViewById(R.id.orderbtn);
        }
    }

    /*Updates location list and notifies recyclerview in the case that the data changes*/
    public void updateList(List<RestaurantMod> updatelist){
        locations = updatelist;
        notifyDataSetChanged();
    }

    /*Method to get the address from lat-long pairs through reverse geocoding*/
    private String getAddressfromLL(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());    //Geocoder object

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);    //Uses .getFromLocation() method to get address list
            if (addresses != null && !addresses.isEmpty()){    //If addresses aren't null or empty
                Address address = addresses.get(0);    //Get the first object
                StringBuilder addressText = new StringBuilder();    //String builder to create the text for address
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++){    //Iterates through the lines of the address
                    if (i > 0) {
                        addressText.append(", ");    //Separates lines by appending a comma
                    }
                    addressText.append(address.getAddressLine(i));        //Appends to stringbuilder object
                    }
                return addressText.toString();        //Converts the result into a string and is returned
                }

        } catch (IOException e){        //Catches any IO exceptions
            Log.e("RestaurantLocAdapter", "Cannot connect to geocoder", e);

        }

        return null;
    }
}
