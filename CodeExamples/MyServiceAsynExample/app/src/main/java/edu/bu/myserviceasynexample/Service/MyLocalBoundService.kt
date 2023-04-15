package edu.bu.myserviceasynexample.Service

import android.app.Service
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*

class MyLocalBoundService : Service() {
    private val myBinder: IBinder = MyLocalBinder()
    private var bound = false
    override fun onCreate() {
        Log.d(TAG, "Service onCreate")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "service onBind")
        bound = true
        return myBinder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(TAG, "service onUnBind")
        bound = false
        return true
    }

    override fun onDestroy() {
        Log.d(TAG, "Service onDestroy")
    }

    inner class MyLocalBinder : Binder() {
        val service: MyLocalBoundService
            get() = this@MyLocalBoundService
    }

    @get:RequiresApi(api = Build.VERSION_CODES.N)
    val currentTime: String
        get() {
            if (bound) {
                val dateFormat = SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US)
                return dateFormat.format(Date())
            }
            return ""
        }

    companion object {
        private const val TAG = "LocalBoundService"
    }
}