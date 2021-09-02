package ViewModels;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import data.ClientJsonApi;
import off_room.DoctorRoom;
import pojo.Doctor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorViewModel extends ViewModel {

    MutableLiveData<Doctor> doctorMutableLiveData = new MutableLiveData<>();
    private int netStatus = 1;

    private static DoctorViewModel INSTANCE;


    public static DoctorViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new DoctorViewModel();
        return INSTANCE;
    }

    public void setNetStatus(int netStatus) {
        this.netStatus = netStatus;
    }

    public MutableLiveData<Doctor> getDoctorMutableLiveData() {
        return doctorMutableLiveData;
    }

    public void getDoctorLiveData(int accountID,Context context) {
        if (netStatus == 1) {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

            clientJsonApi.getDocProFromDatabase(accountID).enqueue(new Callback<Doctor>() {
                @Override
                public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                    if (response.isSuccessful()) {
                        doctorMutableLiveData.setValue(response.body());
                    } else {
                        Toast.makeText(context, "Not found in server", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Doctor> call, Throwable t) {
                    Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();

        }
    }

public void getDoctorByDoctorID(int doctorID, Context context)
{
    if(netStatus == 1)
    {
        ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

        clientJsonApi.getDoctorByDoctorIDFromDatabase(doctorID).enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                if (response.isSuccessful())
                {
                    DoctorRoom.getInstance(context).doctorDao().addDoctor(response.body());
                    System.out.println(response.body().getName()+"   salem");
                    doctorMutableLiveData.setValue(response.body());
                }else{
                    Toast.makeText(context, "Not found in server", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();

            }
        });
    }else{

          doctorMutableLiveData.setValue(DoctorRoom.getInstance(context).doctorDao().getDoctor(doctorID));
    }
}
}
