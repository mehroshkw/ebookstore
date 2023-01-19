package com.example.bookstoreappt.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstoreappt.Model.Complaint;
import com.example.bookstoreappt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomerComplaintSubmit extends AppCompatActivity {

    EditText editTitle, editComplaint;
    Button btnSave;
    TextView textBack;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog dialog;

    String Seller_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_complaint_submit);
        setTitle("Complaint");

        //initialization
        Seller_ID = getIntent().getStringExtra("SellerID");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        editTitle = findViewById(R.id.editTitle);
        editComplaint = findViewById(R.id.editComplaint);

        btnSave = findViewById(R.id.btnSave);
        textBack = findViewById(R.id.textBack);

        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerComplaintSubmit.this, CustomerMain.class));
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //read input
                String id = reference.push().getKey();
                String title = editTitle.getText().toString();
                String complaint = editComplaint.getText().toString();
                String status = "Un-Resolved";

                //validate
                if (title.isEmpty()){
                    editTitle.setError("Required");
                    editTitle.requestFocus();
                }else if (complaint.isEmpty()){
                    editComplaint.setError("Required");
                    editComplaint.requestFocus();
                }else{
                    DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm");
                    String timestamp = df.format(Calendar.getInstance().getTime());

                    Complaint objComplaint = new Complaint(id, title, complaint, status,
                            auth.getCurrentUser().getUid(), Seller_ID, timestamp, null );

                    dialog.setTitle("Submitting");
                    dialog.show();

                    reference.child("Complaints").child(id).setValue(objComplaint)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        dialog.dismiss();
                                        Toast.makeText(CustomerComplaintSubmit.this, "Complaint submitted successfully", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(CustomerComplaintSubmit.this, CustomerMain.class));
                                        finish();
                                    }else{
                                        dialog.dismiss();
                                        Toast.makeText(CustomerComplaintSubmit.this, "Complaint not submitted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }
}