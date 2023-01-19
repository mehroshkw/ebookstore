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

import com.example.bookstoreappt.Model.User;
import com.example.bookstoreappt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerProfile extends AppCompatActivity {

    //declaration
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    EditText editName, editPhone, editAddress, editEmail;
    Button btnUpdate;
    TextView textBack;
    User user ;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        setTitle("Buyer Profile");

        //initialization
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();


        user = new User();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editAddress = findViewById(R.id.editAddress);
        editEmail = findViewById(R.id.editEmail);
        editEmail.setEnabled(false);

        textBack = findViewById(R.id.textBack);
        btnUpdate  = findViewById(R.id.btnUpdate);

        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerProfile.this, CustomerMain.class));
                finish();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                String address = editAddress.getText().toString();

                user.setName(name);
                user.setPhone(phone);
                user.setAddress(address);
                
                dialog.setTitle("Updating");
                dialog.show();
                
                reference.child("Users").child(user.getUserId()).setValue(user)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    dialog.dismiss();
                                    Toast.makeText(CustomerProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                    
                                    startActivity(new Intent(CustomerProfile.this, CustomerMain.class));
                                    finish();
                                }else{
                                    dialog.dismiss();
                                    Toast.makeText(CustomerProfile.this, "Profile not updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        dialog.setTitle("Loading");
        dialog.show();


        reference.child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()>0){
                    dialog.dismiss();

                    user = snapshot.getValue(User.class);

                    editAddress.setText(user.getAddress());
                    editName.setText(user.getName());
                    editPhone.setText(user.getPhone());
                    editEmail.setText(user.getEmail());
                }else{
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}