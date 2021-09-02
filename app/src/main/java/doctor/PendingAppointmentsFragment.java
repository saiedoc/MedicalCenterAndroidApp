package doctor;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ViewModels.AppointmentsViewModel;
import pojo.Appointment;
import com.example.clinic.MainActivity;
import com.example.clinic.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PendingAppointmentsFragment extends Fragment implements RecyclerViewClickListener{

    RecyclerView recyclerView;
    List<Appointment> pendingAppointments = new ArrayList<>();
    PendingAppointmentsAdapter adapter;
    AppointmentsViewModel appointmentsViewModel;


    public PendingAppointmentsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pending_appointments, container, false);
    }

    private void initViewModels(){

        int netStatus = ((MainActivity)getActivity()).getNetStatus();
        appointmentsViewModel = AppointmentsViewModel.getINSTANCE();
        appointmentsViewModel.setNetStatus(netStatus);

    }

    private void getData(){

        appointmentsViewModel.getAppointmentListMutableLiveData().setValue(new ArrayList<>());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            appointmentsViewModel.getDoctorAppointmentsPending(((MainActivity)getActivity()).getDoctor_id(), LocalDate.now().toString(),getContext());
        }

    }

    private void observeData(){

        appointmentsViewModel.getAppointmentListMutableLiveData().observe(getActivity(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                pendingAppointments = appointments;
                System.out.println("" + appointments);
                buildRecyclerView();
            }
        });

    }


    private void findViews(){
        recyclerView = getView().findViewById(R.id.pendingAppointmentsRecyclerView);
        MainActivity.toolbar.setTitle("Pending Appointments Data");
    }

    private void buildRecyclerView(){

        if(pendingAppointments != null){
        adapter = new PendingAppointmentsAdapter(getActivity(), pendingAppointments, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        findViews();
        initViewModels();
        getData();
        observeData();



    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        ((MainActivity)getActivity()).setCurrentAppointmentID(pendingAppointments.get(position).getAppointment_id());
        NavHostFragment.findNavController(PendingAppointmentsFragment.this).navigate(R.id.action_pendingAppointmentsFragment_to_appointmentInfo);
    }
}