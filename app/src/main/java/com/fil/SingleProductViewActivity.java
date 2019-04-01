package com.fil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.fil.Common.Common;
import com.fil.Common.FireB;
import com.fil.Model.CartItem;
import com.fil.Model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class SingleProductViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image;
    private Button addToCart, buyNow, goToCart, continueShopping;
    private LinearLayout choose, added;

    private Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_view);

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");

        setTitle(product.getName());

        image = findViewById(R.id.imgProductView);
        Picasso.get().load(product.getImage()).into(image);


        addToCart = findViewById(R.id.btnProductAddToCart);
        buyNow = findViewById(R.id.btnProductBuyNow);
        goToCart = findViewById(R.id.btnProductGoToCart);
        continueShopping = findViewById(R.id.btnProductShopping);
        choose = findViewById(R.id.layoutProductChoose);
        added = findViewById(R.id.layoutProductAdded);

        addToCart.setOnClickListener(this);
        buyNow.setOnClickListener(this);
        goToCart.setOnClickListener(this);
        continueShopping.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProductAddToCart:
                choose.setVisibility(View.GONE);
                added.setVisibility(View.VISIBLE);

                String sleeveStyle = ((Spinner) findViewById(R.id.spinnerSleeveStyle)).getSelectedItem().toString();
                String collarSize = ((Spinner) findViewById(R.id.spinnerCollarSize)).getSelectedItem().toString();
                String material = ((Spinner) findViewById(R.id.spinnerMaterial)).getSelectedItem().toString();
                String color = ((Spinner) findViewById(R.id.spinnerColor)).getSelectedItem().toString();
                String collarType = ((Spinner) findViewById(R.id.spinnerCollarType)).getSelectedItem().toString();
                String tailor = ((Spinner) findViewById(R.id.spinnerTailor)).getSelectedItem().toString();

                CartItem cartItem = new CartItem(product, sleeveStyle, collarSize, material, color, collarType, tailor);

                FireB.getCartReference().child(FirebaseAuth.getInstance().getUid()).push().setValue(cartItem);

                break;
            case R.id.btnProductBuyNow:
                choose.setVisibility(View.GONE);
                Common.showToast(this, "Brought.");
                finish();
                break;
            case R.id.btnProductGoToCart:
                Intent iCart = new Intent(this, CartActivity.class);
                startActivity(iCart);
                break;
            case R.id.btnProductShopping:
                finish();
                break;
        }
    }
}
