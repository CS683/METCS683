package edu.bu.myserviceasynexample

import android.app.Application
import androidx.work.WorkManager

class MyServiceApplication: Application() {
    lateinit var myWorkManager: WorkManager
    override fun onCreate() {
        super.onCreate()
    //    myWorkManager = WorkManager.getInstance(applicationContext)
    }
}