package admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clinic.MainActivity;
import com.example.clinic.R;

import ViewModels.ClinicInfoViewModel;
import ViewModels.DoctorsViewModel;
import ViewModels.PatientListViewModel;
import pojo.ClinicInfo;
import pojo.Doctor;
import pojo.Patient;
import pojo.Statistic;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    List<Statistic> statistics = new ArrayList<>();
    List<Doctor> doctors = new ArrayList<>();
    List<Patient> patients = new ArrayList<>();
    String clinicName;
    String clinicPhoneNumber;
    String clinicAddress;
    RecyclerView recyclerView;
    StatisticsAdapter adapter;
    DoctorsViewModel doctorsViewModel;
    PatientListViewModel patientListViewModel;
    ClinicInfoViewModel clinicInfoViewModel;



    public StatisticsFragment() {}

    private void initViewModels(){

        int netStatus = ((MainActivity)getActivity()).getNetStatus();
        doctorsViewModel = DoctorsViewModel.getINSTANCE();
        patientListViewModel = PatientListViewModel.getINSTANCE();
        clinicInfoViewModel = ClinicInfoViewModel.getINSTANCE();
        doctorsViewModel.setNetStatus(netStatus);
        patientListViewModel.setNetStatus(netStatus);
        clinicInfoViewModel.setNetStatus(netStatus);


    }

    private void findViews(){

        MainActivity.toolbar.setTitle("Center Statistics");
        recyclerView = getView().findViewById(R.id.statisticsRecylerView);

    }

    private void BuildRecyclerView(){

        adapter = new StatisticsAdapter(getActivity().getBaseContext(), statistics);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

    }

    private void getData(){

        doctorsViewModel.getDoctors(getContext());
        patientListViewModel.getPatients(getContext());
        clinicInfoViewModel.getClinicInfo(getContext());

    }

    private void observeData(){

        doctorsViewModel.getDoctorsListViewModel().observe(getActivity(), new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors1) {
                doctors = doctors1;
                adapter.updateList(doctors.size(),patients.size(),clinicName,clinicPhoneNumber,clinicAddress);
            }
        });

        patientListViewModel.getPatientlistMutableLiveData().observe(getActivity(), new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patients1) {
                patients = patients1;
                adapter.updateList(doctors.size(),patients.size(),clinicName,clinicPhoneNumber,clinicAddress);

            }
        });

        clinicInfoViewModel.getClinicInfoMutableLiveData().observe(getActivity(), new Observer<ClinicInfo>() {
            @Override
            public void onChanged(ClinicInfo clinicInfo) {
                clinicName = clinicInfo.getClinic_name();
                clinicAddress = clinicInfo.getAddress();
                clinicPhoneNumber = clinicInfo.getPhone_number();
                adapter.updateList(doctors.size(),patients.size(),clinicName,clinicPhoneNumber,clinicAddress);

            }
        });

    }

    private void setupStatisticsList(){

        statistics.add(new Statistic("Total Doctors",String.valueOf(doctors.size())));
        statistics.add(new Statistic("Total Patients",String.valueOf(patients.size())));
        statistics.add(new Statistic("Clinic Name",clinicName));
        statistics.add(new Statistic("Clinic Phone Number",clinicPhoneNumber));
        statistics.add(new Statistic("Clinic Address",clinicAddress));

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        findViews();
        initViewModels();
        setupStatisticsList();
        BuildRecyclerView();
        getData();
        observeData();

    }

    @Override
    public void onStop() {
        super.onStop();
        statistics.clear();
    }
}