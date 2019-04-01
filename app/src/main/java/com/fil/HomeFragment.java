package com.fil;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fil.Common.Common;
import com.fil.Model.Product;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView rv;
    private List<Product> data;
    private ProgressDialog progressDialog;

    public HomeFragment() {
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        data = new ArrayList<>();
        rv = v.findViewById(R.id.rvProducts);

        progressDialog = ProgressDialog.show(v.getContext(), "","Loading...", true);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Product product = singleSnapshot.getValue(Product.class);
                    data.add(product);
                }

                ProductAdapter adapter = new ProductAdapter(v.getContext(), data);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(progressDialog!=null)
            progressDialog.dismiss();
    }
}

class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    private List<Product> data;
    private Context mContext;

    public ProductAdapter(Context context, List<Product> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_single_list_view, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MyHolder myHolder = (MyHolder) holder;
        final Product current = data.get(position);
        myHolder.name.setText(current.getName());
        myHolder.price.setText(current.getPrice());
        myHolder.description.setText(current.getDescription());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name, price, description;
        ImageView picture;

        MyHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtSingleItemName);
            price = itemView.findViewById(R.id.txtSingleItemPrice);
            description = itemView.findViewById(R.id.txtSingleItemDescription);
            picture = itemView.findViewById(R.id.imgSingleItemPhoto);
        }

    }
}
