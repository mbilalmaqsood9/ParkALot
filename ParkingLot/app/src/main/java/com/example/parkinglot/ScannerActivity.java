package com.example.parkinglot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.controls.Control;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    public String scannedText;
    SharedPreferences sharedPreferences;
    AlertDialog.Builder builder;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        sharedPreferences = getSharedPreferences("ParkingLotPrefs", Context.MODE_PRIVATE);
        scannerView = (ZXingScannerView) findViewById(R.id.scanner);


        scan();


    }

    public void scan(){
        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.setResultHandler(ScannerActivity.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    public void startBooking(String parkingLotID){
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to start parking at this spot with ID :" +parkingLotID+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                String currentDateandTime = sdf.format(new Date());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id",parkingLotID);
                editor.putString("time",currentDateandTime);
                editor.commit();


                Intent intent = new Intent(ScannerActivity.this,MasterActivity.class);
                intent.putExtra("fragmentName",1);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void handleResult(Result rawResult) {
        scannedText = rawResult.getText();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PakringLotPrefs",Context.MODE_PRIVATE);
        String parkingLotId = sharedPreferences.getString("id","0");
        String startTime = sharedPreferences.getString("time","0");

        if (parkingLotId.equals("null") && startTime.equals("null")){
            startBooking(scannedText);
        }else{
            endBooking();
        }



        Toast.makeText(this, scannedText, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.startCamera();
    }

    public void endBooking(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String endTime = sdf.format(new Date());
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PakringLotPrefs",Context.MODE_PRIVATE);
        String parkingLotId = sharedPreferences.getString("id","0");
        String startTime = sharedPreferences.getString("time","0");

        insertParking(endTime,parkingLotId,startTime);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id","null");
        editor.putString("time","null");
        editor.commit();

    }

    public void insertParking(String end_time, String parking_space_id, String start_time){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Parkings");
        String userId = firebaseUser.getUid();
        String hourly_rate_charged = "0";
        String vehicle_id = "0";
        Parking parking = new Parking(parking_space_id, userId,vehicle_id,start_time,end_time,hourly_rate_charged,end_time,end_time,"0","0");
        databaseReference.push().setValue(parking);
    }
}