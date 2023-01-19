package com.example.bookstoreappt.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bookstoreappt.Adapter.AdminCategoryAdapter;
import com.example.bookstoreappt.Model.Category;
import com.example.bookstoreappt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminCategoryManage extends AppCompatActivity {

    //declaration
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog dialog;

    RecyclerView recyclerView;

    ArrayList<Category> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category_manage);
        setTitle("Books Genre List");

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
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminCategoryManage.this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        dialog.setTitle("Loading");
        dialog.show();

        arrayList.clear();

        reference.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    dialog.dismiss();

                    Category category = new Category();

                    for (DataSnapshot temp : snapshot.getChildren()){
                        category = temp.getValue(Category.class);
                        arrayList.add(category);
                    }

                    //initialize adapter and set to recycler view
                    AdminCategoryAdapter adapter = new AdminCategoryAdapter(AdminCategoryManage.this, arrayList);
                    recyclerView.setAdapter(adapter);

                }else{
                    dialog.dismiss();
                    Toast.makeText(AdminCategoryManage.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void adminGoAddCategory(View view) {
        startActivity(new Intent(AdminCategoryManage.this, AdminCategoryNew.class));
        finish();
    }
}