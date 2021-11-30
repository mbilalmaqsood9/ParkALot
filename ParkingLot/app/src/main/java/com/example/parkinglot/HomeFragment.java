package com.example.parkinglot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    CardView cardViewScanNow, cardViewAdvanceBooking;

    String id,time;

    String items[]= {"AB-6472","ANS-514","AME-765"};
    Spinner spinnerCity;
    public static String selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cardViewScanNow = view.findViewById(R.id.cardViewScanNow);
        cardViewAdvanceBooking = view.findViewById(R.id.cardViewAdvanceBooking);
        spinnerCity = view.findViewById(R.id.simpleSpinner);

        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(aa);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = items[position];
                //Toast.makeText(getActivity(), items[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ParkingLotPrefs", Context.MODE_PRIVATE);

        id = sharedPreferences.getString("id","nothing");
        time = sharedPreferences.getString("time","nothing");


        cardViewScanNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (id.equals("nothing")){
                    Intent intentOpenScannerActivity = new Intent(getActivity(),ScanActivity.class);
                    startActivity(intentOpenScannerActivity);
                }else{
                    replaceFragment(new CurrentParkingFragment());
                }



            }
        });

        cardViewAdvanceBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.equals("nothing")){
                    replaceFragment(new SearchParkingLotFragment());
                }else{
                    replaceFragment(new CurrentParkingFragment());
                    Toast.makeText(getActivity(), "End Current Parking First", Toast.LENGTH_LONG).show();
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