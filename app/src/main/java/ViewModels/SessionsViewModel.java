package ViewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import data.ClientJsonApi;
import off_room.DoctorRoom;
import off_room.PatientRoom;
import pojo.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionsViewModel extends ViewModel {

    MutableLiveData<List<Session>> sessionListMutableLiveData = new MutableLiveData<>();

    private int netStatus = 1;

    public void setNetStatus(int netStatus) {
        this.netStatus = netStatus;
    }

    private static SessionsViewModel INSTANCE;

    public static SessionsViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new SessionsViewModel();
        return INSTANCE;
    }

    public MutableLiveData<List<Session>> getSessionListMutableLiveData() {
        return sessionListMutableLiveData;
    }

    public void getSessions(int patientID,int role,Context context) {
        if (netStatus == 1) {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

            clientJsonApi.getPatientSessionsFromDatabase(patientID).enqueue(new Callback<List<Session>>() {
                @Override
                public void onResponse(Call<List<Session>> call, Response<List<Session>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size() ; i++) {
                            response.body().get(i).setPatient_id(patientID);
                        }

                        if(role == 2)
                            DoctorRoom.getInstance(context).doctorDao().addSessions(response.body());
                        else if(role == 3)
                            PatientRoom.getInstance(context).patientDao().addSessions(response.body());


                        sessionListMutableLiveData.setValue(response.body());
                    } else {
                        //make toast.
                    }
                }

                @Override
                public void onFailure(Call<List<Session>> call, Throwable t) {
                    //make toast.
                }
            });
        } else {

            if(role == 2)
                sessionListMutableLiveData.setValue(DoctorRoom.getInstance(context).doctorDao().getSessionsByPatientID(patientID));
            else if(role == 3)
                sessionListMutableLiveData.setValue(PatientRoom.getInstance(context).patientDao().getSessionsByPatientID(patientID));

        }
    }

}
