package doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinic.AMPM;
import pojo.Appointment;
import com.example.clinic.R;

import java.util.ArrayList;
import java.util.List;

public class DoctorDayAppointmentsListAdapter extends RecyclerView.Adapter<DoctorDayAppointmentsListAdapter.MyViewHolder>{

    List<Appointment> dayAppointments;
    Context context;
    private static RecyclerViewClickListener itemListener;

    public DoctorDayAppointmentsListAdapter(Context context, List<Appointment> dayAppointments, RecyclerViewClickListener itemListener){
        this.context = context;
        this.dayAppointments = dayAppointments;
        this.itemListener = itemListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_doctor_day_appointments, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String apptmntDate = dayAppointments.get(position).getDateOfBooking() + " " + dayAppointments.get(position).getTimeOfBooking();
        holder.appointmentDate.setText(apptmntDate);
        holder.patientName.setText(dayAppointments.get(position).getPatient_name());


    }

    @Override
    public int getItemCount() {
        return dayAppointments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView appointmentDate;
        TextView patientName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            appointmentDate = itemView.findViewById(R.id.singleDayAppointmentDate);
            patientName = itemView.findViewById(R.id.singleDayAppointmentPatient);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, getLayoutPosition());
        }
    }
}
