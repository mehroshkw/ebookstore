package com.example.bookstoreappt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bookstoreappt.Adapter.CustomerOrderedBookAdapter;
import com.example.bookstoreappt.Adapter.ReviewAdapter;
import com.example.bookstoreappt.Model.OrderedBook;
import com.example.bookstoreappt.Model.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewsList extends AppCompatActivity {

    //declaration
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog dialog;

    RecyclerView recyclerView;

    ArrayList<Review> arrayList;

    String BookID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_list);setTitle("Ordered Books");

        //initialization
        BookID = getIntent().getStringExtra("BookID");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        arrayList = new ArrayList<>();
        arrayList.clear();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReviewsList.this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        dialog.setTitle("Loading");
        dialog.show();

        arrayList.clear();

        reference.child("Reviews").child(BookID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    dialog.dismiss();
//                    Toast.makeText(ReviewsList.this, "" +snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();

                    for (DataSnapshot temp : snapshot.getChildren()){
                        Review review = new Review();
                        review = temp.getValue(Review.class);

                        //adding book to arraylist
                        arrayList.add(review);
                    }

//                    initialize adapter and set to recycler view
                    ReviewAdapter adapter = new ReviewAdapter(ReviewsList.this, arrayList);
                    recyclerView.setAdapter(adapter);

                }else{
                    dialog.dismiss();
                    Toast.makeText(ReviewsList.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}