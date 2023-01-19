package com.example.bookstoreappt.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bookstoreappt.Adapter.CustomerOrderAdapter;
import com.example.bookstoreappt.Model.Order;
import com.example.bookstoreappt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerOrdersPending extends AppCompatActivity {

    //declaration
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog dialog;

    RecyclerView recyclerView;

    ArrayList<Order> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_orders_pending);
        setTitle("Orders List");

        //initialization
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        arrayList = new ArrayList<>();
        arrayList.clear();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CustomerOrdersPending.this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        dialog.setTitle("Loading");
        dialog.show();

        arrayList.clear();

        reference.child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    dialog.dismiss();

                    Order order = new Order();

                    for (DataSnapshot temp : snapshot.getChildren()){
                        order = temp.getValue(Order.class);

                        //adding book to arraylist
                        if (order.getOrderStatus().equals("Pending") &&
                                order.getBuyerId().equals(auth.getCurrentUser().getUid())){
                            arrayList.add(order);
                        }

                    }

                    //initialize adapter and set to recycler view
                    CustomerOrderAdapter adapter = new CustomerOrderAdapter(CustomerOrdersPending.this, arrayList);
                    recyclerView.setAdapter(adapter);

                }else{
                    dialog.dismiss();
                    Toast.makeText(CustomerOrdersPending.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}