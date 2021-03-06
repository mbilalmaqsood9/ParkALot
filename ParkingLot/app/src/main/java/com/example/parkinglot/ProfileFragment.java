package com.example.parkinglot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    String userId;

    TextView textViewFullName;

    EditText editTextFirstName, editTextLastName, editTextEmail, editTextPhoneNumber, editTextCnic, editTextPassword, editTextConfirmPassword;

    Button buttonUpdateProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewFullName = view.findViewById(R.id.textViewFullName);
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber);
        editTextCnic = view.findViewById(R.id.editTextCnic);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextConfirmPassword = view.findViewById(R.id.editTextConfirmPassword);
        buttonUpdateProfile = view.findViewById(R.id.buttonUpdateProfile);


        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Profile Cannot be updated", Toast.LENGTH_SHORT).show();
            }
        });
        init();



        return view;

    }

    private void init(){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userId = firebaseUser.getUid();

        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user != null){
                    String fullName = user.first_name + " " + user.last_name;
                    String email = user.email;
                    String phone = user.phone_number;
                    String cnic = user.cnic;

                    textViewFullName.setText(fullName);

                    editTextFirstName.setText(user.first_name);
                    editTextLastName.setText(user.last_name);
                    editTextEmail.setText(email);
                    editTextCnic.setText(cnic);
                    editTextPhoneNumber.setText(phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}