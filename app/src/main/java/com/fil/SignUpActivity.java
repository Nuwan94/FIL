package com.fil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    public TextInputEditText firstName;
    public TextInputEditText lastName;
    public TextInputEditText userName;
    public TextInputEditText contactNumber;
    public TextInputEditText email;
    public TextInputEditText securityQuestionAns;
    public TextInputEditText password;
    public TextInputEditText reEnterpassword;

    public Spinner securityQuestion;

    public Button btnCancel;
    public Button btnCreateAccount;

    private static final String TAG = "Register";

    private ProgressDialog regProgress;

    private DatabaseReference userDatabase;

    public DatabaseReference FILUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // regProgress = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        firstName = findViewById(R.id.textInputFirstName);
        lastName = findViewById(R.id.textInputLastName);
        userName = findViewById(R.id.textInputUserName);
        contactNumber = findViewById(R.id.textInputContactNumber);
        email = findViewById(R.id.textInputEmail);
        securityQuestionAns = findViewById(R.id.textInputAnswer);

        securityQuestion = findViewById(R.id.spinnerSecurityQuestions);

        password =findViewById(R.id.textInputPassword);
        reEnterpassword =findViewById(R.id.textInputConfirmPassword);

        btnCancel = findViewById(R.id.buttonCancel);
        btnCreateAccount = findViewById(R.id.buttonCreateAccount);


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userDisplayName = userName.getEditableText().toString();
                String userEmail = email.getEditableText().toString();
                String userPassword = password.getEditableText().toString();
                String userConfPW = reEnterpassword.getEditableText().toString();

                if(!TextUtils.isEmpty(userDisplayName) ||
                        !TextUtils.isEmpty(userEmail) ||
                        !TextUtils.isEmpty(userPassword)||
                        !TextUtils.isEmpty(userConfPW)
                        ){
                    RegisterUser(userDisplayName,userEmail,userPassword);

//                    regProgress.setTitle("Register Under Progress");
//                    regProgress.setMessage("Please wait for a moment untill the account create");
//                    regProgress.setCanceledOnTouchOutside(false);
//                    regProgress.show();
                }

            }
        });
    }

    private void RegisterUser(final String userDisplayName, String userEmail, String userPassword) {

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = currentUser.getUid();

                            userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String, String> userDetailMap = new HashMap<>();


                            String username = userName.getEditableText().toString();
                            String fname = firstName.getEditableText().toString();
                            String lname = lastName.getEditableText().toString();
                            String contactNo = contactNumber.getEditableText().toString();
                            String answer = securityQuestionAns.getEditableText().toString();

                            userDetailMap.put("UserName",username);

                            userDetailMap.put("FirstName",fname);

                            userDetailMap.put("LastName",lname);
                            userDetailMap.put("ContactNumber",contactNo);
                            userDetailMap.put("Answer",answer);

                            userDatabase.setValue(userDetailMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //regProgress.dismiss();

                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        Intent mainActivityLaunchIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                        mainActivityLaunchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                        startActivity(mainActivityLaunchIntent);
                                        finish();
                                    }
                                }
                            });

                        } else {
                            //regProgress.hide();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            //updateUI(null);
                        }

                        // ...
                    }

                });
    }
}
