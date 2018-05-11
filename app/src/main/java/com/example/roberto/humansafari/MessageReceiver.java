package com.example.roberto.humansafari;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.roberto.humansafari.activity.master.MasterMainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by What's That Lambda on 11/6/17.
 */

public class MessageReceiver extends FirebaseMessagingService {
    private static final int REQUEST_CODE = 1;
    private static final int NOTIFICATION_ID = 6578;
    private static final String TAG = "Push";

    public MessageReceiver() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if(remoteMessage.getData().size() > 0){
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            final String title = remoteMessage.getData().get("title");
            final String message = remoteMessage.getData().get("body");

            showNotifications(title, message);
        }
    }

    private void showNotifications(String title, String msg) {
        Intent i = new Intent(this, MasterMainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE,
                i, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentText(msg)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_logo)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, notification);
    }
}