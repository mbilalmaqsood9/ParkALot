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
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonReset;
    private TextView textViewSignUpOrLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        init();
        listeners();
    }

    private void init() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        textViewSignUpOrLogin = (TextView) findViewById(R.id.textViewSignUpOrLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    private void listeners() {
        textViewSignUpOrLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOpenWelcomActivity = new Intent(ResetPasswordActivity.this, WelcomeActivity.class);
                startActivity(intentOpenWelcomActivity);
                finish();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    public void resetPassword(){
        String email = editTextEmail.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("This field cannot be empty");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this, "Check your email to reset the password", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ResetPasswordActivity.this, "Something went wrong! Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}