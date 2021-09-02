package patient;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import ViewModels.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.clinic.MainActivity;
import com.example.clinic.R;
import java.time.LocalDate;
import java.util.List;
import pojo.Appointment;
import pojo.Patient;


public class PatientProfile extends Fragment {


    Patient patientF;
    int id;
    TextView name;
    TextView age;
    TextView gender;
    TextView email;
    TextView phone_number;
    TextView sickness;
    TextView appointment;
    int NumberOfAppointments;
    Button viewSessionsBtn;
    Button viewDataBtn;
    ImageView callButton;
    ImageView messageButton;
    PatientViewModel patientViewModel;
    AppointmentsViewModel appointmentsViewModel;


    public PatientProfile() {

    }


    private void initViewModels(){

        int netStatus = ((MainActivity)getActivity()).getNetStatus();
        patientViewModel = PatientViewModel.getINSTANCE();
        appointmentsViewModel = AppointmentsViewModel.getINSTANCE();
        patientViewModel.setNetStatus(netStatus);
        appointmentsViewModel.setNetStatus(netStatus);
        View.OnClickListener sicknessCLick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SicknessesDialog sicknessesDialog = new SicknessesDialog() ;
                sicknessesDialog.setListItems(patientF.getSickness());
                sicknessesDialog.show(getFragmentManager(),"");
            }
        };

        sickness.setOnClickListener(sicknessCLick);


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
        name.setText(patientF.getName());
        age.setText(getAge(patientF.getDateOfBirth()));
        gender.setText(patientF.getGender());
        email.setText(patientF.getEmail());
        phone_number.setText(String.valueOf(patientF.getPhoneNumber()));
        appointment.setText(String.valueOf(NumberOfAppointments));
        if(patientF.getSickness() != null){
            sickness.setText(String.valueOf(patientF.getSickness().size()));

        }

    }

    private void findViews() {

        viewSessionsBtn = getView().findViewById(R.id.viewPatientSessionsBtn);
        viewSessionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setCurrentPatientProfileID(patientF.getPatient_id());
                int role = ((MainActivity) getActivity()).getRoleID();

                switch (role) {
                    case 3:
                        NavHostFragment.findNavController(PatientProfile.this).navigate(R.id.action_patientProfile_to_patientSessionsFragment);
                        break;
                    case 2:
                        NavHostFragment.findNavController(PatientProfile.this).navigate(R.id.action_patient_to_patientSessionsFragment);
                        break;
                }
            }
        });
        viewDataBtn = getView().findViewById(R.id.viewPatientDataBtn);
        viewDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setCurrentPatientProfileID(patientF.getPatient_id());
                int role = ((MainActivity) getActivity()).getRoleID();
                if(((MainActivity)getActivity()).getNetStatus() != 1){

                    Toast.makeText(getActivity(),"No Connection.",Toast.LENGTH_SHORT).show();
                    return;

                }
                switch (role) {
                    case 3:
                        NavHostFragment.findNavController(PatientProfile.this).navigate(R.id.action_patient_profile_to_lineChartFragment2);
                        break;
                    case 2:
                        NavHostFragment.findNavController(PatientProfile.this).navigate(R.id.action_patient_to_lineChartFragment);
                        break;
                }
            }
        });
        callButton = getView().findViewById(R.id.callPatient);
        callButton.setClickable(true);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+ patientF.getPhoneNumber()));
                startActivity(callIntent);
            }
        });
        messageButton = getView().findViewById(R.id.messagePatient);
        messageButton.setClickable(true);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" + patientF.getPhoneNumber()));
                startActivity(sendIntent);

            }
        });
        name = getView().findViewById(R.id.patientName);
        age = getView().findViewById(R.id.patientAge);
        gender = getView().findViewById(R.id.patientGender);
        email = getView().findViewById(R.id.patientEmail);
        phone_number = getView().findViewById(R.id.patientPhoneNumber);
        sickness = getView().findViewById(R.id.patientSickness);
        appointment = getView().findViewById(R.id.patientAppointments);

        MainActivity.toolbar.setTitle("Medical Center");

    }

    private void getData() {

        int role = ((MainActivity) getActivity()).getRoleID();
        switch (role) {

            case 1:
                break;
            case 3:
                id = (((MainActivity) getActivity())).getPatient_ID();
                break;
            case 2:
                id = (((MainActivity) getActivity()).getCurrentPatientProfileID());
                break;

        }
        Log.d("id in patient profile", String.valueOf(id));
        patientViewModel.getPatientByID(id,getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            appointmentsViewModel.getAppointments(id, LocalDate.now().toString(), 3,getContext());
        }
    }

    private void observeData() {

        patientViewModel.getPatientLiveData().observe(getActivity(), new Observer<Patient>() {
            @Override
            public void onChanged(Patient patient) {
                patientF = patient;
                System.out.println("p profile : " + patientF);
                if(patientF != null)
                setTextViews();
            }
        });

        appointmentsViewModel.getAppointmentListMutableLiveData().observe(getActivity(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                NumberOfAppointments = appointments.size();
                if(patientF != null)
                setTextViews();
            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_patient_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        initViewModels();
        getData();
        observeData();
    }


}