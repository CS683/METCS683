package edu.bu.myserviceasynexample.Service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class MyBgServiceST  //empty constructor
    : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.d(TAG, "Service onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service onStartCommand ")

        //some background operation here
        LongOperation.longWait(TAG, startId)
        Toast.makeText(this, "service started", Toast.LENGTH_LONG).show()
        stopSelf()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "Service onDestroy")
    }

    companion object {
        private const val TAG = "BgServiceSameThread"
    }
}