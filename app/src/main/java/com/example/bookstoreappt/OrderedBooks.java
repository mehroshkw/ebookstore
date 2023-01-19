package com.example.bookstoreappt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bookstoreappt.Adapter.AdminOrderedBookAdapter;
import com.example.bookstoreappt.Adapter.CustomerBookAdapter;
import com.example.bookstoreappt.Adapter.CustomerOrderedBookAdapter;
import com.example.bookstoreappt.Customer.CustomerBooksList;
import com.example.bookstoreappt.Customer.CustomerOrdersPending;
import com.example.bookstoreappt.Model.Book;
import com.example.bookstoreappt.Model.OrderedBook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderedBooks extends AppCompatActivity {

    //declaration
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog dialog;

    RecyclerView recyclerView;

    ArrayList<OrderedBook> arrayList;

    String OrderID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_books);
        setTitle("Ordered Books");

        //initialization
        OrderID = getIntent().getStringExtra("OrderID");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        arrayList = new ArrayList<>();
        arrayList.clear();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderedBooks.this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        dialog.setTitle("Loading");
        dialog.show();

        arrayList.clear();

        reference.child("OrderedBooks").child(OrderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    dialog.dismiss();


                    Toast.makeText(OrderedBooks.this, snapshot.getChildrenCount() +" Book/s Founds", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot temp : snapshot.getChildren()){
                        OrderedBook book = new OrderedBook();
                        book = temp.getValue(OrderedBook.class);

                        //adding book to arraylist
                        arrayList.add(book);

                    }


//                    initialize adapter and set to recycler view
                        CustomerOrderedBookAdapter adapter = new CustomerOrderedBookAdapter(OrderedBooks.this, arrayList);
                        recyclerView.setAdapter(adapter);

                }else{
                    dialog.dismiss();
                    Toast.makeText(OrderedBooks.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}