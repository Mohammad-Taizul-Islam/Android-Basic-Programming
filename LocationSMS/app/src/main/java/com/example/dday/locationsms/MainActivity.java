package com.example.dday.locationsms;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private  EditText editTextMessage;
    private EditText  editTextNumber;
    private int my_request_permission=1;
    private String SEND="sent_message";
    private String DELIVERD="deliverd_message";

    PendingIntent sentPI,deliverdPI;
    // BroadcastReceiver sendBroadcastReceiver,deliverdBroadcastReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextMessage=findViewById(R.id.editTextMessage);
        editTextNumber=findViewById(R.id.editTextPhoneNumber);

        sentPI=PendingIntent.getBroadcast(this,0,new Intent(SEND),0);
        deliverdPI=PendingIntent.getBroadcast(this,0,new Intent(DELIVERD),0);


    }


    @Override
    protected void onResume() {
        super.onResume();

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
        //registerReceiver(deliverdBroadcastReceiver,new IntentFilter(DELIVERD));




    }

    public void sendMessage(View view) {


        String number=editTextNumber.getText().toString();
        String texts=editTextMessage.getText().toString();


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},my_request_permission);
        }else{

            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(number,null,texts,sentPI,deliverdPI);
        }
    }
}
