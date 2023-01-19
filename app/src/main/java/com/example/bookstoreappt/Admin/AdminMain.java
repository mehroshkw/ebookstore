package com.example.bookstoreappt.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bookstoreappt.MainActivity;
import com.example.bookstoreappt.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminMain extends AppCompatActivity {

    FirebaseAuth auth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        setTitle("Admin Dashboard");

        //initialization
        auth = FirebaseAuth.getInstance();


    }

    public void adminGoLogout(View view) {
        //signing out
        auth.signOut();

        startActivity(new Intent(AdminMain.this, MainActivity.class));
        finish();
    }
    public void adminGoProfile(View view) {
        startActivity(new Intent(AdminMain.this, AdminCategoryManage.class));
//        finish();
    }
    public void adminGoBooks(View view) {
        startActivity(new Intent(AdminMain.this, AdminBookList.class));
//        finish();
    }
    public void adminGoMngCategory(View view) {
        startActivity(new Intent(AdminMain.this, AdminCategoryManage.class));
//        finish();
    }
    public void adminGoPendingOrders(View view) {
        startActivity(new Intent(AdminMain.this, AdminPendingOrders.class));
//        finish();
    }
    public void adminGoOrders(View view) {
        startActivity(new Intent(AdminMain.this, AdminOrdersHistory.class));
//        finish();
    }
    public void adminGoComplaints(View view) {
        startActivity(new Intent(AdminMain.this, AdminComplaintsManage.class));
//        finish();
    }

}