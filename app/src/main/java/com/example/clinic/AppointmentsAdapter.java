package com.example.clinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import doctor.RecyclerViewClickListener;
import pojo.Appointment;

import java.util.ArrayList;
import java.util.List;


public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.DoctorAppointmentsViewHolder> {

    List<Appointment> appointments = new ArrayList<Appointment>();
    Context context;
    private static RecyclerViewClickListener itemListener;

    public AppointmentsAdapter(Context ct , List<Appointment> appointments, RecyclerViewClickListener ItemListener){

        this.appointments = appointments;
        itemListener = ItemListener;
        context = ct;
    }

    @NonNull
    @Override
    public DoctorAppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_appointment, parent,false);
        return new DoctorAppointmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAppointmentsViewHolder holder, int position) {
        String patient = "Patient: " + appointments.get(position).getPatient_name();
        holder.patientName.setText(patient);
        String apptmntDate = appointments.get(position).getDateOfBooking() + "  " +  appointments.get(position).getTimeOfBooking();
        holder.appointmentDate.setText(apptmntDate);
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class DoctorAppointmentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView appointmentDate;
        TextView patientName;

        public DoctorAppointmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.appointment_patient_name);
            appointmentDate = itemView.findViewById(R.id.appointment_date);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }
}
