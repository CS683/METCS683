package edu.bu.projectportal.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import edu.bu.projectportal.LoginActivity

class MyBroadcastReciever: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if  (intent.action == Intent.ACTION_BATTERY_CHANGED) {
            Toast.makeText(
                context, "battery is changed",
                Toast.LENGTH_LONG,
            ).show()
        }
    }
}