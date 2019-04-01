package com.fil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.Common.Common;
import com.fil.Common.FireB;
import com.fil.Model.User;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout defaultLayout, profileLayout, editProfileLayout, layoutSettingsChangePassword;
    Button editProfile, viewProfile, purchase, defaultBack, profileBack, editProfileBack, editProfileSave;

    private TextView username, firstName, lastName, email, contact;
    private EditText editUsername, editFirstName, editLastName, editEmail, editContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Account Settings");

        viewProfile = findViewById(R.id.btnSettingsViewProfile);
        editProfile = findViewById(R.id.btnSettingsEditProfile);
        purchase = findViewById(R.id.btnSettingsPurchaseHistory);
        defaultBack = findViewById(R.id.btnSettingsBack);
        profileBack = findViewById(R.id.btnSettingsBackProfile);
        editProfileBack = findViewById(R.id.btnSettingsBackEditProfile);
        editProfileSave = findViewById(R.id.btnSettingsSaveEditProfile);

        defaultLayout = findViewById(R.id.layoutSettingsDefault);
        profileLayout = findViewById(R.id.layoutSettingsProfileView);
        editProfileLayout = findViewById(R.id.layoutSettingsEditProfileView);
        layoutSettingsChangePassword =findViewById(R.id.layoutSettingsChangePassword);

        viewProfile.setOnClickListener(this);
        editProfile.setOnClickListener(this);
        defaultBack.setOnClickListener(this);
        purchase.setOnClickListener(this);
        profileBack.setOnClickListener(this);
        editProfileBack.setOnClickListener(this);
        editProfileSave.setOnClickListener(this);

        username = findViewById(R.id.txtSettingsProfileViewUsername);
        firstName = findViewById(R.id.txtSettingsProfileViewFirstName);
        lastName = findViewById(R.id.txtSettingsProfileViewLastName);
        email = findViewById(R.id.txtSettingsProfileViewEmail);
        contact = findViewById(R.id.txtSettingsProfileViewContact);

        editUsername = findViewById(R.id.txtSettingsEditProfileUsername);
        editFirstName = findViewById(R.id.txtSettingsEditProfileFirstName);
        editLastName = findViewById(R.id.txtSettingsEditProfileLastName);
        editContact = findViewById(R.id.txtSettingsEditProfileContact);
        editEmail = findViewById(R.id.txtSettingsEditProfileEmail);

        showLayout(R.id.layoutSettingsDefault);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnSettingsViewProfile:
                username.setText(Common.currentUser.getUserName());
                firstName.setText(Common.currentUser.getFirstName());
                lastName.setText(Common.currentUser.getLastName());
                email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                contact.setText(Common.currentUser.getContactNumber());
                showLayout(R.id.layoutSettingsProfileView);
                break;

            case R.id.btnSettingsEditProfile:
                editUsername.setText(Common.currentUser.getUserName());
                editFirstName.setText(Common.currentUser.getFirstName());
                editLastName.setText(Common.currentUser.getLastName());
                editEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                editContact.setText(Common.currentUser.getContactNumber());
                showLayout(R.id.layoutSettingsEditProfileView);
                break;

            case R.id.btnSettingsPurchaseHistory:
                Intent iHistory = new Intent(this, PurchaseActivity.class);
                startActivity(iHistory);
                break;

            case R.id.btnSettingsBackProfile:
            case R.id.btnSettingsBackEditProfile:
                showLayout(R.id.layoutSettingsDefault);
                break;

            case R.id.btnSettingsSaveEditProfile:
                User user = Common.currentUser;

                String username = editUsername.getText().toString();
                String firstName = editFirstName.getText().toString();
                String lastName = editLastName.getText().toString();
                String contact = editContact.getText().toString();
                String email = editEmail.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(email)){

                }else{
                    user.setUserName(username);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setContactNumber(contact);
                    FireB.getUserReference().child(FirebaseAuth.getInstance().getUid()).setValue(user);
                    Common.currentUser = user;
                }

                break;

            case R.id.btnSettingsBack:
                finish();
                break;
        }
    }

    private void showLayout(int layoutID) {
        defaultLayout.setVisibility(View.GONE);
        profileLayout.setVisibility(View.GONE);
        editProfileLayout.setVisibility(View.GONE);
        layoutSettingsChangePassword.setVisibility(View.GONE);

        switch (layoutID){
            case R.id.layoutSettingsDefault:
                defaultLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.layoutSettingsProfileView:
                profileLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.layoutSettingsEditProfileView:
                editProfileLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.layoutSettingsChangePassword:
                layoutSettingsChangePassword.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }
}
