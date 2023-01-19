package com.example.bookstoreappt.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstoreappt.Admin.AdminOrderedBook;
import com.example.bookstoreappt.Model.Order;
import com.example.bookstoreappt.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.MyViewHolder> {

    //data members
    Context context;
    ArrayList<Order> arrayList;

    DatabaseReference reference;
    ProgressDialog dialog;

    //constructor
    public AdminOrderAdapter(Context context, ArrayList<Order> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        reference = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        dialog = new ProgressDialog(context);
        dialog.setCancelable(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //add layout to recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_admin, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Order order = arrayList.get(position);

        //set data to card view
        holder.tvBuyer.setText(order.getName());
        holder.tvPhone.setText(order.getPhone());
        holder.tvAddress.setText(order.getAddress());
        holder.tvAmount.setText(order.getOrderAmount());
        holder.tvQty.setText(order.getOrderQty());

        holder.tvDate.setText(order.getTimestamp());

        holder.tvStatus.setText(order.getOrderStatus());
        if (order.getOrderStatus().equals("Pending")){
            holder.tvStatus.setTextColor(Color.RED);
        }else{
            holder.tvStatus.setTextColor(Color.BLUE);
        }

        //apply click listener
        holder.tvBookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminOrderedBook.class);
                intent.putExtra("OrderID", order.getOrderId());
                (context).startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //declaration card objects
        TextView  tvBuyer, tvPhone, tvAddress, tvAmount, tvQty, tvStatus, tvDate, tvBookView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //initialization car object
            tvBuyer = itemView.findViewById(R.id.tvCardBuyer);
            tvPhone = itemView.findViewById(R.id.tvCardPhone);
            tvAddress = itemView.findViewById(R.id.tvCardAddress);
            tvAmount = itemView.findViewById(R.id.tvCardAmount);
            tvQty = itemView.findViewById(R.id.tvCardQty);
            tvStatus = itemView.findViewById(R.id.tvCardStatus);
            tvDate = itemView.findViewById(R.id.tvCardTimestamp);

            tvBookView = itemView.findViewById(R.id.tvCardBookView);
        }
    }
}
