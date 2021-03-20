package com.example.remindme;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;


public class Notification  extends Application {
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    private void createNotificationChannels() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "My Chanel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("lxjnf");

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID, "My Chanel 2", NotificationManager.IMPORTANCE_LOW);
            channel2.setDescription("lxjnf");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.getNotificationChannel("channel1");
            manager.getNotificationChannel("channel2");
        }
    }
}
