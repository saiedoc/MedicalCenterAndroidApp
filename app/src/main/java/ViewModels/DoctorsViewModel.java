package ViewModels;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clinic.MainActivity;

import data.ClientJsonApi;
import off_room.AdminRoom;
import pojo.Doctor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorsViewModel extends ViewModel {

    MutableLiveData<List<Doctor>> doctorsListViewModel = new MutableLiveData<>();

    private int netStatus = 1;

    private static DoctorsViewModel INSTANCE;

    public void setNetStatus(int netStatus) {
        this.netStatus = netStatus;
    }

    public static DoctorsViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new DoctorsViewModel();
        return INSTANCE;
    }

    public MutableLiveData<List<Doctor>> getDoctorsListViewModel() {
        return doctorsListViewModel;
    }

    public void getDoctors(Context context) {
        if (netStatus == 1) {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

            clientJsonApi.getDoctorsFromDatabase().enqueue(new Callback<List<Doctor>>() {
                @Override
                public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                    if (response.isSuccessful()) {
                        AdminRoom.getInstance(context).adminDao().addDoctors(response.body());
                        doctorsListViewModel.setValue(response.body());
                    } else {
                        Toast.makeText(context, "Not found in server", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Doctor>> call, Throwable t) {
                    Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();
                }
            });
        } else {
           doctorsListViewModel.setValue(AdminRoom.getInstance(context).adminDao().getDoctors());
        }
    }

    public void getDoctorsByWorkday(String dayname) {
        if (netStatus == 1) {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

            clientJsonApi.getDoctorsByWorkdayFromDatabase(dayname).enqueue(new Callback<List<Doctor>>() {
                @Override
                public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                    if (response.isSuccessful()) {
                        doctorsListViewModel.setValue(response.body());

                    } else {
                        Toast.makeText(MainActivity.getContext(), "Not found in server", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Doctor>> call, Throwable t) {
                    Toast.makeText(MainActivity.getContext(), "Connection Failure ", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(MainActivity.getContext(), "Connection Failure ", Toast.LENGTH_LONG).show();
        }
    }

}
