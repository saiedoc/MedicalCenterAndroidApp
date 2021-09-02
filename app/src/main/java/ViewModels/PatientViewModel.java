package ViewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import data.ClientJsonApi;
import off_room.PatientRoom;
import pojo.Patient;

import pojo.Sickness;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientViewModel extends ViewModel {

    MutableLiveData<Patient> patientMutableLiveData = new MutableLiveData<>();
    private Patient patient;
    private int netStatus = 1;

    public void setNetStatus(int netStatus) {
        this.netStatus = netStatus;
    }

    private static PatientViewModel INSTANCE;

    public static PatientViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new PatientViewModel();
        return INSTANCE;
    }

    public MutableLiveData<Patient> getPatientLiveData() {
        return patientMutableLiveData;
    }

    public void getPatient(int accountID) {
        if (netStatus == 1) {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

            clientJsonApi.getPatientByAccountIDFromDatabase(accountID).enqueue(new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    if (response.isSuccessful()) {
                        patient = response.body();

                        for(Sickness sickness : patient.getPatientSickness())
                        {
                            patient.getSickness().add(sickness.getName());
                        }
                        patientMutableLiveData.setValue(patient);


                    } else {
                        //make toast.
                    }
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {
                    //make toast.
                }
            });
        } else {
            //room data.
        }

    }


    public void getPatientByID(int patientID, Context context) {
        if (netStatus == 1) {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();


            clientJsonApi.getPatientInfofromDatabase(patientID).enqueue(new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    if (response.isSuccessful()) {
                        PatientRoom.getInstance(context).patientDao().addPatient(response.body());
                        patient = response.body();

                       for(Sickness sickness : patient.getPatientSickness())
                       {
                          patient.getSickness().add(sickness.getName());
                       }
                        patientMutableLiveData.setValue(patient);
                    } else {
                        //make toast.
                    }
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {
                    //make toast.
                }
            });
        } else {
            patientMutableLiveData.setValue(PatientRoom.getInstance(context).patientDao().getPatientByID(patientID));
        }
    }

}
