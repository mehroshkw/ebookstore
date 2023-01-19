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

import com.example.bookstoreappt.Admin.AdminCategoryEdit;
import com.example.bookstoreappt.Admin.AdminCategoryManage;
import com.example.bookstoreappt.Model.Category;
import com.example.bookstoreappt.Model.Review;
import com.example.bookstoreappt.Model.User;
import com.example.bookstoreappt.R;
import com.example.bookstoreappt.ReviewsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    //data members
    Context context;
    ArrayList<Review> arrayList;

    DatabaseReference reference;
    ProgressDialog dialog;

    //constructor
    public ReviewAdapter(Context context, ArrayList<Review> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        reference = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        dialog = new ProgressDialog(context);
        dialog.setCancelable(true);
        dialog.setTitle("Loading");
        dialog.show();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //add layout to recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Review review = arrayList.get(position);

        holder.tvReview.setText(review.getReview());
        holder.tvRating.setText(String.valueOf(review.getRating()) +"/" +String.valueOf(review.getTotalRating()));

        //set data to card view
        reference.child("Users").child(review.getUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {
                    dialog.dismiss();

                    User user = new User();
                    user = snapshot.getValue(User.class);
//                    Toast.makeText(context, "" +user, Toast.LENGTH_SHORT).show();

                    holder.tvBuyer.setText(user.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //declaration card objects
        TextView tvBuyer, tvReview, tvRating;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //initialization car object
            tvBuyer = itemView.findViewById(R.id.tvCardBuyer);
            tvReview = itemView.findViewById(R.id.tvCardReview);
            tvRating = itemView.findViewById(R.id.tvCardRating);
        }
    }
}
