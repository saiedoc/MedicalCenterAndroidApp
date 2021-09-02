package ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import data.ClientJsonApi;
import pojo.Specialization;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecializationsViewModel extends ViewModel {


    MutableLiveData<List<Specialization>> specializationsViewModel = new MutableLiveData<>();

    private int netStatus = 1;

    public void setNetStatus(int netStatus) {
        this.netStatus = netStatus;
    }

    private static SpecializationsViewModel INSTANCE;

    public static SpecializationsViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new SpecializationsViewModel();
        return INSTANCE;
    }

    public MutableLiveData<List<Specialization>> getSpecializationsViewModel() {
        return specializationsViewModel;
    }

    public void getSpecializations() {


        if (netStatus == 1) {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();


            clientJsonApi.getSpecializationsFromDatabase().enqueue(new Callback<List<Specialization>>() {
                @Override
                public void onResponse(Call<List<Specialization>> call, Response<List<Specialization>> response) {
                    if (response.isSuccessful()) {
                        specializationsViewModel.setValue(response.body());
                    } else {
                        //toast not successful
                    }
                }

                @Override
                public void onFailure(Call<List<Specialization>> call, Throwable t) {
                    //make toast
                }
            });
        } else {
            //room data
        }

    }

}
