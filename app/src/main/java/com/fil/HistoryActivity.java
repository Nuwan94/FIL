package com.fil;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fil.Common.FireB;
import com.fil.Model.CartItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<CartItem> data;
    private ProgressDialog progressDialog;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setTitle("Purchase History");

        data = new ArrayList<>();
        rv = findViewById(R.id.rvHistory);
        progressDialog = ProgressDialog.show(this, "", "Loading...", true);

        FireB.getHistoryReference().child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                data.clear();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    singleSnapshot.getRef().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                CartItem item = singleSnapshot.getValue(CartItem.class);
                                data.add(item);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Collections.reverse(data);

                HistoryAdapter adapter = new HistoryAdapter(getBaseContext(), data);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
}

class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<CartItem> data;
    private Context mContext;

    public HistoryAdapter(Context context, List<CartItem> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_single_history_view, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MyHolder myHolder = (MyHolder) holder;
        final CartItem current = data.get(position);

        myHolder.name.setText(current.getName());
        myHolder.price.setText(current.getPrice());
        String desc = "Color : " + current.getColor() + " | Material : " + current.getMaterial() + " | Tailor : " + current.getTailor();
        myHolder.desc.setText(desc);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name, price, desc;

        MyHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtHistoryItemName);
            price = itemView.findViewById(R.id.txtHistoryItemPrice);
            desc = itemView.findViewById(R.id.txtHistoryItemDesc);
        }

    }
}
