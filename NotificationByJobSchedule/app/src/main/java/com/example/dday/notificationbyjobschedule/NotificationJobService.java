package com.example.dday.notificationbyjobschedule;


import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import static com.example.dday.notificationbyjobschedule.App.CHANNEL_1_ID;


public class NotificationJobService extends JobService {


    private NotificationManagerCompat notificationManager;

    @Override
    public boolean onStartJob(JobParameters params) {


        notificationManager = NotificationManagerCompat.from(this);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("JOB")
                .setContentText("Job running")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);


        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
