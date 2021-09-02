package data;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.clinic.MainActivity;
import com.example.clinic.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFCM extends FirebaseMessagingService {

    private String TAG = "token";
    private static String token;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG,"the message is received now from On message received");

        Log.d(TAG,""+  remoteMessage.getNotification().getTitle() +" / " +
                remoteMessage.getNotification().getBody() +
                " / "+remoteMessage.getNotification().getChannelId());

        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (MainActivity.getContext())
                .setSmallIcon(R.drawable.info_icon)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setColor(1000)
               .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }

    public static String getToken() {
        return token;
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        System.out.println("token : " + s);
        token = s;
        Log.d(TAG, "onNewToken");
       // retrofit request
    }
}
