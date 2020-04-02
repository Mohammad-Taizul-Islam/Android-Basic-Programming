package com.example.dday.wherewhattodo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.JobIntentService;

public class GeofenceTransitionsJobIntentService extends JobIntentService{


    private static final int JOB_ID = 573;

    private static final String TAG = "GeofenceTransitionsIS";

    private static final String CHANNEL_ID = "channel_01";


    public static void enqueueWork(Context context, Intent intent)
    {

    }
}
