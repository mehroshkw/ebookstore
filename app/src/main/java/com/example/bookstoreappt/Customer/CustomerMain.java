package com.example.bookstoreappt.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bookstoreappt.R;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerMain extends AppCompatActivity {

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        setTitle("Customer Dashboard");


        //initialization
        auth = FirebaseAuth.getInstance();


    }



    public void buyerGoLogout(View view) {
        auth.signOut();

        startActivity(new Intent(CustomerMain.this,CustomerLogin.class));
        finish();
    }
    public void buyerGoProfile(View view) {
        startActivity(new Intent(CustomerMain.this,CustomerProfile.class));
        finish();
    }

    public void buyerGoBooks(View view) {
        startActivity(new Intent(CustomerMain.this, CustomerBooksList.class));
//        finish();
    }

    public void buyerGoMyComplaints(View view) {
        startActivity(new Intent(CustomerMain.this, CustomerComplaintsList.class));
//        finish();
    }

    public void buyerGoOrderPending(View view) {
        startActivity(new Intent(CustomerMain.this, CustomerOrdersPending.class));
//        finish();
    }
}