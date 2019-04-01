package com.fil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fil.Common.Common;
import com.fil.Common.FireB;
import com.fil.Model.CartItem;
import com.fil.Model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv;
    private List<CartItem> data;
    private ProgressDialog progressDialog;
    private Button cartCanel, cartBuyNow;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        startActivity(upIntent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartBuyNow = findViewById(R.id.btnCartBuyNow);
        cartCanel = findViewById(R.id.btnCartCancel);

        cartBuyNow.setOnClickListener(this);
        cartCanel.setOnClickListener(this);

        data = new ArrayList<>();
        rv = findViewById(R.id.rvCart);
        progressDialog = ProgressDialog.show(this, "", "Loading...", true);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FireB.getCartReference().child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    CartItem item = singleSnapshot.getValue(CartItem.class);
                    item.setColor(item.getColor() + "," + singleSnapshot.getKey());
                    data.add(item);
                }

                CartAdapter adapter = new CartAdapter(getBaseContext(), data);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                setTitle("Shopping Cart (" + data.size() + ")");

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnCartBuyNow:
                Intent iBuyNow = new Intent(this, PurchaseActivity.class);
                startActivity(iBuyNow);
                break;

            case R.id.btnCartCancel:
                onBackPressed();
                break;

        }
    }
}

class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<CartItem> data;
    private Context mContext;

    public CartAdapter(Context context, List<CartItem> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_single_cart_view, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MyHolder myHolder = (MyHolder) holder;
        final CartItem current = data.get(position);

        final String key = current.getColor().split(",")[1];
        current.setColor(current.getColor().split(",")[0]);

        myHolder.name.setText(current.getName());
        myHolder.price.setText(current.getPrice());
        Picasso.get().load(current.getImage()).into(myHolder.picture);

        String desc = "Color : " + current.getColor() + "\nMaterial : " + current.getMaterial() + " \n" + current.getTailor();

        View.OnClickListener deleteItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.showToast(mContext, "Item Deleted");
                FireB.getCartReference().child(FirebaseAuth.getInstance().getUid()).child(key).removeValue();
            }
        };

        myHolder.description.setText(desc);
        myHolder.delete.setOnClickListener(deleteItem);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name, price, description;
        ImageView picture, delete;

        MyHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtSingleItemName);
            price = itemView.findViewById(R.id.txtCartItemPrice);
            picture = itemView.findViewById(R.id.imgCartItemPhoto);
            description = itemView.findViewById(R.id.txtCartItemDescription);
            delete = itemView.findViewById(R.id.imgCardItemDelete);
        }

    }
}
