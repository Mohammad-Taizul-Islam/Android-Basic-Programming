package com.example.activitylifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    companion object{
        var TAG=MainActivity.javaClass.name
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreate Method")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG,"onRestart Method")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart Method")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume Method")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause Method")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop Method")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy Method")
    }
}
