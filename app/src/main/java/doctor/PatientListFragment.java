package doctor;
import ViewModels.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clinic.Capture;
import com.example.clinic.MainActivity;
import com.example.clinic.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import patient.PatientProfile;
import pojo.Patient;


public class PatientListFragment extends Fragment implements RecyclerViewClickListener , View.OnClickListener {

    RecyclerView recyclerView;
    EditText searchField;
    FloatingActionButton scanButton;
    List<Patient> patientsF = new ArrayList<>();
    List<Patient> filteredPatients = new ArrayList<>();
    PatientsListRecyclerAdapter adapter;
    PatientListViewModel patientListViewModel= PatientListViewModel.getINSTANCE();
    private static Context context;


    public static Context GetContext() {
        return context;
    }
    public void filter(String s){

        filteredPatients.clear();

        if(s.isEmpty()){
            adapter.updateList(patientsF);
            return;
        }


        for(Patient patient:patientsF){
            if(patient.getName().toLowerCase().contains(s)){
                filteredPatients.add(patient);
            }
        }

        adapter.updateList(filteredPatients);

    }
    public void observeSearchField(){



        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString().toLowerCase());
            }
        });

    }
    public void findViews(){

        recyclerView = getView().findViewById(R.id.C_recyclerView);
        searchField = getView().findViewById(R.id.search_field);
        scanButton = getView().findViewById(R.id.QRScanButton);
        scanButton.setOnClickListener(this);
        MainActivity.toolbar.setTitle("Patient List ");

    }
    public void getData(){
        patientListViewModel.getPatients(((MainActivity) getActivity()).getDoctor_id(),context);
    }
    public void observeData(){
        patientListViewModel.getPatientlistMutableLiveData().observe(getActivity(), new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patients) {
                patientsF = patients;
                adapter.updateList(patientsF);
            }
        });
    }
    public void BuildRecyclerView(){
        adapter = new PatientsListRecyclerAdapter(getActivity().getBaseContext(),patientsF,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.patients_list, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        getData();
        findViews();
        BuildRecyclerView();
        observeData();
        observeSearchField();
    }


    @Override
    public void recyclerViewListClicked(View v, int position) {

        if(filteredPatients.isEmpty())
            ((MainActivity)getActivity()).setCurrentPatientProfileID(patientsF.get(position).getPatient_id());
        else
            ((MainActivity)getActivity()).setCurrentPatientProfileID(filteredPatients.get(position).getPatient_id());
        NavHostFragment.findNavController(PatientListFragment.this).navigate(R.id.action_patients_to_patientProfile);

    }

    @Override
    public void onClick(View v) {


        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(PatientListFragment.this);
        integrator.setOrientationLocked(true);
        integrator.setPrompt("Scan QR code");
        integrator.setBeepEnabled(false);
        integrator.setCaptureActivity(Capture.class);
        integrator.initiateScan();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        // if the scanner capture a Qr code then make something
        if(intentResult.getContents() != null)
        {
            System.out.println("code value : " + Integer.valueOf(intentResult.getContents()));
            ((MainActivity)getActivity()).setCurrentPatientProfileID(Integer.valueOf(intentResult.getContents()));
             NavHostFragment.findNavController(PatientListFragment.this).navigate(R.id.action_patients_to_patientProfile);

        }
        else {
            System.out.println("code value : no value ");
            Toast.makeText(getActivity().getBaseContext(),"There is No QR or Bar Code Here", Toast.LENGTH_LONG).show();

        }
    }

}

/*
        ((MainActivity) getActivity()).setCurrentSessionID(sessions.get(position).getSession_id());

 */