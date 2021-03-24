package com.example.remindme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID="ch1";
    @Override
    public void onReceive(Context context, Intent intent) {

        //get id & title of the alarm
        int notificationId= intent.getIntExtra("notificationId",0);
        String title = intent.getStringExtra("title");
        int impo = intent.getIntExtra("importance",0);
        //call SecondaryActivity when notification is tapped
        Intent mainIntent= new Intent(context, SecondaryActivity.class);
        PendingIntent contentIntent= PendingIntent.getActivity(
                context, 0,mainIntent,0
        );

        NotificationManager notificationManager=
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int importance=0;
        int c=Color.GRAY;
        String Reminder_title="Reminder";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // for API 26 AND ABOVE
            CharSequence channel_name = "My Notification";
switch(impo){
    case 0:
        importance = NotificationManager.IMPORTANCE_HIGH;
        c= Color.RED;
        Reminder_title="High Importance Reminder!!";
        break;
    case 2:
        importance = NotificationManager.IMPORTANCE_LOW;
        c= Color.YELLOW;
        Reminder_title="Low Importance Reminder";
        break;

}
            NotificationChannel channel= new NotificationChannel(CHANNEL_ID,channel_name, importance);
            notificationManager.createNotificationChannel(channel);
        }

        // prepare notification
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentTitle(Reminder_title)
                .setContentText(title)
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(c)
                .setAutoCancel(true);


                //notify
        notificationManager.notify(notificationId, builder.build());

    }
}
