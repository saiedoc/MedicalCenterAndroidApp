package ViewModels;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clinic.LoginActivity;
import com.example.clinic.MainActivity;

import data.ClientJsonApi;
import off_room.DoctorRoom;
import off_room.PatientRoom;
import pojo.Appointment;

import java.util.List;

import pojo.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentsViewModel extends ViewModel {

    MutableLiveData<List<Appointment>> appointmentListMutableLiveData = new MutableLiveData<>();
    private int netStatus = 1;
    private static AppointmentsViewModel INSTANCE;

    public static AppointmentsViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new AppointmentsViewModel();
        return INSTANCE;
    }

    public void setNetStatus(int netStatus) {
        this.netStatus = netStatus;
    }


    public MutableLiveData<List<Appointment>> getAppointmentListMutableLiveData() {
        return appointmentListMutableLiveData;
    }


    public void getAppointments(int ID,String date, int role, Context context) {
        ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

        if (role == 2) {
            if (netStatus == 1) {
                clientJsonApi.getDoctorAppointmentsByDateAndAcceptedFromDatabase(ID,date).enqueue(new Callback<List<Appointment>>() {
                    @Override
                    public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                        if (response.isSuccessful()) {

                            DoctorRoom.getInstance(context).doctorDao().addAppointments(response.body());
                            appointmentListMutableLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Appointment>> call, Throwable t) {
                        Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();

                    }
                });
            } else {
                appointmentListMutableLiveData.setValue(DoctorRoom.getInstance(context).doctorDao().getAcceptedAppointments());
            }
        } else if (role == 3) {
            if (netStatus == 1) {
                clientJsonApi.getPatientAppointments(ID,date).enqueue(new Callback<List<Appointment>>() {
                    @Override
                    public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                        if (response.isSuccessful()) {
                            PatientRoom.getInstance(context).patientDao().addAppointements(response.body());
                            appointmentListMutableLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Appointment>> call, Throwable t) {
                        Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();
                    }
                });
            } else {

                appointmentListMutableLiveData.setValue(PatientRoom.getInstance(context).patientDao().getAcceptedAppointments());
            }
        }

    }

    public void getDoctorAppointmentsPending(int doctorID,String date,Context context) {
        if (netStatus == 1) {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

            clientJsonApi.getDoctorAppointmentsPendingFromDatabase(doctorID,date).enqueue(new Callback<List<Appointment>>() {
                @Override
                public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                    if (response.isSuccessful()) {


                        DoctorRoom.getInstance(context).doctorDao().addAppointments(response.body());
                        appointmentListMutableLiveData.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Appointment>> call, Throwable t) {
                    Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();

                }
            });
        } else {
            appointmentListMutableLiveData.setValue(DoctorRoom.getInstance(context).doctorDao().getPendingAppointments());
        }
    }



    public void getAppointmentsByDateAndAccepted(int ID, String date,Context context, int role) {

        if(role == 2){
        if (netStatus == 1) {

            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

            clientJsonApi.getDoctorAppointmentsByDateAndAcceptedFromDatabase(ID, date).enqueue(new Callback<List<Appointment>>() {
                @Override
                public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                    if (response.isSuccessful()) {

                        DoctorRoom.getInstance(context).doctorDao().addAppointments(response.body());
                        appointmentListMutableLiveData.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Appointment>> call, Throwable t) {
                    Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();
                }
            });
        } else {
          appointmentListMutableLiveData.setValue(DoctorRoom.getInstance(context).doctorDao().getAppointmentByDate(date));
        }
        }
        else if (role == 3)
        {
            if(netStatus == 1)
            {
                ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

                clientJsonApi.getPatientAppointmentsAcceptedByDateFromDatabase(date,ID).enqueue(new Callback<List<Appointment>>() {
                    @Override
                    public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                        if(response.isSuccessful())
                        {

                            appointmentListMutableLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Appointment>> call, Throwable t) {
                        Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();

                    }
                });
            }else{
                Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();

            }
        }
    }

}
