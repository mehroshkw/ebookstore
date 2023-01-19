package com.example.bookstoreappt.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstoreappt.Model.Review;
import com.example.bookstoreappt.OrderedBooks;
import com.example.bookstoreappt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitReview extends AppCompatActivity {

    String BookID = "";

    RatingBar ratingBar;
    EditText editReview;
    Button btnSave;
    TextView textBack;
    ProgressDialog dialog;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_review);
        setTitle("Book's Review");

        //initialization
        BookID = getIntent().getStringExtra("BookID");

        reference = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        ratingBar = findViewById(R.id.ratingBar);
        editReview = findViewById(R.id.editReview);

        btnSave = findViewById(R.id.btnSave);
        textBack = findViewById(R.id.textBack);

        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubmitReview.this, CustomerOrdersPending.class));
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = reference.push().getKey();
                String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String review = editReview.getText().toString();
                float rating = ratingBar.getRating();
                float totalRating = ratingBar.getNumStars();

//                Toast.makeText(SubmitReview.this, "Rating: " +rating +"/" +totalRating, Toast.LENGTH_SHORT).show();

                //validate
                if (review.isEmpty()){
                    editReview.setError("Required");
                    editReview.requestFocus();
                }else {
                    Review objReview = new Review(id, BookID, uId, review, rating, totalRating);

                    dialog.setTitle("Submitting");
                    dialog.show();

                    //save
                    reference.child("Reviews")
                            .child(objReview.getBookId())
                            .child(objReview.getReviewId())
                            .setValue(objReview)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete()){
                                        dialog.dismiss();
                                        Toast.makeText(SubmitReview.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();

                                        ratingBar.setRating(0);
                                        editReview.setText("");
                                        startActivity(new Intent(SubmitReview.this, OrderedBooks.class));
                                        finish();
                                    }else{
                                        dialog.dismiss();
                                        Toast.makeText(SubmitReview.this, "", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

    }
}