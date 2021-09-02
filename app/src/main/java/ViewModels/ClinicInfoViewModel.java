package ViewModels;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import data.ClientJsonApi;
import off_room.AdminRoom;
import okhttp3.ResponseBody;
import pojo.ClinicInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicInfoViewModel extends ViewModel {


    MutableLiveData<ClinicInfo> clinicInfoMutableLiveData = new MutableLiveData<>();

    private int netStatus = 1;

    private static ClinicInfoViewModel INSTANCE;

    public void setNetStatus(int netStatus) {
        this.netStatus = netStatus;
    }

    public static ClinicInfoViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new ClinicInfoViewModel();
        return INSTANCE;
    }

    public MutableLiveData<ClinicInfo> getClinicInfoMutableLiveData() {
        return clinicInfoMutableLiveData;
    }

    public void getClinicInfo(Context context)
    {
        if(netStatus == 1)
        {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

            clientJsonApi.getClinicInfoFromDatabase().enqueue(new Callback<ClinicInfo>() {
                @Override
                public void onResponse(Call<ClinicInfo> call, Response<ClinicInfo> response) {
                    if(response.isSuccessful())
                    {
                        System.out.println(response.body().getAddress()+"  eyad");
                        AdminRoom.getInstance(context).adminDao().addClinicInfo(response.body());
                        clinicInfoMutableLiveData.setValue(response.body());
                    }else{
                        Toast.makeText(context, "Not found in server", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ClinicInfo> call, Throwable t) {
                    Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();

                }
            });
        }else{
           clinicInfoMutableLiveData.setValue(AdminRoom.getInstance(context).adminDao().getClinicInfo());
        }
    }

}
