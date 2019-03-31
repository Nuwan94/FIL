package com.fil;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fil.Common.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText txtInputEmail, txtInputPassword;

    private LinearLayout layoutLogin, layoutSignin, layoutSignup, layoutForgot;

    private Button btnSigninCancel, btnSignUpCancel, btnSignInShow, btnSignUpShow,
            btnSignIn, btnSignUp, btnSignInForgot;

    private DatabaseReference userDatabase;
    private FirebaseAuth mAuth;

    private final int LOGIN_LAYOUT = 1;
    private final int SIGNIN_LAYOUT = 2;
    private final int SIGNUP_LAYOUT = 3;
    private final int FORGOT_LAYOUT = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        setUpViews();
        setupClickAction();


    }

    private void setupClickAction() {

        btnSignUpShow.setOnClickListener(this);
        btnSignInShow.setOnClickListener(this);

        btnSignIn.setOnClickListener(this);
        btnSigninCancel.setOnClickListener(this);
        btnSignInForgot.setOnClickListener(this);

        btnSignUp.setOnClickListener(this);
        btnSignUpCancel.setOnClickListener(this);


    }

    private void setUpViews() {

        btnSignInShow = findViewById(R.id.btnSignInShow);
        btnSignUpShow = findViewById(R.id.btnSignUpShow);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSigninCancel = findViewById(R.id.btnSignInCancel);
        btnSignInForgot = findViewById(R.id.btnSigninForgot);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUpCancel = findViewById(R.id.btnSignUpCancel);

        txtInputEmail = findViewById(R.id.txtInputEmail);
        txtInputPassword = findViewById(R.id.txtInputPassword);

        layoutLogin = findViewById(R.id.layoutLogin);
        layoutSignin = findViewById(R.id.layoutSignIn);
        layoutSignup = findViewById(R.id.layoutSignUp);
        layoutForgot = findViewById(R.id.layoutForgotPassword);

    }


    private void showLayout(int layoutCode) {
        layoutLogin.setVisibility(View.GONE);
        layoutSignin.setVisibility(View.GONE);
        layoutSignup.setVisibility(View.GONE);
        layoutForgot.setVisibility(View.GONE);

        switch (layoutCode) {
            case LOGIN_LAYOUT:
                layoutLogin.setVisibility(View.VISIBLE);
                break;
            case SIGNIN_LAYOUT:
                layoutSignin.setVisibility(View.VISIBLE);
                break;
            case SIGNUP_LAYOUT:
                layoutSignup.setVisibility(View.VISIBLE);
                break;
            case FORGOT_LAYOUT:
                layoutForgot.setVisibility(View.VISIBLE);
            default:
                Common.showToast(this, "Error!");

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnSignInShow:
                showLayout(SIGNIN_LAYOUT);
                break;

            case R.id.btnSignUpShow:
                showLayout(SIGNUP_LAYOUT);
                break;

            case R.id.btnSigninForgot:
                showLayout(FORGOT_LAYOUT);
                break;

            case R.id.btnSignInCancel:
            case R.id.btnSignUpCancel:
            default:
                showLayout(LOGIN_LAYOUT);

        }
    }

//    private void loginUser (String email,String password) {
//
//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//
//                    String currentUserID = mAuth.getCurrentUser().getUid();
//                    String deviceTokenID = FirebaseInstanceId.getInstance().getToken();
//
//                    userDatabase.child(currentUserID).child("DeviceToken").setValue(deviceTokenID).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
//                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(mainIntent);
//                            Toast.makeText(LoginActivity.this, "Authentication succeded .",Toast.LENGTH_SHORT).show();
//
//                            finish();
//                        }
//                    });
//
//                    // Sign in success, update UI with the signed-in user's information
//
//                    FirebaseUser user = mAuth.getCurrentUser();
//
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w( "signInWithEmail:failure", task.getException());
//                    Toast.makeText(LoginActivity.this, "Authentication failed. Please Recheck your Email and Password",Toast.LENGTH_SHORT).show();
//
//                }
//
//
//            }
//        });
//    }
//


}