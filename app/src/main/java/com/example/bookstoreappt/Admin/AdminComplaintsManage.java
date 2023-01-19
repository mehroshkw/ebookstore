package com.example.bookstoreappt.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bookstoreappt.Adapter.CustomerComplaintAdapter;
import com.example.bookstoreappt.Customer.CustomerComplaintsList;
import com.example.bookstoreappt.Model.Complaint;
import com.example.bookstoreappt.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminComplaintsManage extends AppCompatActivity {

    //declaration
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog dialog;

    RecyclerView recyclerView;

    ArrayList<Complaint> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaints_manage);
        setTitle("All Complaints");

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
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminComplaintsManage.this));


    }


    @Override
    protected void onStart() {
        super.onStart();

        dialog.setTitle("Loading");
        dialog.show();

        arrayList.clear();

        reference.child("Complaints").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    dialog.dismiss();

                    Complaint complaint = new Complaint();

                    for (DataSnapshot temp : snapshot.getChildren()){
                        complaint = temp.getValue(Complaint.class);

                        //adding book to arraylist
                        arrayList.add(complaint);

                    }

                    //initialize adapter and set to recycler view
                    CustomerComplaintAdapter adapter = new CustomerComplaintAdapter(AdminComplaintsManage.this, arrayList);
                    recyclerView.setAdapter(adapter);

                }else{
                    dialog.dismiss();
                    Toast.makeText(AdminComplaintsManage.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}