package com.fil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class SignInActivity extends AppCompatActivity {

    public TextInputEditText userInputEmail;
    public TextInputEditText userInputPassward;

    public Button btnCancel;
    public Button btnSignUp;

    private DatabaseReference userDatabase;

    private ProgressDialog logProgressDialog;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        userInputEmail = findViewById(R.id.textInputEmail);
        userInputPassward = findViewById(R.id.textInputPassword);

        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users");



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = userInputEmail.getEditableText().toString();
                String password = userInputPassward.getEditableText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                    loginUser(email, password);
                }
            }
        });



//                    logProgressDialog.setTitle("Loging In");
//                    logProgressDialog.setMessage("Please wait for a moment");
//                    logProgressDialog.setCanceledOnTouchOutside(false);


    }

    private void loginUser (String email,String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    logProgressDialog.dismiss();
                    String currentUserID = mAuth.getCurrentUser().getUid();
                    String deviceTokenID = FirebaseInstanceId.getInstance().getToken();

                    userDatabase.child(currentUserID).child("DeviceToken").setValue(deviceTokenID).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent mainIntent = new Intent(SignInActivity.this,MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(mainIntent);
                            Toast.makeText(SignInActivity.this, "Authentication succeded .",Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    });

                    // Sign in success, update UI with the signed-in user's information

                    FirebaseUser user = mAuth.getCurrentUser();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w( "signInWithEmail:failure", task.getException());
                    Toast.makeText(SignInActivity.this, "Authentication failed. Please Recheck your Email and Password",Toast.LENGTH_SHORT).show();

                }


            }
        });
    }



}