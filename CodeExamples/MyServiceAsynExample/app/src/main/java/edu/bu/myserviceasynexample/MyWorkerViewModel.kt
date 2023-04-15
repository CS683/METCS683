package edu.bu.myserviceasynexample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.*
import edu.bu.myserviceasynexample.Service.MyWorker1
import edu.bu.myserviceasynexample.Service.MyWorker2

class MyWorkerViewModel(application: Application): AndroidViewModel(application) {
    private val workManager = WorkManager.getInstance(application)
    private lateinit var myWork1Request:OneTimeWorkRequest
    private lateinit var myWork2Request:OneTimeWorkRequest
    lateinit var work1LiveData: LiveData<WorkInfo>
    lateinit var work2LiveData: LiveData<WorkInfo>
    init {
        val networkConstraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val inputData1 = Data.Builder().putString("inputId", "myid1").build()
        val inputData2 = Data.Builder().putString("inputId", "myid2").build()

        myWork1Request = OneTimeWorkRequest.Builder(MyWorker1::class.java)
            .setConstraints(networkConstraints)
            .setInputData(inputData1).build()

        myWork2Request = OneTimeWorkRequest.Builder(MyWorker2::class.java)
            .setConstraints(networkConstraints)
            .setInputData(inputData2).build()

        work1LiveData =
            workManager.getWorkInfoByIdLiveData(myWork1Request.id)

        work2LiveData =
            workManager.getWorkInfoByIdLiveData(myWork2Request.id)

    }
    fun startMyWork() {
        workManager.beginWith(myWork1Request)
            .then(myWork2Request)
            .enqueue()
    }
}
