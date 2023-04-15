package edu.bu.myserviceasynexample.Service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import edu.bu.myserviceasynexample.MainActivity
import edu.bu.myserviceasynexample.R
import android.app.PendingIntent
import android.widget.Toast
import android.app.AlarmManager
import androidx.annotation.RequiresApi


class MyFgService : Service() {
    companion object {
        private const val NOTIFICATION_SERVICE_ID = 101
        private const val LOG_TAG = "ForegroundService"
    }

    object ACTION {
        const val PLAY_ACTION = "edu.bu.myserviceasynexample.action.play"
        const val ALARM_ACTION = "edu.bu.myserviceasynexample.action.alarm"
        const val STARTFOREGROUND_ACTION = "edu.bu.myserviceasynexample..action.startforeground"
        const val STOPFOREGROUND_ACTION = "edu.bu.myserviceasynexample.action.stopself"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent.action.equals (ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "start the foreground service ")
            val notification = buildNotification()
//             // Use notificationManager to send the notification
//            //if you need to send the notification explicitly
//            val notificationManager =
//                this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.notify(NOTIFICATION_SERVICE_ID, notification)
            startForeground(NOTIFICATION_SERVICE_ID, notification)
        } else if (intent.action.equals (ACTION.STOPFOREGROUND_ACTION)) {
            stopForeground(true)
            stopSelf()
        } else if (intent.action.equals (ACTION.PLAY_ACTION)) {
            Log.i(LOG_TAG, "play action" )
            Toast.makeText (this, "play now...", Toast.LENGTH_LONG).show ();
        } else if (intent.action.equals (ACTION.ALARM_ACTION)) {
            Log.i(LOG_TAG, "play later action")
            Toast.makeText (this, "play later ...", Toast.LENGTH_LONG).show ();
            val mAlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            //create a pendingIntent to specify the action for the alarm
            val serviceIntent = Intent(this, MyFgService::class.java)
            serviceIntent.action = ACTION.PLAY_ACTION
            val pendingIntent = PendingIntent.getService(
                this, 0,
                serviceIntent, 0
            )

            // cancel the previous alarms
            mAlarmManager.cancel(pendingIntent)

            // set the alarm after 5 second
            mAlarmManager[AlarmManager.RTC_WAKEUP, 5000] = pendingIntent
            // set a repeated alarm after 5 second with every 15 minutes
            //mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 5000,
            //    AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent)
        }
        return START_STICKY
    }

    fun buildNotification(): Notification {
        val channelId = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val newChannelId = "ChannelId"
                val channelName = "My Background Service"
                val channel =
                    NotificationChannel(newChannelId, channelName,
                        NotificationManager.IMPORTANCE_DEFAULT)
                val service = getSystemService(Context.NOTIFICATION_SERVICE) as
                        NotificationManager
                service.createNotificationChannel(channel)
                newChannelId
            } else
                ""
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
//        notificationIntent.setAction(ACTION.MAIN_ACTION)
//        notificationIntent.setFlags(
//            Intent.FLAG_ACTIVITY_NEW_TASK
//                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        )
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )
        val playIntent = Intent(this, MyFgService::class.java).apply {
            setAction(ACTION.PLAY_ACTION)
        }
        val pplayIntent: PendingIntent = PendingIntent.getService(
            this, 0,
            playIntent, 0
        )

        val playlaterIntent = Intent(this, MyFgService::class.java).apply{
            action = ACTION.ALARM_ACTION
        }
        val pplaylaterIntent = PendingIntent.getService(
            this,
            0, playlaterIntent, 0
        )

        return NotificationCompat.Builder(this, channelId())
            .setContentTitle("Example Music Player")
            .setTicker("Example Music Player")
            .setContentText("My Music")
            .setSmallIcon(R.drawable.ic_service)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .addAction(
                R.drawable.ic_play, "Play",
                pplayIntent
            )
            .addAction(
                R.drawable.ic_alarm, "Play later",
                pplaylaterIntent
            ).build()
    }



    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_TAG, "In onDestroy")
    }

    override fun onBind(intent: Intent): IBinder? {
        // Used only in case of bound services.
        return null
    }


}