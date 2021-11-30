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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView textViewSignIn;
    EditText editTextFirstName, editTextLastName, editTextPhoneNumber, editTextCnic, editTextEmail, editTextPassword, editTextConfirmPassword;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        editTextCnic = (EditText) findViewById(R.id.editTextCnic);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
            }
        });

        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOpenLoginActivity = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intentOpenLoginActivity);
                finish();
            }
        });


    }

    public void registerUser(){
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String cnic = editTextCnic.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (firstName.isEmpty()){
            editTextFirstName.setError("First Name is required");
            editTextFirstName.requestFocus();
            return;
        }
        if (lastName.isEmpty()){
            editTextLastName.setError("Last Name is required");
            editTextLastName.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty()){
            editTextPhoneNumber.setError("Phone Number is required");
            editTextPhoneNumber.requestFocus();
            return;
        }
        if (cnic.isEmpty()){
            editTextCnic.setError("CNIC is required");
            editTextCnic.requestFocus();
            return;
        }
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
        if (confirmPassword.isEmpty()){
            editTextConfirmPassword.setError("Confirm Password is required");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.length() < 7){
            editTextPassword.setError("Password must be of at least 8 characters");
            editTextPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)){
            editTextConfirmPassword.setError("Passwords do not match");
            editTextConfirmPassword.requestFocus();
            return;
        }

        Calendar calendar = Calendar.getInstance();
        String currentDateTime = DateFormat.getDateInstance().format(calendar.getTime());

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                User user = new User(firstName,lastName,email,phoneNumber,cnic,currentDateTime,currentDateTime,"0");

                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            FirebaseUser fUser = mAuth.getCurrentUser();
                            fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RegisterActivity.this, "Verification email has been sent", Toast.LENGTH_LONG).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                                }
                            });

                            Toast.makeText(RegisterActivity.this, "User inserted", Toast.LENGTH_SHORT).show();
                            Intent intentOpenLoginActivity = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intentOpenLoginActivity);
                            finish();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "User not inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}