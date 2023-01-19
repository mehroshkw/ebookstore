package com.example.bookstoreappt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.bookstoreappt.Admin.AdminLogin;
import com.example.bookstoreappt.Admin.AdminMain;
import com.example.bookstoreappt.Customer.CustomerLogin;
import com.example.bookstoreappt.Customer.CustomerMain;
import com.example.bookstoreappt.Customer.CustomerProfile;
import com.example.bookstoreappt.Model.Seller;
import com.example.bookstoreappt.Model.User;
import com.example.bookstoreappt.Seller.SellerBooksManage;
import com.example.bookstoreappt.Seller.SellerLogin;
import com.example.bookstoreappt.Seller.SellerMain;
import com.example.bookstoreappt.Seller.SellerRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //declaration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Select Module");

//        startActivity(new Intent(MainActivity.this, AdminMain.class));
//        finish();
    }

    public void gotoAdminLogin(View view) {
        startActivity(new Intent(MainActivity.this, AdminLogin.class));
        finish();
    }
    public void gotoSellerLogin(View view) {
        startActivity(new Intent(MainActivity.this, SellerLogin.class));
        finish();
    }
    public void gotoBuyerLogin(View view) {
        startActivity(new Intent(MainActivity.this, CustomerLogin.class));
        finish();
    }
}