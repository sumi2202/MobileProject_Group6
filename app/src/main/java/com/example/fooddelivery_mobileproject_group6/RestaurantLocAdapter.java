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

    private List<RestaurantMod> locations;
    private Context context;
    public RestaurantLocAdapter(List<RestaurantMod> locations, Context context){
        this.locations = locations;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_locations, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        RestaurantMod location = locations.get(position);

        holder.branchNameTV.setText(location.getBranchName());
        String address = getAddressfromLL(location.getLatitude(), location.getLongitude());
        holder.addressTV.setText(address);

        holder.orderButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, mainpage.class);
                context.startActivity(intent);
            }
        });

    }

    public int getItemCount(){
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView branchNameTV, addressTV;
        private Button orderButton;

        public ViewHolder (@NonNull View itemView){
            super(itemView);

            branchNameTV = itemView.findViewById(R.id.pinned_location);
            addressTV = itemView.findViewById(R.id.pinned_addr);
            orderButton = itemView.findViewById(R.id.orderbtn);
        }
    }

    public void updateList(List<RestaurantMod> updatelist){
        locations = updatelist;
        notifyDataSetChanged();
    }

    private String getAddressfromLL(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()){
                Address address = addresses.get(0);
                StringBuilder addressText = new StringBuilder();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++){
                    if (i > 0) {
                        addressText.append(", ");
                    }
                    addressText.append(address.getAddressLine(i));
                    }
                return addressText.toString();
                }

        } catch (IOException e){
            Log.e("RestaurantLocAdapter", "Cannot connect to geocoder", e);

        }

        return null;
    }
}
