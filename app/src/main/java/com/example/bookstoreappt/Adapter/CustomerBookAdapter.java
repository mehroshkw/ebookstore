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

import com.example.bookstoreappt.Customer.CustomerBookView;
import com.example.bookstoreappt.Customer.CustomerComplaintSubmit;
import com.example.bookstoreappt.Model.Book;
import com.example.bookstoreappt.Model.CartBook;
import com.example.bookstoreappt.Model.Review;
import com.example.bookstoreappt.R;
import com.example.bookstoreappt.ReviewsList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerBookAdapter extends RecyclerView.Adapter<CustomerBookAdapter.MyViewHolder> {

    //data members
    Context context;
    ArrayList<Book> arrayList;

    DatabaseReference reference;
    ProgressDialog dialog;

    //constructor
    public CustomerBookAdapter(Context context, ArrayList<Book> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        reference = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        dialog = new ProgressDialog(context);
        dialog.setCancelable(true);
        dialog.setTitle("Loading...");
        dialog.show();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //add layout to recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_customer, parent,false);

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
            holder.tvAddToCart.setVisibility(View.INVISIBLE);
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
        holder.tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CustomerBookView.class);
                intent.putExtra("BookID", book.getBookId());
                (context).startActivity(intent);
                ((Activity)context).finish();
            }
        });
        holder.tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(context, "" +book, Toast.LENGTH_LONG).show();

            CartBook cartBook = new CartBook(book.getBookId(), book.getTitle(),
                book.getGenre(), book.getAuthor(), book.getEdition(), book.getReleaseDate(),
                book.getCompany(), book.getPrice(), book.getSellerID(), book.getStatus(),
                reference.push().getKey(), FirebaseAuth.getInstance().getCurrentUser().getUid(), "1");

                //save to cart
                reference.child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(cartBook.getBookId()).setValue(cartBook).addOnCompleteListener(
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            dialog.dismiss();
                                            Toast.makeText(context, "Book successfully added to cart", Toast.LENGTH_SHORT).show();
                                        }else{
                                            dialog.dismiss();
                                            Toast.makeText(context, "Book not added to cart", Toast.LENGTH_SHORT).show();
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
        TextView  tvTitle, tvCategory, tvAuthor, tvPrice, tvStatus, tvPublisher,
                tvReview, tvRating, tvView, tvAddToCart;
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

            tvReview = itemView.findViewById(R.id.tvCardReview);
            tvReview.setVisibility(View.GONE);
            tvRating = itemView.findViewById(R.id.tvCardRating);
            tvRating.setVisibility(View.GONE);
            ratingBar = itemView.findViewById(R.id.cardRatingBar);
            ratingBar.setVisibility(View.GONE);

//            tvComplaint = itemView.findViewById(R.id.tvCardComplaint);
            tvView = itemView.findViewById(R.id.tvCardView);
            tvAddToCart = itemView.findViewById(R.id.tvCardAddCart);
        }
    }
}
