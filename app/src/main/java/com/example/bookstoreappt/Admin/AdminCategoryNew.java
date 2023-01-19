package com.example.bookstoreappt.Admin;

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

import com.example.bookstoreappt.Model.Category;
import com.example.bookstoreappt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCategoryNew extends AppCompatActivity {

    //declarations
    EditText editTitle;
    TextView tvBack;
    Button btnSave;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category_new);
        setTitle("Add Book Genre");

        //initialization
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        editTitle = findViewById(R.id.editTitle);
        btnSave = findViewById(R.id.btnSave);
        tvBack = findViewById(R.id.textBack);

        //apply click listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //read input
                String id = reference.push().getKey();
                String author = auth.getCurrentUser().getUid();

                String title = editTitle.getText().toString();

                //validate
                if (title.isEmpty()){
                    editTitle.setError("Required");
                    editTitle.requestFocus();
                }else{
                    dialog.setTitle("Saving");
                    dialog.show();

                    //save to object
                    Category category = new Category(id, title, author);

                    reference.child("Categories").child(id).setValue(category).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(AdminCategoryNew.this, "Genre Saved", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(AdminCategoryNew.this, AdminCategoryManage.class));
                                finish();
                            }else {
                                dialog.dismiss();
                                Toast.makeText(AdminCategoryNew.this, "Genre Not Saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryNew.this, AdminMain.class));
                finish();
            }
        });
    }
}