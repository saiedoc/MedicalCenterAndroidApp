package com.example.clinic;
import ViewModels.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;


import data.ApiInterface;
import data.ClientJsonApi;
import pojo.Doctor;
import pojo.Patient;
import pojo.User;
import pojo.UserAccount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private AnimationDrawable loadingAnimation;
    private ImageView loadingImage;
    private static int SPLASH_TIME_OUT = 1000;
    private LoginViewModel loginViewModel;
    private ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private int profileID;
    private String name;
    int status;
    private PatientViewModel patientViewModel = new PatientViewModel();
    private DoctorViewModel doctorViewModel = new DoctorViewModel();
    private SplashActivity splashActivity = this;


    private void findViews() {
        loadingImage = findViewById(R.id.loading_image);
    }

    private void setupSharedPrefreneces() {
        sharedPrefs = getSharedPreferences("My App", MODE_PRIVATE);
        editor = sharedPrefs.edit();
    }

    private void StartLoadingAnimation() {
        loadingAnimation = (AnimationDrawable) loadingImage.getDrawable();
        loadingAnimation.start();
    }

    private void loadLoginAcivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadGUI(int id, int status, int role, int profileID, String name) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class).putExtra("status", status);
        intent.putExtra("id", id);
        intent.putExtra("role", role);
        intent.putExtra("profileID", profileID);
        intent.putExtra("name", name);
        startActivity(intent);
        finish();

    }

    private void checkNetStatus(int id,int role) {

        clientJsonApi.checkAccount(id).enqueue(new Callback<UserAccount>() {
            @Override
            public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                UserAccount userAccount = response.body();
                if (Integer.valueOf(userAccount.getUserid()).equals(null)) {
                    status = -1;
                    loadGUI(id, status, role, profileID, name);
                } else {
                    status = 1;
                    loadGUI(id, status, role, profileID, name);
                }
            }

            @Override
            public void onFailure(Call<UserAccount> call, Throwable t) {
                status = 0;
                loadGUI(id, status, role, profileID, name);
            }
        });

    }

    private void SetLoginStatus() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {


                int id = sharedPrefs.getInt("account_id", -1);
                int role = sharedPrefs.getInt("role", -1);
                profileID = sharedPrefs.getInt("profileID",-1);
                name = sharedPrefs.getString("name","no name");
                if (id == -1) {
                    loadLoginAcivity();
                } else {

                    switch (role) {

                        case 3:
                            patientViewModel.getPatient(id);
                            patientViewModel.getPatientLiveData().observe(splashActivity, new Observer<Patient>() {
                                @Override
                                public void onChanged(Patient patient) {
                                    profileID = patient.getPatient_id();
                                    name = patient.getName();
                                    if (profileID == 0)
                                        loadLoginAcivity();
                                    else {
                                       checkNetStatus(id,role);

                                    }
                                }
                            });
                            break;

                        case 2:
                            doctorViewModel.getDoctorLiveData(id,splashActivity);
                            doctorViewModel.getDoctorMutableLiveData().observe(splashActivity, new Observer<Doctor>() {
                                @Override
                                public void onChanged(Doctor doctor) {
                                    profileID = doctor.getDoctor_id();
                                    name = doctor.getName();
                                    if (profileID== 0)
                                        loadLoginAcivity();
                                    else {
                                        checkNetStatus(id,role);
                                    }

                                }
                            });
                            break;


                        case 1:
                            name = "Admin";
                            checkNetStatus(id,role);
                            break;

                    }

                    checkNetStatus(id,role);

                }

            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViews();
        setupSharedPrefreneces();
        StartLoadingAnimation();
        SetLoginStatus();


    }
}