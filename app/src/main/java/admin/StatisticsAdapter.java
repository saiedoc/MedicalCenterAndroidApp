package admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinic.R;

import pojo.Doctor;
import pojo.Statistic;

import java.util.ArrayList;
import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.MyViewHolder>{

    List<Statistic> statistics = new ArrayList<>();
    Context context;

    public StatisticsAdapter(Context context, List<Statistic> statistics){
        this.context = context;
        this.statistics = statistics;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_statistic, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.key.setText(statistics.get(position).getKey());
        holder.value.setText(statistics.get(position).getValue());
    }


    public void updateList(int doctors_number,int patients_number,String clinicName,String telephone,String address){

        if(statistics.size()!=0){
        statistics.get(0).setValue(String.valueOf(doctors_number));
        statistics.get(1).setValue(String.valueOf(patients_number));
        statistics.get(2).setValue(clinicName);
        statistics.get(3).setValue(telephone);
        statistics.get(4).setValue(address);}
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return statistics.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView key;
        TextView value;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            key = itemView.findViewById(R.id.statisticKey);
            value = itemView.findViewById(R.id.statisticValue);
        }
    }
}
