package com.example.dday.shakecounterbackgound;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_1_ID="channel1";

    @Override
    public void onCreate() {

        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(
                    CHANNEL_1_ID ,
                    "JOB" ,
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("This demo");

            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel);
        }
    }
}
