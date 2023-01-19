package com.example.bookstoreappt.Seller;

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

import com.example.bookstoreappt.MainActivity;
import com.example.bookstoreappt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLogin extends AppCompatActivity {

    //declaration
    EditText editEmail, editPassword;
    Button btnLogin, btnRegister;
    TextView tvBack;

    ProgressDialog dialog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_seller);
        setTitle("Seller Login");//set activity Label

        //initialization
        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        tvBack = findViewById(R.id.tvBack);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister  = findViewById(R.id.btnRegister);

        editEmail  = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        //apply click listener
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerLogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerLogin.this, SellerRegister.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get form data
                String email = editEmail.getText().toString().toLowerCase();
                String pass = editPassword.getText().toString();

                //valiate
                if (email.isEmpty()){
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
                    dialog.setTitle("Validating email/password...");
                    dialog.show();

                    //login
                    auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(SellerLogin.this, "Seller Login Successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SellerLogin.this, SellerMain.class);
                                startActivity(intent);
                                finish();
                            }else{
                                dialog.dismiss();
                                Toast.makeText(SellerLogin.this, "Seller Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

}