package com.example.dday.remindmethere;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class Constants {
    private Constants() {
    }

    private static final String PACKAGE_NAME = "com.example.dday.remindmethere";

    static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";

    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 1 ;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;

   // static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km

    static final float GEOFENCE_RADIUS_IN_METERS = 500;

    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<>();

    static {
        // San Francisco International Airport.
        BAY_AREA_LANDMARKS.put("IICT Building ,SUST", new LatLng(24.917973, 91.830876));

        // Googleplex.
        BAY_AREA_LANDMARKS.put("Bishmillah Restaurant,Sylhet", new LatLng(24.910856,91.832710));
    }
}
