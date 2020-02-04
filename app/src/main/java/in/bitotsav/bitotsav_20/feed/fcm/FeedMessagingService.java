package in.bitotsav.bitotsav_20.feed.fcm;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import in.bitotsav.bitotsav_20.R;

public class FeedMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("FeedMessagingService", "onMessageReceived: " + remoteMessage.getNotification().getBody());
        showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    private void showNotification(String title, String desc) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Bitotsav20")
                .setContentTitle(title)
                .setContentText(desc)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_background);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());
    }
}
