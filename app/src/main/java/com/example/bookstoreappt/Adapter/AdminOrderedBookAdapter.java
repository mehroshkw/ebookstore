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
import com.example.bookstoreappt.Customer.SubmitReview;
import com.example.bookstoreappt.Model.OrderedBook;
import com.example.bookstoreappt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminOrderedBookAdapter extends RecyclerView.Adapter<AdminOrderedBookAdapter.MyViewHolder> {

    //data members
    Context context;
    ArrayList<OrderedBook> arrayList;

    DatabaseReference reference;
    ProgressDialog dialog;

    //constructor
    public AdminOrderedBookAdapter(Context context, ArrayList<OrderedBook> arrayList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordered_book_admin, parent,false);

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


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //declaration card objects
        TextView  tvTitle, tvCategory, tvAuthor, tvPrice, tvQty, tvEdition, tvPublisher ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


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
