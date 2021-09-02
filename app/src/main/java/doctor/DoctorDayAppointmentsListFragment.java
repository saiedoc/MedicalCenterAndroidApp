package doctor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.util.Date;
import java.util.List;

public class DoctorDayAppointmentsListFragment extends Fragment implements RecyclerViewClickListener{

    List<Appointment> dayAppointments = new ArrayList<>();
    RecyclerView recyclerView;
    DoctorDayAppointmentsListAdapter adapter;
    AppointmentsViewModel appointmentsViewModel;
    LocalDate date;


    public DoctorDayAppointmentsListFragment() { }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.doctor_day_appointments, container, false);
    }

    private void initViewModels(){

        int netStatus = ((MainActivity)getActivity()).getNetStatus();
        appointmentsViewModel = AppointmentsViewModel.getINSTANCE();
        appointmentsViewModel.setNetStatus(netStatus);

    }


    private void findViews(){

        MainActivity.toolbar.setTitle("Day Appointments");
        recyclerView = getView().findViewById(R.id.doctorDayAppoinotmentsRecyclerView);
        MainActivity.toolbar.setTitle("Doctor Day Appointment Data");

    }

    private void BuildRecyclerView(){

        if(getView() != null && dayAppointments != null){
        recyclerView = getView().findViewById(R.id.doctorDayAppoinotmentsRecyclerView);
        adapter = new DoctorDayAppointmentsListAdapter(getActivity().getBaseContext(), dayAppointments, this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);}

    }

    private void getData(){
        date = ((MainActivity)getActivity()).getCurrentAppointmentDate();
        int doctor_id = ((MainActivity)getActivity()).getDoctor_id();
        int role = ((MainActivity)getActivity()).getRoleID();
        appointmentsViewModel.getAppointmentsByDateAndAccepted(doctor_id,date.toString(),getContext(),role);
    }

    private void observeData(){
        appointmentsViewModel.getAppointmentListMutableLiveData().observe(getActivity(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                dayAppointments.clear();
                for(Appointment appointment:appointments){
                    if(appointment.getDateOfBooking().equals(date.toString())){
                       dayAppointments.add(appointment);
                    }
                }
                BuildRecyclerView();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initViewModels();
        findViews();
        getData();
        observeData();

    }

    @Override
    public void onStop() {
        super.onStop();
        dayAppointments.clear();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        ((MainActivity)getActivity()).setCurrentAppointmentID(dayAppointments.get(position).getAppointment_id());
        NavHostFragment.findNavController(DoctorDayAppointmentsListFragment.this).navigate(R.id.action_doctorDayAppointmentsListFragment_to_appointmentInfo);
    }
}