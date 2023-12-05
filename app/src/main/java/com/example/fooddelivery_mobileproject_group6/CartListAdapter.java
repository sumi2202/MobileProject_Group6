package com.example.fooddelivery_mobileproject_group6;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery_mobileproject_group6.database.MyCart;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    ArrayList<Dish> listFoodSelected;
    private MyCart myCart;
    OrderQuantityListener orderQuantityListener;

    public CartListAdapter(ArrayList<Dish> listFoodSelected, Context context, OrderQuantityListener orderQuantityListener) {
        this.listFoodSelected = listFoodSelected;
        myCart = new MyCart(context);
        this.orderQuantityListener = orderQuantityListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
//        View contactView = inflater.inflate(R.layout.item_dish, parent, false);
        View contactView = inflater.inflate(R.layout.cart_viewholder, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, myCart, orderQuantityListener);
        return viewHolder;
    }

//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_viewholder,parent,false);
//        return new ViewHolder(inflate);
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Get the data model based on position
        Dish dish = listFoodSelected.get(position);

        // Set item views based on your views and data model
        double price = Double.parseDouble(listFoodSelected.get(position).getDishPrice().replace("$", ""));
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
                if (orderQuantityListener != null) {
                    orderQuantityListener.onPlusButtonClick(listFoodSelected, position, myCart, orderQuantityListener);
                }
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderQuantityListener != null) {
                    orderQuantityListener.onMinusButtonClick(listFoodSelected, position, myCart, orderQuantityListener);
                }
            }
        });


    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        double price = Double.parseDouble(listFoodSelected.get(position).getDishPrice().replace("$", ""));
//        int quantity = listFoodSelected.get(position).getNumberinCart();
//
//// Perform multiplication and round the result
//        int totalAmount = (int) Math.round(quantity * price);
//
//// Set the total amount as text in the TextView
//        holder.totalEachItem.setText("$" + totalAmount);
//
//        holder.title.setText(listFoodSelected.get(position).getDishName());
//        holder.feeEachItem.setText("$"+listFoodSelected.get(position).getDishPrice());
//        holder.num.setText((String.valueOf(listFoodSelected.get(position).getNumberinCart())));
//
//        // Set image using setImageResource
//        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(listFoodSelected.get(position).getImage(), "drawable", holder.itemView.getContext().getPackageName());
//        holder.pic.setImageResource(drawableResourceId);
//        holder.plusItem.setOnClickListener(v -> myCart.plusNumberDish(listFoodSelected,position, () -> {
//            notifyDataSetChanged();
//            orderQuantityListener.changed();
//        }));
//
//        holder.minusItem.setOnClickListener(v -> myCart.minusNumberDish(listFoodSelected,position, () -> {
//            notifyDataSetChanged();
//            orderQuantityListener.changed();
//        }));
//
//    }

    @Override
    public int getItemCount() {
        return listFoodSelected.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView title, feeEachItem, plusItem, minusItem, totalEachItem, num;
        ImageView pic;

        //adding this parts
        private MyCart myCart;
        private OrderQuantityListener orderQuantityListener;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, MyCart myCart, OrderQuantityListener orderQuantityListener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.myCart = myCart;
            this.orderQuantityListener = orderQuantityListener;

            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderQuantityListener != null) {
                        orderQuantityListener.onPlusButtonClick(listFoodSelected, getAdapterPosition(), myCart, orderQuantityListener);

                    }

                }
            });

            minusItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderQuantityListener != null) {
                        orderQuantityListener.onMinusButtonClick(listFoodSelected, getAdapterPosition(),  myCart, orderQuantityListener);

                    }

                }
            });

        }

    }

    //interface to handle when user clicks the add button
    public interface OrderQuantityListener {
        void onPlusButtonClick(ArrayList<Dish> listFoodSelected, int position, MyCart myCart, OrderQuantityListener orderQuantityListener);
        void onMinusButtonClick(ArrayList<Dish> listFoodSelected, int position, MyCart myCart, OrderQuantityListener orderQuantityListener);

        void changed();
    }

//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView title, feeEachItem, plusItem, minusItem, totalEachItem, num;
//        ImageView pic;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            title = itemView.findViewById(R.id.titleTxt);
//            pic = itemView.findViewById(R.id.pic);
//            feeEachItem = itemView.findViewById(R.id.feeEachItem);
//            totalEachItem = itemView.findViewById(R.id.totalEachItem);
//            plusItem = itemView.findViewById(R.id.plusCartBtn);
//            minusItem = itemView.findViewById(R.id.minusCartBtn);
//            num = itemView.findViewById(R.id.numberItemTxt);
//        }
//    }

}

