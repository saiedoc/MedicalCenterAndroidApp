package com.example.clinic;

import ViewModels.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pojo.Doctor;
import pojo.Patient;
import pojo.Role;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;
    private String name;
    private LoginViewModel loginViewModel = LoginViewModel.getINSTANCE();
    private static Context context;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private int patient_id;
    private int doctor_id;
    private int admin_id;
    private Role role;
    private PatientViewModel patientViewModel = new PatientViewModel();
    private DoctorViewModel doctorViewModel = new DoctorViewModel();
    private LoginActivity loginActivity = this;
    ////


    public void loginClicked(View view) {

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        loginViewModel.getRole(username, password);
        observeData();



    }

    public static Context getContext() {
        return context;
    }

    private void findViews() {
        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
    }

    private void SetupSharedPrefrences() {
        sharedPrefs = getSharedPreferences("My App", MODE_PRIVATE);
        editor = sharedPrefs.edit();
    }

    private void MakeTransaction(int account_id,int role,int profile_id,String name){

        editor.putInt("role", role);
        Log.d("idlog", String.valueOf(account_id));
        editor.putInt("account_id", account_id);
        editor.putInt("profileID",profile_id);
        editor.putString("name",name);
        editor.commit();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class).putExtra("status", 1);
        intent.putExtra("id", account_id);
        intent.putExtra("role",role);
        intent.putExtra("profileID",profile_id);
        intent.putExtra("name",name);
        startActivity(intent);
        finish();



    }


    private void getProfileID(int accountID,int role){

        switch (role){
            case 3:

                patientViewModel.getPatient(accountID);
                patientViewModel.getPatientLiveData().observe(loginActivity, new Observer<Patient>() {
                    @Override
                    public void onChanged(Patient patient) {
                        patient_id = patient.getPatient_id();
                        name = patient.getName();
                        MakeTransaction(accountID,role,patient_id,name);
                    }
                });
                break;

            case 2:
                doctorViewModel.getDoctorLiveData(accountID,getContext());
                doctorViewModel.getDoctorMutableLiveData().observe(loginActivity, new Observer<Doctor>() {
                    @Override
                    public void onChanged(Doctor doctor) {
                        doctor_id = doctor.getDoctor_id();
                        name = doctor.getName();
                        MakeTransaction(accountID,role,doctor_id,name);
                    }
                });
                break;

            case 1:
                MakeTransaction(accountID,role,accountID,"Admin");
                break;

        }


    }

    private void loadGUI(Role role){

        getProfileID(role.getAccount_id(),role.getRole_id());

    }



    private void observeData() {
        loginViewModel.getRoleMutableLivedata().observe(this, new Observer<Role>() {
            @Override
            public void onChanged(Role role1) {
                role = role1;
                loadGUI(role);
        }});


    }

    private void ClearSP(){

        deleteSharedPreferences("My App");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ClearSP();
        context = this;
        findViews();
        SetupSharedPrefrences();


    }
}