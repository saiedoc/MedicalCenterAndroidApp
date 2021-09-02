package patient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinic.R;
import pojo.Session;

import java.util.List;

import doctor.RecyclerViewClickListener;

public class PatientSessionsAdapter extends RecyclerView.Adapter<PatientSessionsAdapter.MyViewHolder>{

    List<Session> sessions;
    Context context;
    private static RecyclerViewClickListener itemListener;

    public PatientSessionsAdapter(Context context, List<Session> sessions, RecyclerViewClickListener ItemListener) {
        this.context = context;
        this.sessions = sessions;
        itemListener = ItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_session,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.sessionDate.setText(sessions.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView sessionDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sessionDate = itemView.findViewById(R.id.sessionDate);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());

        }
    }
}
