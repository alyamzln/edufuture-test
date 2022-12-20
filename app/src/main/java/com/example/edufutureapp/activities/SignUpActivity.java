package com.example.edufutureapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edufutureapp.R;
import com.example.edufutureapp.utilities.Constants;
import com.example.edufutureapp.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {


    private EditText emailEt,passwordEt1,passwordEt2,firstNameEt,lastNameEt;
    private Button SignUpButton;
    private TextView SignInTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private PreferenceManager preferenceManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        preferenceManager = new PreferenceManager(getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();
        firstNameEt = findViewById(R.id.firstname);
        lastNameEt = findViewById(R.id.lastname);
        emailEt = findViewById(R.id.email);
        passwordEt1 = findViewById(R.id.password1);
        passwordEt2 = findViewById(R.id.password2);
        SignUpButton = findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);
        SignInTv = findViewById(R.id.logInTv);
        SignUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (firstNameEt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Enter first name", Toast.LENGTH_SHORT).show();
                } else if (lastNameEt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Enter last name", Toast.LENGTH_SHORT).show();
                } else if (emailEt.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEt.getText().toString()).matches()) {
                    Toast.makeText(SignUpActivity.this, "Enter invalid email", Toast.LENGTH_SHORT).show();
                } else if (passwordEt1.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else if (passwordEt2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Confirm password", Toast.LENGTH_SHORT).show();
                } else if (!passwordEt1.getText().toString().equals(passwordEt2.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "Password and confirm password must be the same", Toast.LENGTH_SHORT).show();
                } else {
                    Register();
                }
            }
        });

        SignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Register(){
        //Firebase Firestore
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_FIRST_NAME, firstNameEt.getText().toString());
        user.put (Constants. KEY_LAST_NAME, lastNameEt.getText().toString());
        user.put (Constants. KEY_EMAIL, emailEt.getText().toString());
        user.put(Constants. KEY_PASSWORD, passwordEt1.getText().toString());

        database.collection(Constants. KEY_COLLECTION_USERS)

                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                        preferenceManager.putString(Constants.KEY_FIRST_NAME,firstNameEt.getText().toString());
                        preferenceManager.putString(Constants.KEY_LAST_NAME,lastNameEt.getText().toString());
                        preferenceManager.putString(Constants.KEY_EMAIL,emailEt.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Sign up fail!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }});}}

