package com.example.bookstoreappt.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bookstoreappt.Adapter.AdminOrderAdapter;
import com.example.bookstoreappt.Model.Order;
import com.example.bookstoreappt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminOrdersHistory extends AppCompatActivity {

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
        setContentView(R.layout.activity_admin_orders_history);
        setTitle("Orders in Process.");

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
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminOrdersHistory.this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        dialog.setTitle("Loading");
        dialog.show();

        arrayList.clear();

        reference.child("Orders").orderByChild("status").equalTo("Delivered").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    dialog.dismiss();

                    Order order = new Order();

                    for (DataSnapshot temp : snapshot.getChildren()){
                        order = temp.getValue(Order.class);

                        if (order.getOrderStatus().equals("Delivered")){
                            arrayList.add(order);
                        }
                    }

                    //initialize adapter and set to recycler view
                    AdminOrderAdapter adapter = new AdminOrderAdapter(AdminOrdersHistory.this, arrayList);
                    recyclerView.setAdapter(adapter);

                }else{
                    dialog.dismiss();
                    Toast.makeText(AdminOrdersHistory.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}