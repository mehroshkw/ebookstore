package com.example.bookstoreappt.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bookstoreappt.Admin.AdminOrdersHistory;
import com.example.bookstoreappt.Admin.AdminPendingOrders;
import com.example.bookstoreappt.R;
import com.google.firebase.auth.FirebaseAuth;

public class SellerMain extends AppCompatActivity {

    //declaration
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);
        setTitle("Seller Dashboard");

        //initialization
        auth = FirebaseAuth.getInstance();

    }

    public void sellerGoLogout(View view) {
        auth.signOut();

        startActivity(new Intent(SellerMain.this, SellerLogin.class));
        finish();
    }
    public void sellerGoProfile(View view) {

        startActivity(new Intent(SellerMain.this, SellerProfile.class));
        finish();
    }
    public void sellerGoMngBooks(View view) {

        startActivity(new Intent(SellerMain.this, SellerBooksManage.class));
//        finish();
    }
    public void sellerGoPendingOrders(View view) {

        startActivity(new Intent(SellerMain.this, AdminPendingOrders.class));
//        finish();
    }
    public void sellerGoOrders(View view) {

        startActivity(new Intent(SellerMain.this, AdminOrdersHistory.class));
//        finish();
    }

    public void gotoBuyerLogin(View view) {
    }
}