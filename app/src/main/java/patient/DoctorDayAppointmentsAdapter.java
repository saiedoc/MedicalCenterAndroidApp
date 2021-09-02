package patient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinic.AMPM;
import pojo.Appointment;

import java.util.ArrayList;
import java.util.List;

import com.example.clinic.R;

public class DoctorDayAppointmentsAdapter extends RecyclerView.Adapter<DoctorDayAppointmentsAdapter.MyViewHolder> {

    List<Appointment> bookedAppointments = new ArrayList<>();
    Context context;

    public DoctorDayAppointmentsAdapter(Context context, List<Appointment> bookedAppointments){
        this.context = context;
        this.bookedAppointments = bookedAppointments;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_patient_doctor_day_appointment,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String apptmntDate = bookedAppointments.get(position).getDateOfBooking() + " " + bookedAppointments.get(position).getTimeOfBooking();
        holder.bookedAppointmentDate.setText(apptmntDate);
    }

    @Override
    public int getItemCount() {
        return bookedAppointments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView bookedAppointmentDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookedAppointmentDate = itemView.findViewById(R.id.patientDoctorDayBookedAppointment);
        }
    }
}