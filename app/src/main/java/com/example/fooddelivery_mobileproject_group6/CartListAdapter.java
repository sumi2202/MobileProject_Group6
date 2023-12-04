package com.example.fooddelivery_mobileproject_group6;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery_mobileproject_group6.database.MyCart;
import com.example.fooddelivery_mobileproject_group6.database.OrderQuantityListener;
import com.example.fooddelivery_mobileproject_group6.Dish;
import com.example.fooddelivery_mobileproject_group6.R;

import java.util.ArrayList;
import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    ArrayList<Dish>listFoodSelected;
    private MyCart myCart;
    OrderQuantityListener orderQuantityListener;

    public CartListAdapter(ArrayList<Dish> listFoodSelected, Context context, OrderQuantityListener orderQuantityListener) {
        this.listFoodSelected = listFoodSelected;
        myCart = new MyCart(context);
        this.orderQuantityListener = orderQuantityListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_viewholder,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        double price = Double.parseDouble(listFoodSelected.get(position).getDishPrice());
        int quantity = listFoodSelected.get(position).getNumberinCart();

// Perform multiplication and round the result
        int totalAmount = (int) Math.round(quantity * price);

// Set the total amount as text in the TextView
        holder.totalEachItem.setText("$" + totalAmount);

        holder.title.setText(listFoodSelected.get(position).getDishName());
        holder.feeEachItem.setText("$"+listFoodSelected.get(position).getDishPrice());
        holder.num.setText((String.valueOf(listFoodSelected.get(position).getNumberinCart())));

        // Set image using setImageResource
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(listFoodSelected.get(position).getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.pic.setImageResource(drawableResourceId);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCart.plusNumberDish(listFoodSelected,position);
                notifyDataSetChanged();
                orderQuantityListener.changed();
            }
        });

        holder.minusItem.setOnClickListener(v -> myCart.minusNumberDish(listFoodSelected,position, () -> {
            notifyDataSetChanged();
            orderQuantityListener.changed();
        }));

    }

    @Override
    public int getItemCount() {
        return listFoodSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, plusItem, minusItem, totalEachItem, num;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            num = itemView.findViewById(R.id.numberItemTxt);
        }
    }

}

