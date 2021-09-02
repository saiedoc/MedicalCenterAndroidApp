package ViewModels;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import data.ClientJsonApi;
import pojo.Appointment;
import pojo.DataChart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataChartViewModel extends ViewModel {


    MutableLiveData<List<DataChart>> dataChartMutableLiveData = new MutableLiveData<>();
    private int netStatus = 1;
    private static DataChartViewModel INSTANCE;

    public static DataChartViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new DataChartViewModel();
        return INSTANCE;
    }

    public void setNetStatus(int netStatus) {
        this.netStatus = netStatus;
    }


    public MutableLiveData<List<DataChart>> getAppointmentListMutableLiveData() {
        return dataChartMutableLiveData;
    }


    public void getData(int patientID, Context context)
    {
        if(netStatus == 1) {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

            clientJsonApi.getDataChartFromDataBase(patientID).enqueue(new Callback<List<DataChart>>() {
                @Override
                public void onResponse(Call<List<DataChart>> call, Response<List<DataChart>> response) {
                    if (response.isSuccessful()) {
                        dataChartMutableLiveData.setValue(response.body());
                    } else {
                        Toast.makeText(context, "Not found in server", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<List<DataChart>> call, Throwable t) {
                    Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();

                }
            });

        }else{
            Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();
        }
    }

}
