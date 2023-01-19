package com.example.bookstoreappt.Customer;

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

import com.example.bookstoreappt.Model.User;
import com.example.bookstoreappt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerRegister extends AppCompatActivity {

    //declaration
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    EditText editName, editPhone, editAddress, editEmail, editPassword;
    Button btnLogin, btnRegister;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
        setTitle("New Buyer Registration");


        //initialization
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editAddress = findViewById(R.id.editAddress);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerRegister.this, CustomerLogin.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get form data
                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();
                String address = editAddress.getText().toString();
                String email = editEmail.getText().toString().toLowerCase();
                String pass = editPassword.getText().toString();
                String usertype = "Buyer";


                //validate
                if (name.isEmpty()){
                    editName.setError("required");
                    editName.requestFocus();
                }else  if (phone.isEmpty()){
                    editPhone.setError("required");
                    editPhone.requestFocus();
                }else  if (address.isEmpty()){
                    editAddress.setError("required");
                    editAddress.requestFocus();
                }else  if (email.isEmpty()){
                    editEmail.setError("required");
                    editEmail.requestFocus();
                }else if (!email.contains("@") || !email.contains(".")){
                    editEmail.setError("Invalid Email");
                    editEmail.requestFocus();
                }else if (pass.isEmpty()){
                    editPassword.setError("Required");
                    editPassword.requestFocus();
                }else if (pass.length() < 6){
                    editPassword.setError("Use 6 characters");
                    editPassword.requestFocus();
                }else{
                    dialog.setTitle("Registering");
                    dialog.show();

                    User user = new User(name, phone, address, email, pass, usertype);

                    //Register
                    auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();

                                user.setUserId(auth.getCurrentUser().getUid());

                                saveUser(user);
                            }else{
                                dialog.dismiss();
                                Toast.makeText(CustomerRegister.this, "User Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

    private void saveUser(User user) {
        dialog.setTitle("Save");
        dialog.show();

        reference.child("Users").child(user.getUserId()).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();

                            Toast.makeText(CustomerRegister.this, "Customer/Buyer Registration Successful", Toast.LENGTH_SHORT).show();

                            //move to login
                            Intent intent = new Intent(CustomerRegister.this, CustomerLogin.class);
                            startActivity(intent);
                            finish();
                        }else{
                            dialog.dismiss();
                            Toast.makeText(CustomerRegister.this, "Customer/Buyer Details not Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}