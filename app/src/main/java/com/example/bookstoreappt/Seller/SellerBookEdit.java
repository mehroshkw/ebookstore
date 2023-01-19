package com.example.bookstoreappt.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstoreappt.Model.Book;
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

import java.util.ArrayList;

public class SellerBookEdit extends AppCompatActivity {

    //declarations
    EditText editTitle,  editAuthor, editEdition, editDate, editCompany, editPrice ;
    Spinner spinnerGenre, spinnerStatus;

    Button btnUpdate;
    TextView tvBack;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog dialog;

    ArrayList<String> arrayListGenre;

    String BOOK_ID;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_book_edit);
        setTitle("Edit Book Info");

        //initialization
        book = new Book();
        BOOK_ID = getIntent().getStringExtra("BookID");

        arrayListGenre = new ArrayList<>();
        arrayListGenre.clear();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        editTitle = findViewById(R.id.editTitle);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        editAuthor = findViewById(R.id.editAuthor);
        editEdition = findViewById(R.id.editEdition);
        editDate = findViewById(R.id.editDate);
        editCompany = findViewById(R.id.editCompany);
        editPrice = findViewById(R.id.editPrice);
        spinnerStatus = findViewById(R.id.spinnerStatus);

        btnUpdate = findViewById(R.id.btnUpdate);
        tvBack = findViewById(R.id.textBack);


        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerBookEdit.this, SellerMain.class));
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editTitle.getText().toString();
                String author = editAuthor.getText().toString();
                String edition = editEdition.getText().toString();
                String date = editDate.getText().toString();
                String company = editCompany.getText().toString();
                String price = editPrice.getText().toString();

                String genre = spinnerGenre.getSelectedItem().toString();
                String status = spinnerStatus.getSelectedItem().toString();

                //validate
                if (title.isEmpty()){
                    editTitle.setError("Required");
                    editTitle.requestFocus();
                }else if (author.isEmpty()){
                    editAuthor.setError("Required");
                    editAuthor.requestFocus();
                }else if (edition.isEmpty()){
                    editEdition.setError("Required");
                    editEdition.requestFocus();
                }else if (date.isEmpty()){
                    editDate.setError("Required");
                    editDate.requestFocus();
                }else if (company.isEmpty()){
                    editCompany.setError("Required");
                    editCompany.requestFocus();
                }else if (price.isEmpty()){
                    editPrice.setError("Required");
                    editPrice.requestFocus();
                }else{
                    dialog.setTitle("Saving");
                    dialog.show();

                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setGenre(genre);
                    book.setEdition(edition);
                    book.setCompany(company);
                    book.setPrice(price);
                    book.setStatus(status);

                    reference.child("Books").child(book.getBookId()).setValue(book)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(SellerBookEdit.this, "Book Updated", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(SellerBookEdit.this, SellerMain.class));
                                finish();
                            }else {
                                dialog.dismiss();
                                Toast.makeText(SellerBookEdit.this, "Book Not Updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();

        dialog.setTitle("Loading Book Genre");
        dialog.show();

        arrayListGenre.clear();

        reference.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()> 0 ){
                    dialog.dismiss();

                    for (DataSnapshot temp : snapshot.getChildren()){
                        Category category = new Category();
                        category = temp.getValue(Category.class);

                        arrayListGenre.add(category.getTitle());

                    }
//                    Toast.makeText(SellerBookEdit.this, ""+arrayListGenre, Toast.LENGTH_LONG).show();
                    //set values to spinner
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(SellerBookEdit.this, android.R.layout.simple_spinner_dropdown_item, arrayListGenre );
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerGenre.setAdapter(spinnerAdapter);


                    dialog.setTitle("Loading Book Info");
                    dialog.show();

                    reference.child("Books").child(BOOK_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getChildrenCount()>0){
                                dialog.dismiss();

                                book = snapshot.getValue(Book.class);

//                    Toast.makeText(SellerBookEdit.this, "" +book.toString(), Toast.LENGTH_SHORT).show();

                                //display values to form
                                editTitle.setText(book.getTitle());

                    String compareValue = book.getGenre();
                    spinnerGenre.setSelection(((ArrayAdapter<String>)spinnerGenre.getAdapter()).getPosition(compareValue));

                                editAuthor.setText(book.getAuthor());
                                editEdition.setText(book.getEdition());
                                editDate.setText(book.getReleaseDate());
                                editCompany.setText(book.getCompany());
                                editPrice.setText(book.getPrice());

                    String compareStatus = book.getStatus();
                    spinnerStatus.setSelection(((ArrayAdapter<String>)spinnerStatus.getAdapter()).getPosition(compareStatus));

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}