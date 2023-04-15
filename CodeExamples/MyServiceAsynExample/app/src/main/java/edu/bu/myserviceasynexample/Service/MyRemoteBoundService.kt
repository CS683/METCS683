package edu.bu.myserviceasynexample.Service

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast

class MyRemoteBoundService : Service() {
    val handler = Looper.myLooper()?.let {
        Handler(it) { msg ->
            val data = msg.data
            val dataString = data.getString("MyString")
            Log.i(TAG, "received message: $dataString")
            Toast.makeText(applicationContext, dataString, Toast.LENGTH_LONG).show()
            true
        }
    }
    val myMessenger: Messenger = Messenger(handler)

    override fun onCreate() {
        Log.d(TAG, "Service onCreate")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "service onBind")
        return myMessenger.binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(TAG, "service onUnBind")
        return false
    }

    override fun onDestroy() {
        Log.d(TAG, "Service onDestroy")
    }


    companion object {
        private const val TAG = "RemoteBoundService"
    }
}