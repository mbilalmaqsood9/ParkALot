package com.example.parkinglot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    public String scannedText;
    SharedPreferences sharedPreferences;
    AlertDialog.Builder builder;
    TextView textViewHelperText;

    String parkingLotId;
    String startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        sharedPreferences = getSharedPreferences("ParkingLotPrefs", Context.MODE_PRIVATE);
        scannerView = (ZXingScannerView) findViewById(R.id.scanner);
        textViewHelperText = (TextView) findViewById(R.id.textViewHelperText);

        parkingLotId = sharedPreferences.getString("id","nothing");
        startTime = sharedPreferences.getString("time","nothing");

        if (!parkingLotId.equals("nothing")){
            textViewHelperText.setText("Scan To End Booking");
        }

        scan();
    }




    public void scan(){
        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.setResultHandler(ScanActivity.this);
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

                String registration = HomeFragment.selected;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                String currentDateandTime = sdf.format(new Date());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id",parkingLotID);
                editor.putString("time",currentDateandTime);
                editor.putString("registration",registration);
                editor.commit();


                Intent intent = new Intent(ScanActivity.this,MasterActivity.class);
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

    public void endBooking(){

        builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to end parking?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                String currentDateandTime = sdf.format(new Date());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id","nothing");
                editor.putString("time","nothing");
                editor.commit();


                Intent intent = new Intent(ScanActivity.this,MasterActivity.class);
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

    public void insertParking(){

    }

    @Override
    public void handleResult(Result rawResult) {
        scannedText = rawResult.getText();

        if (parkingLotId.equals("nothing")){
            startBooking(scannedText);
        }else if (parkingLotId.equals(scannedText)){
            endBooking();
        }else{
            Toast.makeText(this, "Codes do not match", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ScanActivity.this,MasterActivity.class);
            intent.putExtra("fragmentName",1);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,new HomeFragment());
        fragmentTransaction.commit();
    }


}