package com.example.bookstoreappt.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstoreappt.Model.Complaint;
import com.example.bookstoreappt.Model.Seller;
import com.example.bookstoreappt.Model.User;
import com.example.bookstoreappt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminComplaintAdapter extends RecyclerView.Adapter<AdminComplaintAdapter.MyViewHolder> {

    //data members
    Context context;
    ArrayList<Complaint> arrayList;

    DatabaseReference reference;
    ProgressDialog dialog;

    //constructor
    public AdminComplaintAdapter(Context context, ArrayList<Complaint> arrayList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint_customer, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Complaint complaint = arrayList.get(position);

        //set data to card view
        holder.tvTitle.setText(complaint.getTitle());
        holder.tvComplaint.setText(complaint.getComplaint());
//        holder.tvBuyer.setText(complaint.getBuyerId());
//        holder.tvSeller.setText(complaint.getSellerId());
        holder.tvTimestamp.setText(complaint.getTimestamp());

        if (complaint.getStatus().equals("Resolved")){
            holder.tvStatus.setTextColor(Color.BLUE);
            holder.tvStatus.setText(complaint.getStatus());
        }else{
            holder.tvStatus.setTextColor(Color.RED);
            holder.tvStatus.setText(complaint.getStatus());
        }

//        dialog.setTitle("Loading");
//        dialog.show();

        //set buyer and seller name
        reference.child("Users").child(complaint.getBuyerId()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            dialog.dismiss();

                            User user = new User();
                            user = snapshot.getValue(User.class);

                            holder.tvBuyer.setText(user.getName());
                            holder.tvSeller.setTextColor(Color.BLUE);

                        }else{
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
        reference.child("Sellers").child(complaint.getSellerId()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            dialog.dismiss();

                            Seller seller = new Seller();
                            seller = snapshot.getValue(Seller.class);

                            holder.tvSeller.setText(seller.getName());
                            holder.tvSeller.setTextColor(Color.RED);

                        }else{
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

        holder.tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        //declaration card objects
        TextView  tvTitle, tvComplaint, tvBuyer, tvSeller, tvStatus, tvTimestamp, tvView, tvAddToCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //initialization car object
            tvTitle = itemView.findViewById(R.id.tvCardTitle);
            tvComplaint = itemView.findViewById(R.id.tvCardComplaint);
            tvBuyer = itemView.findViewById(R.id.tvCardBuyer);
            tvSeller = itemView.findViewById(R.id.tvCardSeller);
            tvStatus = itemView.findViewById(R.id.tvCardStatus);
            tvTimestamp = itemView.findViewById(R.id.tvCardTimestamp);

            tvView = itemView.findViewById(R.id.tvCardView);
            tvAddToCart = itemView.findViewById(R.id.tvCardAddCart);
        }
    }
}
