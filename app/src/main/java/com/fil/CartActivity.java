package com.fil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    Button cartCanel, cartBuyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartBuyNow = findViewById(R.id.btnCartBuyNow);
        cartCanel = findViewById(R.id.btnCartCancel);

        cartBuyNow.setOnClickListener(this);
        cartCanel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnCartBuyNow:
                Intent iBuyNow = new Intent(this,PurchaseActivity.class);
                startActivity(iBuyNow);
                break;

            case  R.id.btnCartCancel:
                finish();
                break;

        }
    }
}
