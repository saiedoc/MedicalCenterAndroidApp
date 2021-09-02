package patient;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import ViewModels.AppointmentsViewModel;
import ViewModels.DoctorViewModel;
import data.ClientJsonApi;
import pojo.Appointment;
import pojo.BookAppointmentModel;
import pojo.Doctor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.clinic.MainActivity;
import com.example.clinic.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DoctorDayAppointmentFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    TextView workPeriod;
    TextView docName;
    Button makeAppointmentBtn;
    DoctorDayAppointmentsAdapter adapter;
    List<Appointment> bookedAppointments;
    AppointmentsViewModel appointmentsViewModel;
    DoctorViewModel doctorViewModel;
    ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();
    LocalDate currentDate;



    public DoctorDayAppointmentFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.patient_doctor_day_appointments, container, false);
    }

    private void initViewModels(){
        int netStatus = ((MainActivity)getActivity()).getNetStatus();
        appointmentsViewModel = AppointmentsViewModel.getINSTANCE();
        doctorViewModel = DoctorViewModel.getINSTANCE();
        appointmentsViewModel.setNetStatus(netStatus);
        doctorViewModel.setNetStatus(netStatus);
    }

    private void getData(){
        currentDate = ((MainActivity)getActivity()).getCurrentAppointmentDate();
        int id = ((MainActivity)getActivity()).getCurrentDoctorProfileId();
        int role = ((MainActivity)getActivity()).getRoleID();
        doctorViewModel.getDoctorByDoctorID(id,getContext());
        appointmentsViewModel.getAppointmentsByDateAndAccepted(id,currentDate.toString(),getContext(),role);

    }

    private void observeData(){

        doctorViewModel.getDoctorMutableLiveData().observe(getActivity(), new Observer<Doctor>() {
            @Override
            public void onChanged(Doctor doctor) {
                docName.setText(doctor.getName());
            }
        });


        appointmentsViewModel.getAppointmentListMutableLiveData().observe(getActivity(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                bookedAppointments = appointments;
                buildRecyclerView();
            }
        });
    }


    private void findViews(){

        makeAppointmentBtn = getView().findViewById(R.id.patientDoctorDayMakeAppointmentBtn);
        makeAppointmentBtn.setOnClickListener(this);
        docName = getView().findViewById(R.id.patient_doctor_day_docName);
        workPeriod = getView().findViewById(R.id.workPeriod);
        recyclerView = getView().findViewById(R.id.patientDoctorDayRecyclerView);
        MainActivity.toolbar.setTitle("Doctor Day Appointment Data");

    }

    private void buildRecyclerView(){

        if(recyclerView != null && getActivity() != null) {
            adapter = new DoctorDayAppointmentsAdapter(getActivity().getBaseContext(), bookedAppointments);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
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

    private void bookAppointment(Date date, int hour, int minute){

        Time time = new Time(hour,minute,0);
        String sdate = new SimpleDateFormat("dd-MM-yyyy").format(date);
        System.out.println(date.toString());
        int doctor_id = ((MainActivity)getActivity()).getCurrentDoctorProfileId();
        int patient_id = ((MainActivity)getActivity()).getPatient_ID();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("doctor_id",doctor_id);
        jsonObject.addProperty("patient_id",patient_id);
        jsonObject.addProperty("dateOfBooking",sdate);
        jsonObject.addProperty("timeOfBooking", String.valueOf(time));
        System.out.println("request " + jsonObject.toString());
        clientJsonApi.bookAppointment(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null){
                    Toast.makeText(getContext(), "Appointment requested.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Network problem occurred.", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "No Connection.", Toast.LENGTH_LONG).show();

            }
        });



    }

    @Override
    public void onClick(View view) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int mnt) {}
        }, hour, minute, false);
        timePickerDialog.show();
        Button Ok_button = timePickerDialog.getButton(TimePickerDialog.BUTTON_POSITIVE);
        Ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment(Date.from(currentDate.atTime(hour,minute).toInstant(ZoneOffset.MAX)),hour,minute);
                timePickerDialog.dismiss();
            }
        });


    }
}