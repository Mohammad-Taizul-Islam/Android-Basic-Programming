package com.example.dday.locationalerttest;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainActivity extends AppCompatActivity implements ShakeEventManager.ShakeListener{

    private static final String TAG=MainActivity.class.getSimpleName();
    private static final int MY_PERMISSION_REQUEST_LOCATION = 99;
    private static  final int LOCATION_PERMISSION_CODE=420;
    private static  final int CONTACT_ACCESS_PERMISSION_CODE=420;
    private MultiAutoCompleteTextView locationText;
    private EditText numberEditText,tvDes;
    final Context context = this;
    private ShakeEventManager sd;


    //for location

    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL=10*1000;
    private long FASTEST_INTERVAL=2*1000;

    //for place

    private Geocoder geocoder;



    //for SMS
    private int my_request_permission=1;
    private String SEND="sent_message";
    private String DELIVERD="deliverd_message";

    PendingIntent sentPI,deliverdPI;




    //for contacts Retrive

    Intent intent ;

    //for Toolbar
    private Toolbar mTopToolbar;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NUMBER = "text";
    public static final String TEXT = "text";
      String text;
     String number;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        EnableRuntimePermission();
        locationText=findViewById(R.id.multiAutoCompleteTextView);
        numberEditText=findViewById(R.id.editTextNumber);
        tvDes=findViewById(R.id.editText3);


        geocoder=new Geocoder(this, Locale.getDefault());


        //for SMS

        sentPI=PendingIntent.getBroadcast(this,0,new Intent(SEND),0);
        deliverdPI=PendingIntent.getBroadcast(this,0,new Intent(DELIVERD),0);




        //for ToolBar Initialization

        mTopToolbar =findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);





        //shake handler

        sd = new ShakeEventManager();
        sd.setListener(this);
        sd.init(this);


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bookMarksNumber) {
            //Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();

            saveNumber();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void saveNumber() {
       Intent intent=new Intent(this,SaveActivity.class);
       startActivity(intent);
       }




    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(MainActivity.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.READ_CONTACTS},CONTACT_ACCESS_PERMISSION_CODE);

        }
    }




    @Override
    public void onActivityResult(int RequestCode, int ResultCode, Intent ResultIntent) {

        super.onActivityResult(RequestCode, ResultCode, ResultIntent);

        switch (RequestCode) {

            case (7):
                if (ResultCode == Activity.RESULT_OK) {

                    Uri uri;
                    Cursor cursor1, cursor2;
                    String TempNameHolder, TempNumberHolder, TempContactID, IDresult = "" ;
                    int IDresultHolder ;

                    uri = ResultIntent.getData();

                    cursor1 = getContentResolver().query(uri, null, null, null, null);

                    if (cursor1.moveToFirst()) {

                        TempNameHolder = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        TempContactID = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));

                        IDresult = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        IDresultHolder = Integer.valueOf(IDresult) ;

                        if (IDresultHolder == 1) {

                            cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + TempContactID, null, null);

                            while (cursor2.moveToNext()) {

                                TempNumberHolder = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                //name.setText(TempNameHolder);

                               // number.setText(TempNumberHolder);
                                numberEditText.setText(TempNumberHolder);


                            }
                        }

                    }
                }
                break;
        }
    }



    private boolean checkPermission()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            return  true;
        }else {
            return false;
        }
    }


    protected  void startLocationUpdate()
    {

        //create location request to start receiving location update

        mLocationRequest=new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);


        LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest=builder.build();


        SettingsClient settingsClient= LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);


        if(checkPermission()){
            getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            // do work here
                            onLocationChanged(locationResult.getLastLocation());
                        }
                    },
                    Looper.myLooper());
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
            {
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    startLocationUpdate();
                }else{
                    Snackbar.make(findViewById(R.id.root_layout),"Location permission is denied",Snackbar.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }

    public void getLastLocation()
    {

        FusedLocationProviderClient client=new FusedLocationProviderClient(this);

        if(checkPermission())
        {
            client.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location!=null)
                            {
                                onLocationChanged(location);
                            }
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });


        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
        }

    }


    public void onLocationChanged(Location location) {

        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        //locationText.setText(msg);
        // You can now create a LatLng Object for use with maps
        //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName = addresses.get(0).getAddressLine(0);
       // String stateName = addresses.get(0).getAddressLine(1);
       // String countryName = addresses.get(0).getAddressLine(2);
        locationText.setText(cityName);
        //locationText.setText(stateName);
        //locationText.setText(countryName);

    }




    @Override
    protected void onResume() {
        super.onResume();

        sd.register();

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SEND));




    //---when the SMS has been delivered---
    registerReceiver(new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            switch (getResultCode())
            {
                case Activity.RESULT_OK:
                    Toast.makeText(getBaseContext(), "SMS delivered",
                            Toast.LENGTH_SHORT).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(getBaseContext(), "SMS not delivered",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }, new IntentFilter(DELIVERD));

    //registerReceiver(sendBroadcastReceiver,new IntentFilter(SEND));
    //registerReceiver(deliverdBroadcastReceiver,new IntentFilter(DELIVERD))
}





    public void sendMessage(View view) {


        String number=numberEditText.getText().toString();
        text= String.valueOf(tvDes.getText());
        String texts=text+locationText.getText().toString();


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},my_request_permission);
        }else{

            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(number,null,texts,sentPI,deliverdPI);
        }
    }


    public void autoSendMessage() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);


        number =sharedPreferences.getString(NUMBER,"");
        text =sharedPreferences.getString(TEXT,"");

        String texts=text+locationText.getText().toString();


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},my_request_permission);
        }else{

            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(number,null,texts,sentPI,deliverdPI);
        }
    }




    //Location button

    public void getLocation(View view) {
        Log.i(TAG,"Permission granted");

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            if(checkPermission())
            {
                // startLocationUpdate();
                getLastLocation();

                Snackbar.make(findViewById(R.id.root_layout),"Permission is granted",Snackbar.LENGTH_INDEFINITE).show();
            }else {

                if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION))
                {

                    Log.i(TAG,"Permission showing rationale");
                    Snackbar.make(findViewById(R.id.root_layout),"Need permission to get Location",Snackbar.LENGTH_INDEFINITE).setAction("Enable",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
                                }
                            }).show();

                }else {

                    Log.i(TAG,"Requesting permission");
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSION_REQUEST_LOCATION);
                }

            }

        }else{

            startLocationUpdate();
            getLastLocation();
        }

    }

    public void getContacts(View view) {

        intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 7);
    }



    @Override
    public void onShake() {
        autoSendMessage();
        Toast.makeText(this, "Refresh data...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sd.deregister();
    }
}
