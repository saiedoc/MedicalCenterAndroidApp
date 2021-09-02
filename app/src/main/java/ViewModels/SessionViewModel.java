package ViewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import data.ClientJsonApi;
import off_room.DoctorRoom;
import off_room.PatientRoom;
import pojo.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionViewModel extends ViewModel {

    MutableLiveData<Session> sessionMutableLiveData = new MutableLiveData<>();
    private int netStatus = 1;


    public void setNetStatus(int netStatus) {
        this.netStatus = netStatus;
    }

    private static SessionViewModel INSTANCE;

    public static SessionViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new SessionViewModel();
        return INSTANCE;
    }

    public MutableLiveData<Session> getSessionMutableLiveData() {
        return sessionMutableLiveData;
    }

    public void getSession(int sessionID,int role , Context context) {
        if (netStatus == 1) {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

            clientJsonApi.getSessionFromDatabase(sessionID).enqueue(new Callback<Session>() {
                @Override
                public void onResponse(Call<Session> call, Response<Session> response) {
                    if (response.isSuccessful()) {

                        sessionMutableLiveData.setValue(response.body());
                    } else {
                        // make toast.
                    }
                }

                @Override
                public void onFailure(Call<Session> call, Throwable t) {
                    //make toast.
                }
            });
        } else {

            if(role == 3)
            sessionMutableLiveData.setValue(PatientRoom.getInstance(context).patientDao().getSessionByID(sessionID));
            else if(role == 2)
            sessionMutableLiveData.setValue(DoctorRoom.getInstance(context).doctorDao().getSessionByID(sessionID));
        }
    }

}
