package edu.bu.myserviceasynexample.Service

import android.content.Context
import android.util.Log
import androidx.work.*
// when MyWorker1 inherits from the Worker class,
// WorkerManager automatically runs it in a background thread
// It can also inherit from the CoroutineWorker class
// WorkManager will use a default dispatcher.
class MyWorker1 (context: Context,
                 workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        // your work here
        val id = inputData.getString("inputId")
        //mimic a different background work
        Thread.sleep(3000L)
        Log.d("Work1", "thread id: " + Thread.currentThread().id)
        val outputData = Data.Builder()
            .putString("outputId", id)
            .build()
        return Result.success(outputData)
    }
}