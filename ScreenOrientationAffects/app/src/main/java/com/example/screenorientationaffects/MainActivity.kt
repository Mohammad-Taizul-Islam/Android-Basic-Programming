package com.example.screenorientationaffects

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        var TAG=MainActivity.javaClass.name
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG,"onCreate Method")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("sampleKey",et_sample.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        et_sample.getTe
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
