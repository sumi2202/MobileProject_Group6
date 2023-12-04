package com.example.fooddelivery_mobileproject_group6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<Dish> mDishes;
    private OnAddButtonClickListener onAddButtonClickListener;


    // Pass in the contact array into the constructor
    public DishAdapter(List<Dish> contacts, OnAddButtonClickListener onAddButtonClickListener) {
        mDishes = contacts;
        this.onAddButtonClickListener = onAddButtonClickListener;

    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_dish, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Dish dish = mDishes.get(position);

        // Set item views based on your views and data model
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(dish.getDishName());
        TextView priceTextView = holder.priceTextView;
        priceTextView.setText(dish.getDishPrice());

        Button button = holder.messageButton;
        button.setText("Add");
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mDishes.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView priceTextView;
        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.dish_name);
            priceTextView = (TextView) itemView.findViewById(R.id.dish_price);
            messageButton = (Button) itemView.findViewById(R.id.add_button);
            messageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAddButtonClickListener != null) {
                        onAddButtonClickListener.onAddButtonClick(getAdapterPosition());

                    }

                    }
            });

        }
    }

    //interface to handle when user clicks the add button
    public interface OnAddButtonClickListener {
        void onAddButtonClick(int position);
    }

}
