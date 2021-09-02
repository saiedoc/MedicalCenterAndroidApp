package ViewModels;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clinic.LoginActivity;

import data.ClientJsonApi;
import pojo.Role;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginViewModel extends ViewModel {

    MutableLiveData<Role> roleMutableLivedata = new MutableLiveData<>();

    private static LoginViewModel INSTANCE;

    public static LoginViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new LoginViewModel();
        return INSTANCE;
    }

    public MutableLiveData<Role> getRoleMutableLivedata() {
        return roleMutableLivedata;
    }

    public void getRole(String username, String password) {
        ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

        clientJsonApi.getRolefromDatabase(username, password).enqueue(new Callback<Role>() {
            @Override
            public void onResponse(Call<Role> call, Response<Role> response) {

                if (response.isSuccessful()) {
                    Log.d("res", String.valueOf(response.body().getAccount_id()));
                    roleMutableLivedata.setValue(response.body());
                } else {
                    Toast.makeText(LoginActivity.getContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Role> call, Throwable t) {

                Toast.makeText(LoginActivity.getContext(), "Connection error", Toast.LENGTH_LONG).show();

            }
        });

    }

}
