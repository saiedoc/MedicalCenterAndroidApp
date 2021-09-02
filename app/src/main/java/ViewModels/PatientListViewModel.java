package ViewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;


import data.ClientJsonApi;
import off_room.AdminRoom;
import off_room.DoctorRoom;
import pojo.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientListViewModel {
    MutableLiveData<List<Patient>> patientlistMutableLiveData = new MutableLiveData<>();
    private int netStatus = 1;

    public void setNetStatus(int netStatus) {
        this.netStatus = netStatus;
    }

    private static PatientListViewModel INSTANCE;

    public static PatientListViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new PatientListViewModel();
        return INSTANCE;
    }

    public MutableLiveData<List<Patient>> getPatientlistMutableLiveData() {
        return patientlistMutableLiveData;
    }

    public void getPatients(int docid, Context context) {
        if (netStatus == 1) {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

            clientJsonApi.getPatientsFromDatabase(docid).enqueue(new Callback<List<Patient>>() {
                @Override
                public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                    if (response.isSuccessful()) {
                        DoctorRoom.getInstance(context).doctorDao().addPatients(response.body());
                        patientlistMutableLiveData.setValue(response.body());
                    } else {
                        //Toast.makeText(PatientListFragment.GetContext(),"1 - Invalid Account ID",Toast.LENGTH_LONG).show();
                        //Log.d("ad","didn't work");
                    }
                }

                @Override
                public void onFailure(Call<List<Patient>> call, Throwable t) {
                    //Toast.makeText(PatientListFragment.GetContext(),"Connection error",Toast.LENGTH_LONG).show();

                }
            });
        } else {
            patientlistMutableLiveData.setValue(DoctorRoom.getInstance(context).doctorDao().getPatientList());
        }
    }

   public void getPatients(Context context)
   {
       if(netStatus == 1)
       {
           ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();

           clientJsonApi.getPatientsFromDatabase().enqueue(new Callback<List<Patient>>() {
               @Override
               public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                   if(response.isSuccessful())
                   {
                       AdminRoom.getInstance(context).adminDao().addPatients(response.body());
                       patientlistMutableLiveData.setValue(response.body());
                   }else{

                   }
               }

               @Override
               public void onFailure(Call<List<Patient>> call, Throwable t) {

               }
           });
       }else{
        patientlistMutableLiveData.setValue(AdminRoom.getInstance(context).adminDao().getPatients());
       }
   }

}
