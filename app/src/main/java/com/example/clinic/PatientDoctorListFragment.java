package com.example.clinic;
import ViewModels.*;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import doctor.RecyclerViewClickListener;
import pojo.Doctor;
import pojo.Specialization;

public class PatientDoctorListFragment extends Fragment implements RecyclerViewClickListener {

    DoctorsViewModel doctorsViewModel;
    RecyclerView recyclerView;
    EditText searchField;
    List<Doctor> doctors = new ArrayList<>();
    List<Doctor> specDoctors = new ArrayList<>();
    List<Specialization> specializations = new ArrayList<>();
    List<Doctor> filteredDoctors = new ArrayList<>();
    PatientDoctorListRecyclerAdapter adapter;
    SpecializationsViewModel specializationsViewModel;
    Spinner specSpinner;

    public void nameFilter(String s){

        filteredDoctors.clear();

        if(s.isEmpty()){
            adapter.updateList(specDoctors);
            return;
        }


        for(Doctor doctor:specDoctors){
            if(doctor.getName().toLowerCase().contains(s)){
                filteredDoctors.add(doctor);
            }
        }

        adapter.updateList(filteredDoctors);

    }

    public void specFilter(String s){


        specDoctors.clear();
        String selected = specSpinner.getSelectedItem().toString();


        if(s.isEmpty()){
            adapter.updateList(doctors);
            return;
        }

        for(Doctor doctor:doctors){
            for(Specialization specialization : doctor.getSpecializations()){
                if (selected.equals(specialization.getName())){
                    System.out.println("matching");
                    specDoctors.add(doctor);
                }
            }
        }

        System.out.println("spec doctors : "+specDoctors);
        adapter.updateList(specDoctors);


    }

    private void observeSearchField(){



        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nameFilter(s.toString().toLowerCase());
            }
        });



    }


    private void observeSpinner(){

        specSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                specFilter(specSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                specFilter("");
            }
        });

    }


    private void initViewModels(){

        int netStatus = ((MainActivity)getActivity()).getNetStatus();
        doctorsViewModel = DoctorsViewModel.getINSTANCE();
        specializationsViewModel = SpecializationsViewModel.getINSTANCE();
        doctorsViewModel.setNetStatus(netStatus);
        specializationsViewModel.setNetStatus(netStatus);

    }

    private void getData(){

        LocalDate date = ((MainActivity)getActivity()).getCurrentAppointmentDate();
        String day = "Sunday";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            day = LocalDateTime.of(date.getYear(),date.getMonth(),date.getDayOfMonth(),0,0,0).getDayOfWeek().toString();
        }
        Log.d("appointment day : ",day);
        doctorsViewModel.getDoctorsByWorkday(day);
        specializationsViewModel.getSpecializations();

    }

    private void setupSpinner(){

        List<String> SpecNames = new ArrayList<>();

        for(int i=0 ; i<specializations.size();i++){

            SpecNames.add(specializations.get(i).getName());

        }

        System.out.println(SpecNames);
        if(SpecNames != null && getActivity() != null) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,SpecNames);
            specSpinner.setAdapter(arrayAdapter);
        }

    }


    private void observeData(){

        doctorsViewModel.getDoctorsListViewModel().observe(getActivity(), new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors1) {
                doctors = doctors1;

            }
        });

        specializationsViewModel.getSpecializationsViewModel().observe(getActivity(), new Observer<List<Specialization>>() {
            @Override
            public void onChanged(List<Specialization> specializations1) {
                specializations = specializations1;
                setupSpinner();
                observeSpinner();
            }
        });
    }

    private void findViews(){
        recyclerView = getView().findViewById(R.id.dayDoctorListRecyclerView);
        searchField = getView().findViewById(R.id.dayDoctorListSearchField);
        specSpinner = getView().findViewById(R.id.dayDoctorListSpecializations);
    }

    private void buildRecyclerView(){
        adapter = new PatientDoctorListRecyclerAdapter(getActivity().getBaseContext(),doctors,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
    }


    private String getSelectedSpec(){

        return specSpinner.getSelectedItem().toString();

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.day_doctor_list, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        initViewModels();
        getData();
        observeData();
        buildRecyclerView();
        observeSearchField();
    }

    @Override
    public void onStop() {
        super.onStop();
        getData();
    }

    public void onStart() {
        super.onStart();
        doctors.clear();

    }



    @Override
    public void recyclerViewListClicked(View v, int position) {

       if(filteredDoctors.isEmpty())
            ((MainActivity)getActivity()).setCurrentDoctorProfileId(doctors.get(position).getDoctor_id());
        else
            ((MainActivity)getActivity()).setCurrentDoctorProfileId(filteredDoctors.get(position).getDoctor_id());

        NavHostFragment.findNavController(PatientDoctorListFragment.this).navigate(R.id.action_patientDoctorListFragment2_to_doctorDayAppointmentFragment);
    }
}