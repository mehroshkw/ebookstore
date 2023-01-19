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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstoreappt.Model.Book;
import com.example.bookstoreappt.Model.Review;
import com.example.bookstoreappt.R;
import com.example.bookstoreappt.ReviewsList;
import com.example.bookstoreappt.Seller.SellerBookEdit;
import com.example.bookstoreappt.Seller.SellerBooksManage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellerBookAdapter extends RecyclerView.Adapter<SellerBookAdapter.MyViewHolder> {

    //data members
    Context context;
    ArrayList<Book> arrayList;

    DatabaseReference reference;
    ProgressDialog dialog;

    //constructor
    public SellerBookAdapter(Context context, ArrayList<Book> arrayList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_seller, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Book book = arrayList.get(position);

        //set data to card view
        holder.tvTitle.setText(book.getTitle());
        holder.tvCategory.setText(book.getGenre().toUpperCase());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvPrice.setText(book.getPrice());
        holder.tvPublisher.setText(book.getCompany());

        if (book.getStatus().equals("Available")){
            holder.tvStatus.setTextColor(Color.BLUE);
            holder.tvStatus.setText(book.getStatus());
        }else{
            holder.tvStatus.setTextColor(Color.RED);
            holder.tvStatus.setText(book.getStatus());
        }


        //check submission of reviews/start ratings/ratings
        reference.child("Reviews").child(book.getBookId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getChildrenCount() > 0){
                            float Rating = 0, TotalRating = 0;

                            for (DataSnapshot temp : snapshot.getChildren()){
                                Review review = new Review();
                                review = temp.getValue(Review.class);

                                Rating = Rating + review.getRating();
                                TotalRating = TotalRating + review.getTotalRating();
                            }

                            //calculate ratings
                            holder.tvRating.setText(String.valueOf(Rating/snapshot.getChildrenCount()) +"/" +5 +" (" +snapshot.getChildrenCount() +")");
                            //display rating on bar
                            holder.ratingBar.setRating(Rating/snapshot.getChildrenCount());

                            String txt = holder.tvReview.getText().toString();
                            if (snapshot.getChildrenCount()==1){
                                holder.tvReview.setText(snapshot.getChildrenCount() +" Review" );
                            }else if (snapshot.getChildrenCount()>1){
                                holder.tvReview.setText(snapshot.getChildrenCount() +" Reviews" );
                            }
                            //set visibility of rating, ratingbar and reviews to VISIBLE
                            holder.ratingBar.setVisibility(View.VISIBLE);
//                            holder.ratingBar.setEnabled(false);
                            holder.tvRating.setVisibility(View.VISIBLE);
                            holder.tvReview.setVisibility(View.VISIBLE);
                            dialog.dismiss();



                        }else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        //apply click listener
        holder.tvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReviewsList.class);
                intent.putExtra("BookID", book.getBookId());
                (context).startActivity(intent);
                ((Activity)context).finish();

            }
        });

        //apply click listener
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SellerBookEdit.class);
                intent.putExtra("BookID", book.getBookId());
                (context).startActivity(intent);
                ((Activity)context).finish();
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setTitle("Deleting");
                dialog.show();

//                Toast.makeText(context, "" +book.getBookId(), Toast.LENGTH_SHORT).show();

                //update Contact status
                reference.child("Books").child(book.getBookId()).setValue(null)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    dialog.dismiss();
                                    Toast.makeText(context, "Record deleted", Toast.LENGTH_SHORT).show();

                                    (context).startActivity(new Intent(context, SellerBooksManage.class));
                                    ((Activity)context).finish();

                                }else{
                                    dialog.dismiss();
                                    Toast.makeText(context, "Record not deleted", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //declaration card objects
        TextView  tvTitle, tvCategory, tvAuthor, tvPrice, tvStatus, tvPublisher, tvEdit, tvDelete;
        TextView tvRating, tvReview;
        RatingBar ratingBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //initialization car object
            tvTitle = itemView.findViewById(R.id.tvCardTitle);
            tvCategory = itemView.findViewById(R.id.tvCardCategory);
            tvAuthor = itemView.findViewById(R.id.tvCardAuthor);
            tvPrice = itemView.findViewById(R.id.tvCardPrice);
            tvStatus = itemView.findViewById(R.id.tvCardStatus);
            tvPublisher = itemView.findViewById(R.id.tvCardPublisher);

            tvEdit = itemView.findViewById(R.id.tvCardEdit);
            tvDelete = itemView.findViewById(R.id.tvCardAddCart);

            tvReview = itemView.findViewById(R.id.tvCardReview);
            tvReview.setVisibility(View.GONE);
            tvRating = itemView.findViewById(R.id.tvCardRating);
            tvRating.setVisibility(View.GONE);
            ratingBar = itemView.findViewById(R.id.cardRatingBar);
            ratingBar.setVisibility(View.GONE);
        }
    }
}
