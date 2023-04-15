package edu.bu.myserviceasynexample.Service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker2 (context: Context,
                   workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
     override suspend fun doWork(): Result {
        val id = inputData.getString("inputId")
        //mimic a background work
        Log.d("Work2", "thread id: " + Thread.currentThread().id)
        Thread.sleep(5000L)
        val outputData = Data.Builder()
            .putString("outputId", id)
            .build()
        return Result.success(outputData)
    }
}
