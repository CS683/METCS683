package edu.bu.metcs.projectportal

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action == Intent.ACTION_BATTERY_LOW) {
            Log.d("Receiver", "Battery is low")
        }
    }

}