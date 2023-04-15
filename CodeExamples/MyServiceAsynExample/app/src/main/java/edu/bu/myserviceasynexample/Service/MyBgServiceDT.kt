package edu.bu.myserviceasynexample.Service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import edu.bu.myserviceasynexample.Service.MyBgServiceDT
import edu.bu.myserviceasynexample.Service.LongOperation
import android.widget.Toast

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


//    Toast.makeText(
//        applicationContext,
//        "service finished", Toast.LENGTH_LONG
//    ).show()

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