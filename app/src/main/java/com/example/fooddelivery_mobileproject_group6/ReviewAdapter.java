package com.example.fooddelivery_mobileproject_group6;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<Review> reviewList;
    private Context context;

    //Constructor that takes an array list and a context object
    public ReviewAdapter(ArrayList<Review> reviewList, Context context){
        this.reviewList = reviewList;
        this.context = context;
    }

    //Creates a new ViewHolder and puts the note_item xml into a view

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }


    //Method to display the data and updates what's in the ViewHolder

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Review mod = reviewList.get(position);
        holder.notetitle.setText(String.valueOf(mod.getRating()));
        holder.notedesc.setText(mod.getReviewText());

        // Display image if available
        if (mod.getImage() != null && mod.getImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(mod.getImage(), 0, mod.getImage().length);
            holder.imageView.setImageBitmap(bitmap);
        } else {
            // If no image is available, you might want to set a default image or hide the ImageView
            holder.imageView.setImageResource(R.drawable.default_image);
        }
    }


    //Gets the number of items in the adapter

    public int getItemCount(){
        return reviewList.size();
    }

    //Class that extends the recycler view


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView notetitle, notedesc;
        private ImageView imageView; // Add this line

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notetitle = itemView.findViewById(R.id.TVNoteTitle);
            notedesc = itemView.findViewById(R.id.TVNoteDesc);
            imageView = itemView.findViewById(R.id.imageview); // Initialize imageView

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Review reviewEdit = reviewList.get(position);
                        Intent intent = new Intent(context, AddReviewActivity.class);
                        intent.putExtra("reviewId", reviewEdit.getReviewId());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

}


