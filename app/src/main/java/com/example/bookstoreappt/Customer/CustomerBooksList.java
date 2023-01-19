package com.example.bookstoreappt.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bookstoreappt.Adapter.CustomerBookAdapter;
import com.example.bookstoreappt.Adapter.SellerBookAdapter;
import com.example.bookstoreappt.Model.Book;
import com.example.bookstoreappt.R;
import com.example.bookstoreappt.Seller.SellerBooksManage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerBooksList extends AppCompatActivity {

    //declaration
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressDialog dialog;

    RecyclerView recyclerView;

    ArrayList<Book> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_books_list);
        setTitle("Books List");

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
        recyclerView.setLayoutManager(new LinearLayoutManager(CustomerBooksList.this));
    }
    //display menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_books_customer, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchResult(newText);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);    }

    private void searchResult(String newText) {
        ArrayList<Book> searchArrayList = new ArrayList<>();
        searchArrayList.clear();

        for (Book searchResult : arrayList){
            try{
                if (searchResult.getTitle().toLowerCase().contains(newText.toLowerCase()) ||
                    searchResult.getAuthor().toLowerCase().contains(newText.toLowerCase()) ||
                    searchResult.getGenre().toLowerCase().contains(newText.toLowerCase())||
                    searchResult.getCompany().toLowerCase().contains(newText.toLowerCase())
                ){
                    searchArrayList.add(searchResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //display result
            CustomerBookAdapter adapter = new CustomerBookAdapter(CustomerBooksList.this, searchArrayList);
            recyclerView.setAdapter(adapter);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        dialog.setTitle("Loading");
        dialog.show();

        arrayList.clear();

        reference.child("Books").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>0){
                    dialog.dismiss();

                    Book book = new Book();

                    for (DataSnapshot temp : snapshot.getChildren()){
                        book = temp.getValue(Book.class);

                        //adding book to arraylist
                        arrayList.add(book);

                    }

                    //initialize adapter and set to recycler view
                    CustomerBookAdapter adapter = new CustomerBookAdapter(CustomerBooksList.this, arrayList);
                    recyclerView.setAdapter(adapter);

                }else{
                    dialog.dismiss();
                    Toast.makeText(CustomerBooksList.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    //adding click listener for menu options
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_cart:
                startActivity(new Intent(CustomerBooksList.this, CustomerCart.class));
//                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}