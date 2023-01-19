package com.example.bookstoreappt.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstoreappt.Admin.AdminCategoryEdit;
import com.example.bookstoreappt.Admin.AdminCategoryManage;
import com.example.bookstoreappt.Model.Category;
import com.example.bookstoreappt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminCategoryAdapter extends RecyclerView.Adapter<AdminCategoryAdapter.MyViewHolder> {

    //data members
    Context context;
    ArrayList<Category> arrayList;

    DatabaseReference reference;
    ProgressDialog dialog;

    //constructor
    public AdminCategoryAdapter(Context context, ArrayList<Category> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        reference = FirebaseDatabase.getInstance("https://book-store-appt-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        dialog = new ProgressDialog(context);
        dialog.setCancelable(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //add layout to recycler view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_admin, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = arrayList.get(position);

        //set data to card view
        holder.tvCategory.setText(category.getTitle().toUpperCase());

        //apply click listener
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdminCategoryEdit.class);
                intent.putExtra("CategoryID", category.getCatID());
                (context).startActivity(intent);
                ((Activity)context).finish();
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setTitle("Deleting");
                dialog.show();

//                Toast.makeText(context, "" +category.getCategoryID(), Toast.LENGTH_SHORT).show();
                //update Contact status
                reference.child("Categories").child(category.getCatID()).setValue(null)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    dialog.dismiss();
                                    Toast.makeText(context, "Record deleted", Toast.LENGTH_SHORT).show();

                                    (context).startActivity(new Intent(context, AdminCategoryManage.class));
                                    ((Activity)context).finish();

                                }else{
                                    dialog.dismiss();
                                    Toast.makeText(context, "Record not deleted", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //declaration card objects
        TextView  tvCategory, tvEdit, tvDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //initialization car object
            tvCategory = itemView.findViewById(R.id.tvCardCategory);

            tvEdit = itemView.findViewById(R.id.tvCardEdit);
            tvDelete = itemView.findViewById(R.id.tvCardAddCart);
        }
    }
}
