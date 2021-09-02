package com.example.clinic;
import ViewModels.*;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import doctor.RecyclerViewClickListener;
import pojo.Appointment;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.List;

public class AppointmentsFragment extends Fragment implements RecyclerViewClickListener, CalendarView.OnDateChangeListener{

    RecyclerView doctorAppointmentsRecyclerView;
    AppointmentsAdapter adapter;
    AppointmentsViewModel mViewModel;
    List<Appointment> upcomingAppointments = new ArrayList<>();
    Animation animation;
    AppointmentsViewModel appointmentsViewModel;
    CalendarView calendarView;
    int role;

    private void initViewModels(){

        int netStatus = ((MainActivity)getActivity()).getNetStatus();
        appointmentsViewModel = AppointmentsViewModel.getINSTANCE();
        appointmentsViewModel.setNetStatus(netStatus);


    }

    public void loadAnimation(){

        animation = AnimationUtils.loadAnimation(getContext(),R.anim.appointments_animation);
        animation.setDuration(500);

    }

    public void findViews(){
        doctorAppointmentsRecyclerView = getView().findViewById(R.id.doctorAppointmentsRecyclerView);
        doctorAppointmentsRecyclerView = getView().findViewById(R.id.doctorAppointmentsRecyclerView);
        calendarView =  getView().findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(this);
        MainActivity.toolbar.setTitle("Appointments");
    }

    public void BuildRecyclerView(){
        adapter = new AppointmentsAdapter(getActivity(), upcomingAppointments,this);
        doctorAppointmentsRecyclerView.setAdapter(adapter);
        doctorAppointmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        doctorAppointmentsRecyclerView.startAnimation(animation);
    }

    public void getData(){

        role = ((MainActivity)getActivity()).role;
        int id;
        switch(role) {

            case 2:
                id = ((MainActivity) getActivity()).getDoctor_id();
                System.out.println("id in doctor appointments :" + id);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    appointmentsViewModel.getAppointmentsByDateAndAccepted(id,LocalDate.now().toString(),getContext(),2);
                }
                break;
            case 3:
                id = ((MainActivity) getActivity()).getPatient_ID();
                System.out.println("id in patient appointments :" + id);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    appointmentsViewModel.getAppointmentsByDateAndAccepted(id,LocalDate.now().toString(),getContext(),3);
                }
                break;
        }

    }



    public void observeData(){

        appointmentsViewModel.getAppointmentListMutableLiveData().observe(getActivity(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments1) {
                  upcomingAppointments = appointments1;
                System.out.println("upcoming : " + upcomingAppointments);
                  BuildRecyclerView();
            }});


    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.doctor_appointments, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModels();
        findViews();
        loadAnimation();
        getData();
        observeData();


    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

        ((MainActivity)getActivity()).setCurrentAppointmentID(upcomingAppointments.get(position).getAppointment_id());
        switch (role) {

            case 2:
            NavHostFragment.findNavController(AppointmentsFragment.this).navigate(R.id.action_appointments_to_appointmentInfo);
            break;

            case 3:
            NavHostFragment.findNavController(AppointmentsFragment.this) .navigate(R.id.action_doctorAppointmentsFragment_to_appointmentInfo2);
            break;

        }
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
    {

        System.out.println(year + "-" + month+1 + "-" + dayOfMonth);
        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.of(year, month+1, dayOfMonth);
        }
        ((MainActivity) getActivity()).setCurrentAppointmentDate(date);
        role = ((MainActivity)getActivity()).role;
        switch (role){
            case 3:
                if(((MainActivity)getActivity()).getNetStatus() != 1){

                    Toast.makeText(getActivity(),"No Connection.",Toast.LENGTH_SHORT).show();
                    return;

                }
                NavHostFragment.findNavController(AppointmentsFragment.this).navigate(R.id.action_patient_appointments_to_patientDoctorListFragment2);
                break;
            case 2:
                NavHostFragment.findNavController(AppointmentsFragment.this).navigate(R.id.action_doctor_appointments_to_doctorDayAppointmentsListFragment);
                break;
        }

    }
}
