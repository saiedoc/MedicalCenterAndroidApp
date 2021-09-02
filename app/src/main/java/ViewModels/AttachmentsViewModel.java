package ViewModels;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import data.ClientJsonApi;
import off_room.DoctorRoom;
import off_room.PatientRoom;
import pojo.Attachments;

import java.util.List;

import pojo.Patient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttachmentsViewModel extends ViewModel {

   MutableLiveData<List<Attachments>> attachmentsMutableLiveData = new MutableLiveData<>();
   private int netStatus = 1;

    public void setNetStatus(int netStatus) {
        this.netStatus = netStatus;
    }

    private static AttachmentsViewModel INSTANCE;

    public static AttachmentsViewModel getINSTANCE(){
        if(INSTANCE == null)
            INSTANCE = new AttachmentsViewModel();
        return INSTANCE;
    }

    public MutableLiveData<List<Attachments>> getAttachmentsMutableLiveData() {
        return attachmentsMutableLiveData;
    }

    public void getAttachments(int sessionID, int role , Context context)
    {
        if(netStatus == 1)
        {
            ClientJsonApi clientJsonApi = ClientJsonApi.getINSTANCE();


            clientJsonApi.getAttachmentsFromDatabase(sessionID).enqueue(new Callback<List<Attachments>>() {
                @Override
                public void onResponse(Call<List<Attachments>> call, Response<List<Attachments>> response) {
                    if(response.isSuccessful())
                    {
                        for (int i = 0; i < response.body().size() ; i++) {
                            response.body().get(i).setSessionID(sessionID);
                        }

                        if(role ==2)
                            DoctorRoom.getInstance(context).doctorDao().addAttachments(response.body());
                        else if(role == 3)
                            PatientRoom.getInstance(context).patientDao().addAttachments(response.body());

                        attachmentsMutableLiveData.setValue(response.body());
                    }else{
                        Toast.makeText(context, "Not found in server", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<List<Attachments>> call, Throwable t) {
                    Toast.makeText(context, "Connection Failure ", Toast.LENGTH_LONG).show();

                }
            });
        }else{
            if(role ==2)
                attachmentsMutableLiveData.setValue(DoctorRoom.getInstance(context).doctorDao().getAttachments(sessionID));
            else if(role==3)
                attachmentsMutableLiveData.setValue(PatientRoom.getInstance(context).patientDao().getAttachmentsBySessionID(sessionID));
        }
    }
}
