package com.example.activitylifecyclejava;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String TAG=MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate Method");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart Method");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onReStart Method");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume Method");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause Method");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop Method");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy Method");
    }
}
