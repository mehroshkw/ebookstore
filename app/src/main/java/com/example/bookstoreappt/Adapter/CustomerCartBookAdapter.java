package com.example.bookstoreappt.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstoreappt.Customer.CustomerCart;
import com.example.bookstoreappt.Model.CartBook;
import com.example.bookstoreappt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerCartBookAdapter extends RecyclerView.Adapter<CustomerCartBookAdapter.MyViewHolder> {

    //data members
    Context context;
    ArrayList<CartBook> arrayList;

    DatabaseReference reference;
    ProgressDialog dialog;

    //constructor
    public CustomerCartBookAdapter(Context context, ArrayList<CartBook> arrayList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_book_customer, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartBook book = arrayList.get(position);

        //set data to card view
        holder.tvTitle.setText(book.getTitle());
        holder.tvCategory.setText(book.getGenre().toUpperCase());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvPrice.setText(book.getPrice());
        holder.tvQty.setText(book.getQty());

        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setTitle("Removing");
                dialog.show();

                //save to cart
                reference.child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(book.getBookId()).removeValue().addOnCompleteListener(
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            dialog.dismiss();
                                            Toast.makeText(context, "Book removed form cart", Toast.LENGTH_SHORT).show();

                                            (context).startActivity(new Intent(context, CustomerCart.class));
                                            ((Activity)context).finish();
                                        }else{
                                            dialog.dismiss();
                                            Toast.makeText(context, "Book not removed from cart", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                        );


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //declaration card objects
        TextView  tvTitle, tvCategory, tvAuthor, tvPrice, tvQty, tvRemove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //initialization car object
            tvTitle = itemView.findViewById(R.id.tvCardTitle);
            tvCategory = itemView.findViewById(R.id.tvCardCategory);
            tvAuthor = itemView.findViewById(R.id.tvCardAuthor);
            tvPrice = itemView.findViewById(R.id.tvCardPrice);
            tvQty = itemView.findViewById(R.id.tvCardQty);

            tvRemove = itemView.findViewById(R.id.tvCardRemove);
        }
    }
}
