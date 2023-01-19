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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminCategoryEdit extends AppCompatActivity {

    String CAT_ID;
    Category category;

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
        setContentView(R.layout.activity_admin_category_edit);
        setTitle("Update Book Genre");

        //initialization
        category = new Category();

        CAT_ID = getIntent().getStringExtra("CategoryID");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        editTitle = findViewById(R.id.editTitle);
        btnSave = findViewById(R.id.btnUpdate);
        tvBack = findViewById(R.id.textBack);


        //apply click listener
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //read input
                String title = editTitle.getText().toString();

                //validate
                if (title.isEmpty()){
                    editTitle.setError("Required");
                    editTitle.requestFocus();
                }else{
                    category.setTitle(title);

                    dialog.setTitle("Updating");
                    dialog.show();

                    reference.child("Categories").child(category.getCatID()).setValue(category).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(AdminCategoryEdit.this, "Book Genre Updated", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(AdminCategoryEdit.this, AdminCategoryManage.class));
                                finish();
                            }else {
                                dialog.dismiss();
                                Toast.makeText(AdminCategoryEdit.this, "Book Genre Not Updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });                }
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryEdit.this, AdminCategoryManage.class));
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        dialog.setTitle("Loading...");
        dialog.show();

        reference.child("Categories").child(CAT_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()> 0){
                    dialog.dismiss();

                    category = snapshot.getValue(Category.class);

                    //display book genre
                    editTitle.setText(category.getTitle());
                }else{
                    dialog.dismiss();
                    Toast.makeText(AdminCategoryEdit.this, "No recrod found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}