package com.example.bookstoreappt.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookstoreappt.Model.CartBook;
import com.example.bookstoreappt.Model.Order;
import com.example.bookstoreappt.Model.OrderedBook;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CustomerPlaceOrder extends AppCompatActivity {

    EditText editName, editPhone, editAddress, editNote, editQty, editAmount;
    Button btnPlaceOrder;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    ProgressDialog dialog;

    User user;
    
    ArrayList<OrderedBook> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        setTitle("Place Your Order");

        // initialization
        user = new User();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/");
        reference = database.getReference();

        arrayList = new ArrayList<>();
        arrayList.clear();

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editAddress = findViewById(R.id.editAddress);

        editNote = findViewById(R.id.editNote);

        editQty = findViewById(R.id.editProductQty);
        editQty.setText(String.valueOf(getIntent().getStringExtra("OrderQty")));
        editQty.setEnabled(false);

        editAmount = findViewById(R.id.editAmount);
        editAmount.setText(String.valueOf(getIntent().getStringExtra("OrderAmount")));
        editAmount.setEnabled(false);

        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Saving");
                dialog.show();

                DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());


                Order order = new Order(reference.push().getKey(), auth.getCurrentUser().getUid(),
                    editName.getText().toString(), editPhone.getText().toString(),
                        editAddress.getText().toString(), editNote.getText().toString(),
                        editAmount.getText().toString(), editQty.getText().toString(),
                        "Pending", date);


                reference.child("Orders").child(order.getOrderId()).setValue(order)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    dialog.dismiss();

//                                    Toast.makeText(PlaceOrder.this, "Order placed successfully", Toast.LENGTH_SHORT).show();

                                    //save ordered books
                                    reference.child("Cart").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()){
                                                CartBook cartBook = new CartBook();

                                                for (DataSnapshot temp : snapshot.getChildren()){
                                                    cartBook = temp.getValue(CartBook.class);

                                                    OrderedBook orderedBook = new OrderedBook(
                                                            cartBook.getBookId(), cartBook.getTitle(), cartBook.getGenre(),
                                                            cartBook.getAuthor(), cartBook.getEdition(), cartBook.getReleaseDate(),
                                                            cartBook.getCompany(), cartBook.getPrice(), cartBook.getSellerID(),
                                                            cartBook.getStatus(), reference.push().getKey(), order.getOrderId(),
                                                            cartBook.getQty()
                                                    );
                                                    
                                                    arrayList.add(orderedBook);
                                                }
                                                
                                                //save ordered books
                                                reference.child("OrderedBooks").child(order.getOrderId()).setValue(arrayList)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    dialog.dismiss();
//                                                                    Toast.makeText(PlaceOrder.this, "Ordered Books Saved", Toast.LENGTH_SHORT).show();

                                                                    //clear cart books after order placed
                                                                    reference.child("Cart").child(auth.getCurrentUser().getUid()).setValue(null);

                                                                    startActivity(new Intent(CustomerPlaceOrder.this, CustomerMain.class));
                                                                    finish();
                                                                }else{
                                                                    dialog.dismiss();
                                                                    Toast.makeText(CustomerPlaceOrder.this, "Ordered books not saved", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }else{
                                    dialog.dismiss();
                                    Toast.makeText(CustomerPlaceOrder.this, "Order not placed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.child("Users").child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            user = snapshot.getValue(User.class);

                            editName.setText(user.getName());
                            editPhone.setText(user.getPhone());
                            editAddress.setText(user.getAddress());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }
}