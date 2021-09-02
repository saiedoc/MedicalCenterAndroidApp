package doctor;
import ViewModels.*;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.clinic.MainActivity;
import com.example.clinic.R;

import java.time.LocalDate;
import java.util.List;


import pojo.Appointment;
import pojo.Doctor;
import pojo.Patient;

public class DoctorProfile extends Fragment {


    TextView name;
    TextView age;
    TextView gender;
    TextView email;
    TextView phone_number;
    TextView specialization;
    TextView patients;
    TextView appointment;
    Doctor doctor = new Doctor();
    int NumberOfAppointments;
    int NumberOfPatients;
    DoctorViewModel doctorViewModel;
    AppointmentsViewModel appointmentsViewModel;
    PatientListViewModel patientListViewModel;
    private static Context context;
    ImageView callButton;
    ImageView messageButton;

    public DoctorProfile() {
        // Required empty public constructor
    }

    public static Context GetContext() {
        return context;
    }

    private void initViewModels(){

        int netStatus = ((MainActivity)getActivity()).getNetStatus();
        doctorViewModel = DoctorViewModel.getINSTANCE();
        appointmentsViewModel = AppointmentsViewModel.getINSTANCE();
        patientListViewModel = PatientListViewModel.getINSTANCE();
        doctorViewModel.setNetStatus(netStatus);
        appointmentsViewModel.setNetStatus(netStatus);
        patientListViewModel.setNetStatus(netStatus);


    }

    private void findViews() {

        callButton = getView().findViewById(R.id.callDoctor);
        callButton.setClickable(true);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+ doctor.getPhoneNumber()));
                startActivity(callIntent);
            }
        });

        messageButton = getView().findViewById(R.id.messageDoctor);
        messageButton.setClickable(true);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" + doctor.getPhoneNumber() ));
                startActivity(sendIntent);
            }
        });


        name = getView().findViewById(R.id.doctorName);
        age = getView().findViewById(R.id.doctorAge);
        gender = getView().findViewById(R.id.doctorGender);
        email = getView().findViewById(R.id.doctorEmail);
        phone_number = getView().findViewById(R.id.doctorPhoneNumber);
        specialization = getView().findViewById(R.id.doctorSpecialization);
        appointment = getView().findViewById(R.id.doctorAppointments);
        patients = getView().findViewById(R.id.doctorPatients);
        MainActivity.toolbar.setTitle("Medical Center");
    }

    private void getData() {

        int role = ((MainActivity) getActivity()).getRoleID();
        switch(role){

            case 2:
                doctorViewModel.getDoctorByDoctorID(((MainActivity) getActivity()).getDoctor_id(),getContext());
                patientListViewModel.getPatients(((MainActivity) getActivity()).getDoctor_id(),getContext());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    appointmentsViewModel.getAppointments(((MainActivity) getActivity()).getDoctor_id(), LocalDate.now().toString(),2,getContext());
                }
                break;

            case 1:
                System.out.println("ADMIIIIIIN");
                doctorViewModel.getDoctorByDoctorID(((MainActivity) getActivity()).getCurrentDoctorProfileId(),getContext());
                patientListViewModel.getPatients(((MainActivity) getActivity()).getCurrentDoctorProfileId(),getContext());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    appointmentsViewModel.getAppointments(((MainActivity) getActivity()).getCurrentDoctorProfileId(),LocalDate.now().toString(),2,getContext());
                }
                break;
        }
    }

    private void observeData() {

        doctorViewModel.getDoctorMutableLiveData().observe(getActivity(), new Observer<Doctor>() {
            @Override
            public void onChanged(Doctor docPro) {
                doctor = docPro;
                if(doctor!=null )
                setTextViews();
            }
        });

        appointmentsViewModel.getAppointmentListMutableLiveData().observe(getActivity(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {

                NumberOfAppointments = appointments.size();
                if(doctor!=null)
                setTextViews();

            }
        });

        patientListViewModel.getPatientlistMutableLiveData().observe(getActivity(), new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patients) {
                NumberOfPatients = patients.size();
                if(doctor!=null)
                setTextViews();
            }
        });

    }

    private String getAge(String date){
        String []spDate = date.split("-");
        String []spLocalDate = new String[1];

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            spLocalDate = LocalDate.now().toString().split("-");
        }

        int year = Integer.parseInt(spDate[0]);
        int yearNow = Integer.parseInt(spLocalDate[0]);

        yearNow -= year;
        return String.valueOf(yearNow);
    }

    private void setTextViews() {

     name.setText(doctor.getName());
     age.setText(getAge(doctor.getDateOfBirth()));
     gender.setText(doctor.getGender());
     email.setText(doctor.getEmail());
     phone_number.setText(doctor.getPhoneNumber());
     appointment.setText(String.valueOf(String.valueOf(NumberOfAppointments)));
     patients.setText(String.valueOf(NumberOfPatients));

     if(doctor.getSpecializations() != null) {
         specialization.setText(doctor.getSpecializations().get(0).getName());
     }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doctor_profile, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        findViews();
        initViewModels();
        getData();
        observeData();



    }

}