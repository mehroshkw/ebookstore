package com.example.bookstoreappt.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookstoreappt.Adapter.CustomerCartBookAdapter;
import com.example.bookstoreappt.Model.CartBook;
import com.example.bookstoreappt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerCart extends AppCompatActivity {

    //declaration
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog dialog;

    RecyclerView recyclerView;

    ArrayList<CartBook> arrayList;

    Button btnOrder;
    int Qty =0, OrderAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_cart);
        setTitle("My Cart");

        //initialization
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        arrayList = new ArrayList<>();
        arrayList.clear();

        btnOrder = findViewById(R.id.btnOrder);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CustomerCart.this));

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(CustomerCart.this, ""+OrderAmount, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CustomerCart.this, CustomerPlaceOrder.class);
                intent.putExtra("OrderQty", String.valueOf(Qty));
                intent.putExtra("OrderAmount", String.valueOf(OrderAmount));
                startActivity(intent);
//                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        dialog.setTitle("Loading");
        dialog.show();

        arrayList.clear();
//        Qty = 0;
//        OrderAmount = 0;

        reference.child("Cart").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    dialog.dismiss();

                    CartBook cartBook = new CartBook();
                    btnOrder.setVisibility(View.VISIBLE);

                    for (DataSnapshot temp : snapshot.getChildren()){
                        cartBook = temp.getValue(CartBook.class);

                        Qty = Qty + Integer.parseInt(cartBook.getQty());
                        OrderAmount = OrderAmount + Integer.parseInt(cartBook.getPrice());

                        //adding book to arraylist
                        arrayList.add(cartBook);


                    }
//                    Toast.makeText(CustomerCart.this, "Qty: " +Qty, Toast.LENGTH_SHORT).show();

                    //initialize adapter and set to recycler view
                    CustomerCartBookAdapter adapter = new CustomerCartBookAdapter(CustomerCart.this, arrayList);
                    recyclerView.setAdapter(adapter);

                }else{
                    dialog.dismiss();
                    btnOrder.setVisibility(View.INVISIBLE);
                    Toast.makeText(CustomerCart.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}