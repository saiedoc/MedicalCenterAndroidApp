package doctor;


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


import pojo.Patient;

public class PatientsListRecyclerAdapter extends RecyclerView.Adapter<PatientsListRecyclerAdapter.MyViewHolder> {

    List<Patient> patients = new ArrayList<Patient>();
    Context context;
    private static RecyclerViewClickListener itemListener;


    public PatientsListRecyclerAdapter(Context ct , List<Patient> patients, RecyclerViewClickListener ItemListener){

      this.patients = patients;
      itemListener = ItemListener;
      context = ct;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.patient_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.patientName.setText(patients.get(position).getName());
        //if(!patients.get(position).getSicknessList().isEmpty())
        //holder.patientSickness.setText(patients.get(position).getSicknessList().get(0).getName());

    }

    @Override
    public int getItemCount() {
        if(patients != null) return patients.size();
        else return 0;
    }

    public void updateList(List<Patient> patients1){
        patients = patients1;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView patientName,patientSickness;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.patient_list_name);
            patientSickness = itemView.findViewById(R.id.patient_list_sickness);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }
}
