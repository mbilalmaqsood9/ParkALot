package com.example.parkinglot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class CurrentParkingFragment extends Fragment {

    TextView textViewParkingLotName, textViewStartTime, textViewRegistrationNumber;
    String id, time, regis;
    Button buttonEndParking;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_parking, container, false);

        textViewParkingLotName = view.findViewById(R.id.textViewParkingLotName);
        textViewStartTime = view.findViewById(R.id.textViewStartTime);
        textViewRegistrationNumber = view.findViewById(R.id.textViewRegistrationNumber);
        buttonEndParking = view.findViewById(R.id.buttonEndParking);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ParkingLotPrefs", Context.MODE_PRIVATE);

        id = sharedPreferences.getString("id","nothing");
        time = sharedPreferences.getString("time","nothing");
        regis = sharedPreferences.getString("registration","nothing");

        if (id.equals("nothing")){
            buttonEndParking.setText("Go To Home");
            Intent intentOpenScannerActivity = new Intent(getActivity(),MasterActivity.class);
            startActivity(intentOpenScannerActivity);
        }

        textViewParkingLotName.setText(id);
        textViewStartTime.setText(time);
        textViewRegistrationNumber.setText(regis);

        buttonEndParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.equals("nothing")){
                    replaceFragment(new HomeFragment());
                }else{
                    Intent intentOpenScannerActivity = new Intent(getActivity(),ScanActivity.class);
                    startActivity(intentOpenScannerActivity);
                }

            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();


    }




}