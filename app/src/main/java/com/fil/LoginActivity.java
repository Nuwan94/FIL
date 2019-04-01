package com.fil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.fil.Common.Common;
import com.fil.Common.FireB;
import com.fil.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layoutLogin, layoutSignin, layoutSignup, layoutForgot, layoutPasswordProcess, layoutPasswordSuccess;

    TextInputLayout signInLayoutPassword, signUpLayoutPassword, getSignUpLayoutConfirmPassword;

    private Button btnSigninCancel, btnSignUpCancel, btnSignInShow, btnSignUpShow,
            btnSignIn, btnSignUp, btnSignInForgot, btnForgotPasswordCancel, btnForgotPasswordSubmit, btnForgotSuccessBack;

    private FirebaseAuth mAuth;

    private final int LOGIN_LAYOUT = 1;
    private final int SIGNIN_LAYOUT = 2;
    private final int SIGNUP_LAYOUT = 3;
    private final int FORGOT_LAYOUT = 4;
    private final int LAYOUT_FORGOT_PROCESS = 5;
    private final int LAYOUT_FORGOT_SUCCESS = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        mAuth = FirebaseAuth.getInstance();

        setUpViews();
        showLayout(LOGIN_LAYOUT);
        setupClickAction();

    }

    private void setUpViews() {

        btnSignInShow = findViewById(R.id.btnSignInShow);
        btnSignUpShow = findViewById(R.id.btnSignUpShow);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSigninCancel = findViewById(R.id.btnSignInCancel);
        btnSignInForgot = findViewById(R.id.btnSigninForgot);
        btnForgotPasswordCancel = findViewById(R.id.btnForgotPasswordCancel);
        btnForgotPasswordSubmit = findViewById(R.id.btnForgotPasswordSubmit);
        btnForgotSuccessBack = findViewById(R.id.btnForgotSuccessBack);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUpCancel = findViewById(R.id.btnSignUpCancel);

        layoutLogin = findViewById(R.id.layoutLogin);
        layoutSignin = findViewById(R.id.layoutSignIn);
        layoutSignup = findViewById(R.id.layoutSignUp);
        layoutForgot = findViewById(R.id.layoutForgotPassword);
        layoutPasswordProcess = findViewById(R.id.layoutPasswordResetProcess);
        layoutPasswordSuccess = findViewById(R.id.layoutPasswordResetSuccess);

        signInLayoutPassword = findViewById(R.id.tilSigninPassword);
        signUpLayoutPassword = findViewById(R.id.tilRegisterPassword);
        getSignUpLayoutConfirmPassword = findViewById(R.id.tilRegisterConfirmPassword);

        signUpLayoutPassword.setPasswordVisibilityToggleEnabled(true);
        signInLayoutPassword.setPasswordVisibilityToggleEnabled(true);
        getSignUpLayoutConfirmPassword.setPasswordVisibilityToggleEnabled(true);



    }

    private void setupClickAction() {

        btnSignUpShow.setOnClickListener(this);
        btnSignInShow.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnSigninCancel.setOnClickListener(this);
        btnSignInForgot.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnSignUpCancel.setOnClickListener(this);
        btnForgotPasswordSubmit.setOnClickListener(this);
        btnForgotPasswordCancel.setOnClickListener(this);
        btnForgotSuccessBack.setOnClickListener(this);

    }

    private void showLayout(int layoutCode) {
        layoutLogin.setVisibility(View.GONE);
        layoutSignin.setVisibility(View.GONE);
        layoutSignup.setVisibility(View.GONE);
        layoutForgot.setVisibility(View.GONE);
        layoutPasswordSuccess.setVisibility(View.GONE);
        layoutPasswordProcess.setVisibility(View.GONE);

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
                layoutPasswordProcess.setVisibility(View.VISIBLE);
                break;
            case LAYOUT_FORGOT_SUCCESS:
                layoutForgot.setVisibility(View.VISIBLE);
                layoutPasswordSuccess.setVisibility(View.VISIBLE);
                break;
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

            case R.id.btnSignIn:
                signInUser();
                break;

            case R.id.btnSignUp:
                signUpUser();
                break;

            case R.id.btnForgotPasswordSubmit:
                forgotPasswordSubmit();
                break;

            case R.id.btnSignInCancel:
            case R.id.btnSignUpCancel:
            case R.id.btnForgotPasswordCancel:
            case R.id.btnForgotSuccessBack:
            default:
                showLayout(LOGIN_LAYOUT);

        }
    }

    private void forgotPasswordSubmit() {

        String email = ((EditText)findViewById(R.id.txtForgotPasswordEmail)).getText().toString();
//        String answer = ((EditText)findViewById(R.id.txtForgotAnswer)).getText().toString();
//        String question = ((Spinner)findViewById(R.id.spinnerForgotPasswordSecurityQuestion)).getSelectedItem().toString();

        if(TextUtils.isEmpty(email)){
            Common.showToast(this,"Please fill all the fields.");
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
        showLayout(LAYOUT_FORGOT_SUCCESS);

    }

    private void signUpUser() {
        final String firstName = ((EditText) findViewById(R.id.txtRegisterFirstName)).getText().toString();
        final String lastName = ((EditText) findViewById(R.id.txtRegisterLastName)).getText().toString();
        final String userName = ((EditText) findViewById(R.id.txtRegisterUsername)).getText().toString();
        final String contact = ((EditText) findViewById(R.id.txtRegisterContact)).getText().toString();
        String email = ((EditText) findViewById(R.id.txtRegisterEmail)).getText().toString();
        final String question = ((Spinner)findViewById(R.id.spinnerSecurityQuestions)).getSelectedItem().toString();
        final String answer = ((EditText)findViewById(R.id.txtRegisterAnswer)).getText().toString();
        String password = ((EditText) findViewById(R.id.txtRegisterPassword)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.txtRegisterConfirmPassword)).getText().toString();


        if(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(question)){
            Common.showToast(this,"Please fill all the fields.");
            return;
        }

        if(!password.equals(confirmPassword)){
            Common.showToast(this,"Password mismatch!");
           return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            User dbUser = new User(firstName,lastName,userName,contact,question,answer,"buyer");
                            Common.currentUser = dbUser;
                            FireB.getUserReference().child(user.getUid()).setValue(dbUser);
                            transferToMainActivity();
                        } else {
                            Common.showToast(getApplicationContext(),"Register Failed");
                        }
                    }
                });
    }

    private void signInUser() {

        EditText editTextEmail = findViewById(R.id.txtSigninEmail);
        EditText editTextPassword = findViewById(R.id.txtISigninPassword);

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Common.showToast(this,"Please fill all the fields.");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            transferToMainActivity();
                        } else {
                            Common.showToast(getApplicationContext(),"Sign In Failed");
                        }
                    }
                });
    }

    private void transferToMainActivity(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Common.showToast(getApplicationContext(),"Login Successfully");
        startActivity(intent);
    }

}