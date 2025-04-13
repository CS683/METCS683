package edu.bu.metcs.servicesexample.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyBgServiceDT : Service() {
   //var handler:Handler = Handler(Looper.getMainLooper)
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.d(TAG, "Service onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service onStartCommand $startId")


        Thread {
            LongOperation.longWait(TAG, startId)
            Log.d(TAG, "Finish long run operation $startId")

        }.start()


        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d(TAG, "Service onDestroy")
    }

    companion object {
        private const val TAG = "BgServiceDiffThread"
    }
}