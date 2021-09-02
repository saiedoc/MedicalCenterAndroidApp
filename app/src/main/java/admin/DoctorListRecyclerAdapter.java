package admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinic.R;

import java.util.ArrayList;
import java.util.List;

import doctor.RecyclerViewClickListener;
import pojo.Doctor;

public class DoctorListRecyclerAdapter extends RecyclerView.Adapter<DoctorListRecyclerAdapter.DoctorViewHolder> {

    List<Doctor> doctors = new ArrayList<Doctor>();
    Context context;
    private static RecyclerViewClickListener itemListener;


    public DoctorListRecyclerAdapter(Context ct , List<Doctor> doctors, RecyclerViewClickListener ItemListener){

        this.doctors = doctors;
        itemListener = ItemListener;
        context = ct;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.doctor_row,parent,false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position){
        holder.doctorName.setText(doctors.get(position).getName());
        holder.doctorSpecialization.setText("Dentist");
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public void updateList(List<Doctor> doctors){
        this.doctors = doctors;
        notifyDataSetChanged();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView doctorName, doctorSpecialization;
        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctor_list_name);
            doctorSpecialization = itemView.findViewById(R.id.doctor_list_specialization);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }
}
