package edu.bu.myserviceasynexample.Service

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.job.JobService
import android.app.job.JobParameters
import android.util.Log
import android.widget.Toast

class MyJobService : JobService() {
    override fun onStartJob(params: JobParameters): Boolean {
        val id = params.extras.getInt("id")
        Log.d("Jobservice", "onStart: Job $id is scheduled on Thread id " + Thread.currentThread().id)
        // do some work here
        //mimic a background work
        Thread.sleep(10000L)
        Log.d("Jobservice", "onStart: Job $id is done ")
        return false
    }

    override fun onStopJob(params: JobParameters): Boolean {
        val id = params.extras.getInt("id")
        Log.d("Jobservice", "onStart: Job $id is done ")
        return false
    }
}