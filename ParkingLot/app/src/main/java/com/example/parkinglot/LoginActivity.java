package com.example.parkinglot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewSignUp, textViewForgotPassword;
    private Button buttonLogin;
    private EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        listeners();


    }

    private void init(){
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        textViewForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();
    }
    private void listeners(){
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOpenRegisterActivity = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intentOpenRegisterActivity);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();

            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOpenResetPasswordActivity = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intentOpenResetPasswordActivity);
            }
        });
    }


    public void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {



                if(task.isSuccessful()){

                    // move after email verification fixed
                    Intent intentOpenDashboardActivity = new Intent(LoginActivity.this, MasterActivity.class);
                    startActivity(intentOpenDashboardActivity);
                    finish();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()){
                        // open dashboard here
                    }else{
                        Toast.makeText(LoginActivity.this, "Please check your email to verify your account", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "Failed to Login", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}