package com.fil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Button account, profile, purchase, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        profile = findViewById(R.id.btnSettingsProfile);
        account = findViewById(R.id.btnSettingsAccount);
        purchase = findViewById(R.id.btnSettingsPurchaseHistory);
        back = findViewById(R.id.btnSettingsBack);

        profile.setOnClickListener(this);
        account.setOnClickListener(this);
        back.setOnClickListener(this);
        purchase.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSettingsProfile:
                break;

            case R.id.btnSettingsAccount:
                break;

            case R.id.btnSettingsPurchaseHistory:

                break;

            case R.id.btnSettingsBack:
                finish();
                break;
        }
    }
}
