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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstoreappt.Customer.CustomerComplaintSubmit;
import com.example.bookstoreappt.Model.OrderedBook;
import com.example.bookstoreappt.R;
import com.example.bookstoreappt.Customer.SubmitReview;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerOrderedBookAdapter extends RecyclerView.Adapter<CustomerOrderedBookAdapter.MyViewHolder> {

    //data members
    Context context;
    ArrayList<OrderedBook> arrayList;

    DatabaseReference reference;
    ProgressDialog dialog;

    //constructor
    public CustomerOrderedBookAdapter(Context context, ArrayList<OrderedBook> arrayList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordered_book_customer, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        OrderedBook book = arrayList.get(position);

        //set data to card view
        holder.tvTitle.setText(book.getTitle());
        holder.tvCategory.setText(book.getGenre().toUpperCase());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvPrice.setText(book.getPrice());
        holder.tvEdition.setText(book.getEdition());
        holder.tvQty.setText(book.getQty());
        holder.tvPublisher.setText(book.getCompany());


        //check already review submission
        reference.child("Reviews").child(book.getBookId())
                .orderByChild("userUid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() == 0){
                            holder.tvReview.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//        reference.child("Reviews").child(book.getBookId())
//                .orderByChild("userUid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.getChildrenCount() == 0){
//                            holder.tvReview.setVisibility(View.VISIBLE);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

        //apply click listener
        holder.tvComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomerComplaintSubmit.class);
                intent.putExtra("SellerID", book.getSellerID());
                (context).startActivity(intent);
                ((Activity)context).finish();

            }
        });

        holder.tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Book ID: " +book.getBookId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SubmitReview.class);
                intent.putExtra("BookID", book.getBookId());
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
        TextView  tvReview, tvComplaint, tvTitle, tvCategory, tvAuthor, tvPrice, tvQty, tvEdition, tvPublisher ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //initialization car object
            tvReview = itemView.findViewById(R.id.tvCardReview);
            tvReview.setVisibility(View.GONE);
            tvComplaint = itemView.findViewById(R.id.tvCardComplaint);

            tvTitle = itemView.findViewById(R.id.tvCardTitle);
            tvCategory = itemView.findViewById(R.id.tvCardCategory);
            tvAuthor = itemView.findViewById(R.id.tvCardAuthor);
            tvPrice = itemView.findViewById(R.id.tvCardPrice);
            tvEdition = itemView.findViewById(R.id.tvCardEdition);
            tvQty = itemView.findViewById(R.id.tvCardQty);
            tvPublisher = itemView.findViewById(R.id.tvCardCompany);

        }
    }
}
