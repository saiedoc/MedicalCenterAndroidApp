package com.example.clinic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import pojo.Appointment;

import ViewModels.*;

public class AppointmentInfo extends Fragment implements  Animation.AnimationListener {

    Appointment appointment;
    TextView appointmentDate;
    TextView patient;
    TextView doctor;
    TextView time;
    Button attachments;
    Button acceptBtn;
    Button rejectBtn;
    TextView acceptedOrRejected;
    Animation topToBottom;
    Animation bottomToTop;
    View acceptedRejectedBar;
    View textViewLayout;
    AppointmentViewModel appointmentViewModel = AppointmentViewModel.getINSTANCE();

    public AppointmentInfo() {}



    private void setupAnimation(){
        topToBottom = AnimationUtils.loadAnimation(getContext(), R.anim.top_to_bottom);
        topToBottom.setDuration(750);
        bottomToTop = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_to_top);
        bottomToTop.setDuration(750);
        topToBottom.setAnimationListener(this);
    }

    private void findViews(){


        appointmentDate = getView().findViewById(R.id.appointmentInfoDate);
        patient = getView().findViewById(R.id.appointmentInfoPatient);
        doctor = getView().findViewById(R.id.appointmentInfoDoctor);
        time = getView().findViewById(R.id.appointmentInfoTime);

    }

    private void setTextViews(){

        patient.setText(appointment.getPatient_name());
        doctor.setText(appointment.getDoctor_name());
        appointmentDate.setText(appointment.getDateOfBooking());
        time.setText(appointment.getTimeOfBooking());
    }

    private void getData(){
        int role = ((MainActivity)getActivity()).getRoleID();
        appointmentViewModel.getAppointment(((MainActivity)getActivity()).currentAppointmentID,role,getContext());
    }


    private void observeData(){

        appointmentViewModel.getAppointmentMutableLiveData().observe(getActivity(), new Observer<Appointment>() {
            @Override
            public void onChanged(Appointment appointment1) {
                appointment = appointment1;
                setTextViews();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.appointment_info, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        findViews();
        setupAnimation();
        getData();
        observeData();

    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        textViewLayout.setVisibility(View.VISIBLE);
        textViewLayout.startAnimation(bottomToTop);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}