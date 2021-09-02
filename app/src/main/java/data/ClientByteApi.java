package data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientByteApi {


    private static final String BASE_URL="http://192.168.43.122:5050/";
    private ApiInterface apiInterface;
    private static ClientByteApi INSTANCE;

    public ClientByteApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ClientByteApi getINSTANCE() {
        if(null == INSTANCE){

            INSTANCE = new ClientByteApi();
        }
        return INSTANCE;
    }


    public Call<ResponseBody> getAttachmentFromDatabase(int attachmentID)
    {
        return apiInterface.getAttachment(attachmentID);
    }

}
