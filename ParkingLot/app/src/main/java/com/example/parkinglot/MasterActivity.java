package com.example.parkinglot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MasterActivity extends AppCompatActivity implements View.OnClickListener {

    private DuoDrawerLayout drawerLayout;
    private LinearLayout linearLayoutHome, linearLayoutProfile, linearLayoutBookingHistory,
            linearLayoutSupprt, linearLayoutSettings, linearLayoutLogout, linearLayoutAddVehicle;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    String userId;

    TextView textViewUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        
        init();
        initUserData();


    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        View contentView = drawerLayout.getContentView();
        View menuView = drawerLayout.getMenuView();

        linearLayoutHome = (LinearLayout) findViewById(R.id.linearLayoutHome);
        linearLayoutProfile = (LinearLayout) findViewById(R.id.linearLayoutProfile);
        linearLayoutBookingHistory = (LinearLayout) findViewById(R.id.linearLayoutBookingHistory);
        linearLayoutSupprt = (LinearLayout) findViewById(R.id.linearLayoutSupport);
        linearLayoutSettings = (LinearLayout) findViewById(R.id.linearLayoutSettings);
        linearLayoutLogout = (LinearLayout) findViewById(R.id.linearLayoutLogout);
        linearLayoutAddVehicle = (LinearLayout) findViewById(R.id.linearLayoutAddVehicle);
        textViewUserName = (TextView) findViewById(R.id.textViewUserName);



        linearLayoutHome.setOnClickListener(this);
        linearLayoutProfile.setOnClickListener(this);
        linearLayoutBookingHistory.setOnClickListener(this);
        linearLayoutSupprt.setOnClickListener(this);
        linearLayoutSettings.setOnClickListener(this);
        linearLayoutLogout.setOnClickListener(this);
        linearLayoutAddVehicle.setOnClickListener(this);


        replaceFragment(new HomeFragment());


        if(getIntent().getIntExtra("fragmentName",0)==1){
            replaceFragment(new CurrentParkingFragment());
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.linearLayoutHome:
                replaceFragment(new HomeFragment(),"Home");
                drawerLayout.closeDrawer();
                break;

            case R.id.linearLayoutProfile:
                replaceFragment(new ProfileFragment(),"Profile");
                drawerLayout.closeDrawer();
                break;

            case R.id.linearLayoutBookingHistory:
                replaceFragment(new BookingHistoryFragment(),"Booking History");
                drawerLayout.closeDrawer();
                break;

            case R.id.linearLayoutSupport:
                replaceFragment(new SupportFragment(),"Support");
                drawerLayout.closeDrawer();
                break;

            case R.id.linearLayoutSettings:
                //Intent intent = new Intent(MasterActivity.this, ResetPasswordActivity.class);
                //startActivity(intent);
                //finish();
                replaceFragment(new SettingsFragment());
                drawerLayout.closeDrawer();
                break;

            case R.id.linearLayoutLogout:
                logout();
                break;

            case R.id.linearLayoutAddVehicle:
                replaceFragment(new AddVehicleFragment());
                drawerLayout.closeDrawer();
                break;

        }


    }

    private void replaceFragment(Fragment fragment, String s) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.addToBackStack(s);
        fragmentTransaction.commit();


    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();


    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intentOpenWelcomActivity = new Intent(this,WelcomeActivity.class);
        startActivity(intentOpenWelcomActivity);
        finish();
    }

    @Override
    public void onBackPressed() {

    }

    private void initUserData(){

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

                    textViewUserName.setText(fullName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}