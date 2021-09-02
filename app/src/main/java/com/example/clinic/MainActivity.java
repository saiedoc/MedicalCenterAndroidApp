package com.example.clinic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Date;

import data.ClientJsonApi;
import data.MyFCM;
import off_room.AdminRoom;
import off_room.DoctorRoom;
import off_room.PatientRoom;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    View HeaderView;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public static Toolbar toolbar;
    static Context context;
    Fragment mainFragment;
    int currentPatientProfileID;
    int currentDoctorProfileId;
    int currentAppointmentID;
    int account_id;
    String token;
    int role;
    int doctor_id;
    int patient_id;
    int admin_id;
    int netStatus;
    LocalDate currentAppointmentDate;
    int currentSessionID;
    int currentAttachmentID;
    String currentAttachmentName;
    String sDoctorName;
    String sPatientName;
    TextView DoctorName;
    TextView PatientName;



    public int getCurrentAttachmentID() {
        return currentAttachmentID;
    }

    public void setCurrentAttachmentID(int currentAttachmentID) {
        this.currentAttachmentID = currentAttachmentID;
    }

    private void setNetStatus(){

        netStatus = getIntent().getIntExtra("status",2);
        if ( netStatus == 2 || netStatus == -1){
            Toast.makeText(this,"Connection Problem",Toast.LENGTH_LONG).show();
            LogOut();
        }

    }

    public int getNetStatus(){
        return netStatus;
    }

    public int getPatient_ID(){
        return patient_id;
    }

    public int getAdmin_id(){
        return admin_id;
    }

    public void setCurrentAppointmentDate(LocalDate date){
        currentAppointmentDate = date;
    }

    public LocalDate getCurrentAppointmentDate() {
        return currentAppointmentDate;
    }

    public String getCurrentAttachmentName() {
        return currentAttachmentName;
    }

    public void setCurrentAttachmentName(String currentAttachmentName) {
        this.currentAttachmentName = currentAttachmentName;
    }

    public int getCurrentSessionID() {
        return currentSessionID;
    }

    public int getCurrentDoctorProfileId() {
        return currentDoctorProfileId;
    }

    public void setCurrentDoctorProfileId(int currentDoctorProfileId) {
        this.currentDoctorProfileId = currentDoctorProfileId;
    }

    public void setCurrentSessionID(int currentSessionID) {
        this.currentSessionID = currentSessionID;
    }

    public int getCurrentAppointmentID() {
        return currentAppointmentID;
    }

    public void setCurrentAppointmentID(int currentAppointmentID) {
        this.currentAppointmentID = currentAppointmentID;
    }

    public int getCurrentPatientProfileID() {
        return currentPatientProfileID;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    private void getRole(){role = getIntent().getIntExtra("role",-1);}

    public int getRoleID(){return role;}

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setCurrentPatientProfileID(int currentPatientProfileID) {
        this.currentPatientProfileID = currentPatientProfileID;
    }

    public TextView getDoctorName() {
        return DoctorName;
    }

    public void findViews(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        switch(role){

            case 1:
                navigationView.inflateHeaderView(R.layout.admin_header);
                HeaderView = navigationView.getHeaderView(0);
                PatientName = HeaderView.findViewById(R.id.NavAdminName);
                System.out.println("patient name : " + sPatientName);
                PatientName.setText(sPatientName);
                NavHostFragment.create(R.navigation.admin_graph);
            break;
            case 2:
                navigationView.inflateHeaderView(R.layout.doctor_header);
                HeaderView = navigationView.getHeaderView(0);
                DoctorName = HeaderView.findViewById(R.id.NavDocName);
                DoctorName.setText(sDoctorName);
                break;
            case 3:
                navigationView.inflateHeaderView(R.layout.patient_header);
                HeaderView = navigationView.getHeaderView(0);
                PatientName = HeaderView.findViewById(R.id.NavPatientName);
                PatientName.setText(sPatientName);
                NavHostFragment.create(R.navigation.patient_graph);
                break;


        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    public void getData(){

        switch (role) {
            case 1:
               admin_id = account_id;
            break;
            case 2:
                doctor_id = getIntent().getIntExtra("profileID",-1);
                sDoctorName = getIntent().getStringExtra("name");
            break;
            case 3:
                patient_id = getIntent().getIntExtra("profileID",-1);
                sPatientName = getIntent().getStringExtra("name");
            break;
        }
    }

    private void sendToken(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", MyFCM.getToken());
        jsonObject.addProperty("profile_id",getIntent().getIntExtra("profileID",-1));
        if(getRoleID() == 1)
            jsonObject.addProperty("role","ADMIN");
        else if(getRoleID() == 2)
            jsonObject.addProperty("role","DOCTOR");
        else if(getRoleID() == 3)
            jsonObject.addProperty("role","PATIENT");

        ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

        clientJsonApi.sendToken(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    System.out.println("token sent.");
                }
                else{
                    System.out.println(" token error : " + response.code() );
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("token not sent/");
            }
        });

    }

    private void setupFirebase(){

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(task.isSuccessful())
                {
                    token = task.getResult().getToken();
                    Log.d("Token","Token is : " + token);
                    Log.d("Token","is successful from on Complete");
                }
                else {
                    Log.d("Token","is not generated");

                }
            }
        });

    }

    public void setAccountID(){


    account_id = getIntent().getIntExtra("id",-1);
    }

    public void setupNavigationView(){
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
        toolbar.inflateMenu(R.menu.drop_menu);
        toolbar.setOnMenuItemClickListener(this);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        switch(role){
            case 1:
                NavController navController3 = Navigation.findNavController(this, R.id.NavHostFragment);
                navController3.setGraph(R.navigation.admin_graph);
                NavigationUI.setupWithNavController(navigationView, navController3);
                navigationView.inflateMenu(R.menu.admin_main_menu);
                //setCheckedItem
                break;
            case 2:
                NavController navController1 = Navigation.findNavController(this, R.id.NavHostFragment);
                navController1.setGraph(R.navigation.main);
                NavigationUI.setupWithNavController(navigationView, navController1);
                navigationView.inflateMenu(R.menu.doctor_main_menu);
                navigationView.setCheckedItem(R.id.doctorProfile);
                break;
            case 3:
                NavController navController2 = Navigation.findNavController(this, R.id.NavHostFragment);
                navController2.setGraph(R.navigation.patient_graph);
                NavigationUI.setupWithNavController(navigationView, navController2);
                navigationView.inflateMenu(R.menu.patient_main_menu);
                navigationView.setCheckedItem(R.id.patient_profile);
                break;
        }

    }


    private void selectCorrectLayout(){

        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFirebase();
        setNetStatus();
        getRole();
        sendToken();
        setAccountID();
        getData();
        selectCorrectLayout();
        context = this;
        findViews();
        setupNavigationView();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static Context getContext(){
        return context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        LogoutDialog logoutDialog = new LogoutDialog();
        logoutDialog.show(getSupportFragmentManager(), "123");
        return false;
    }

    public void ClearDatabase(){

        if(role == 1)
            AdminRoom.getInstance(context).clearAllTables();
        else if(role == 3)
            PatientRoom.getInstance(context).clearAllTables();
        else if(role == 2)
            DoctorRoom.getInstance(context).clearAllTables();

    }

    public void LogOut(){

        ClearDatabase();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}