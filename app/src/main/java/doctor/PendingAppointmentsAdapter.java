package doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinic.AMPM;

import data.ClientJsonApi;
import okhttp3.ResponseBody;
import pojo.Appointment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.clinic.R;

import java.util.ArrayList;
import java.util.List;



public class PendingAppointmentsAdapter extends RecyclerView.Adapter<PendingAppointmentsAdapter.MyViewHolder> {

    List<Appointment> pendingAppointments = new ArrayList<Appointment>();
    Context context;
    private static RecyclerViewClickListener itemListener;

    public PendingAppointmentsAdapter(Context ct, List<Appointment> pendingAppointments, RecyclerViewClickListener itemListener) {
        this.pendingAppointments = pendingAppointments;
        context = ct;
        this.itemListener = itemListener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_pending_appointment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.pendingAppointmentDate.setText(pendingAppointments.get(position).getDateOfBooking());

    }

    public void updateList(List<Appointment> appointments){
        pendingAppointments = appointments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pendingAppointments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Animation.AnimationListener {

        TextView pendingAppointmentDate;
        ImageView infoBtn;
        ImageView acceptPendingAppointmentBtn;
        ImageView rejectPendingAppointmentBtn;
        TextView pendingAcceptedOrRejected;
        CardView card;
        Animation fadeOut;

        private void acceptAppointment(int id){

            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();
            clientJsonApi.updateAppointmentAcceptFromDatabase(id).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful())
                    {
                        System.out.println("Accept response : " + response.body());
                    }else{
                        System.out.println("Accept response : " + response.body() + " code : " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("Accept response message : " + t.getMessage());
                }
            });

        }

        private void rejectAppointment(int id){

            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();
            clientJsonApi.updateAppointmentRejectFromDatabase(id).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pendingAppointmentDate = itemView.findViewById(R.id.doctorPendingAppointmentDate);
            fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
            fadeOut.setDuration(1000);
            fadeOut.setAnimationListener(this);
            card = itemView.findViewById(R.id.pendingAppointmentCardView);
            infoBtn = itemView.findViewById(R.id.pending_appointment_info);
            acceptPendingAppointmentBtn = itemView.findViewById(R.id.acceptPendingAppointment);
            rejectPendingAppointmentBtn = itemView.findViewById(R.id.rejectPendingAppointment);
            pendingAcceptedOrRejected = itemView.findViewById(R.id.pendingAcceptedOrRejected);
            pendingAcceptedOrRejected.setVisibility(View.GONE);
            infoBtn.setOnClickListener(this);
            acceptPendingAppointmentBtn.setOnClickListener(this);
            rejectPendingAppointmentBtn.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.pending_appointment_info:

                    itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
                    break;

                case R.id.acceptPendingAppointment:
                    Toast.makeText(context, "Appointment Accepted", Toast.LENGTH_LONG).show();
                    acceptPendingAppointmentBtn.setVisibility(View.GONE);
                    rejectPendingAppointmentBtn.setVisibility(View.GONE);
                    pendingAcceptedOrRejected.setText(R.string.accepted);
                    pendingAcceptedOrRejected.setVisibility(View.VISIBLE);
                    acceptAppointment(pendingAppointments.get(getAdapterPosition()).getAppointment_id());
                    card.startAnimation(fadeOut);

                    break;

                case R.id.rejectPendingAppointment:
                    Toast.makeText(context, "Appointment Rejected", Toast.LENGTH_LONG).show();
                    acceptPendingAppointmentBtn.setVisibility(View.GONE);
                    rejectPendingAppointmentBtn.setVisibility(View.GONE);
                    pendingAcceptedOrRejected.setText(R.string.rejected);
                    pendingAcceptedOrRejected.setVisibility(View.VISIBLE);
                    card.startAnimation(fadeOut);
                    rejectAppointment(pendingAppointments.get(getAdapterPosition()).getAppointment_id());
                    break;
            }
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            card.setVisibility(View.GONE);
            pendingAppointments.remove(this.getLayoutPosition());
            notifyItemRemoved(this.getLayoutPosition());
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }
}