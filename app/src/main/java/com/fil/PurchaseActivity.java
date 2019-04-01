package com.fil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fil.Common.FireB;
import com.fil.Model.CartItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PurchaseActivity extends AppCompatActivity implements View.OnClickListener {

    TextView total;

    Button btnPurchaseCancel, btnPurchaseConfirm;

    double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        setTitle("Checkout");

        total = findViewById(R.id.txtPurchaseTotalAmount);
        btnPurchaseCancel = findViewById(R.id.btnPurchaseCancel);
        btnPurchaseConfirm = findViewById(R.id.btnPurchaseConfirm);

        totalAmount = 0.0;

        FireB.getCartReference().child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    CartItem cartItem = singleSnapshot.getValue(CartItem.class);
                    totalAmount += Double.valueOf(cartItem.getPrice());
                }
                total.setText("LKR " + String.valueOf(totalAmount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnPurchaseCancel.setOnClickListener(this);
        btnPurchaseConfirm.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        startActivity(upIntent);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPurchaseCancel:
                finish();
                break;

            case R.id.btnPurchaseConfirm:
                FireB.getCartReference().child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        FireB.getHistoryReference().child(FirebaseAuth.getInstance().getUid()).push().setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if (databaseError == null) {
                                    FireB.getCartReference().child(FirebaseAuth.getInstance().getUid()).setValue(null);
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                onBackPressed();
                break;
        }
    }
}
