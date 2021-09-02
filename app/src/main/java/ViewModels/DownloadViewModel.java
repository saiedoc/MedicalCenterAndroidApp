package ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import data.ClientByteApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadViewModel extends ViewModel {


    MutableLiveData<ResponseBody> byteMutableLiveData = new MutableLiveData<>();

    private static DownloadViewModel INSTANCE;

    public static DownloadViewModel getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new DownloadViewModel();
        return INSTANCE;
    }

    public MutableLiveData<ResponseBody> getByteMutableLiveData() {
        return byteMutableLiveData;
    }

    public void getFile(int attachmentID) {
        ClientByteApi clientByteApi = ClientByteApi.getINSTANCE();
        clientByteApi.getAttachmentFromDatabase(attachmentID).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println("getting the bytes");
                    byteMutableLiveData.setValue(response.body());
                } else {
                    //make toast.
                    System.out.println("hhhhh");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //make toast.
            }
        });
    }

}
