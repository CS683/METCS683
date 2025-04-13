package edu.bu.metcs.servicesexample.services

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bu.metcs.servicesexample.services.MyWorker1
import edu.bu.metcs.servicesexample.services.MyWorker2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyWorkerViewModel @Inject constructor (application: Application) : ViewModel() {

    private val networkConstraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    private val inputData1 = Data.Builder().putString("inputId", "myid1").build()
    private val inputData2 = Data.Builder().putString("inputId", "myid2").build()
    private var myWork1Request:OneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker1::class.java)
        .setConstraints(networkConstraints)
        .setInputData(inputData1).build()

    private var myWork2Request:OneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker2::class.java)
        .setConstraints(networkConstraints)
        .setInputData(inputData2).build()

    private val workManager: WorkManager
    val work1State: StateFlow<String>
    val work2State: StateFlow<String>

    init {
        workManager = WorkManager.getInstance(application)

        work1State = workManager.getWorkInfoByIdFlow(myWork1Request.id).map { info ->
            info?.let {
                stateString(it.state) +
                        "(output id: " + it.outputData.getString("outputId") + ") "
            } ?: ""
        }.stateIn(
            scope = viewModelScope,
            SharingStarted.Eagerly,
            ""
        )

        work2State = workManager.getWorkInfoByIdFlow(myWork2Request.id).map { info ->
            info?.let {
                stateString(it.state) +
                        "(output id: " + it.outputData.getString("outputId") + ")"
            } ?: ""
        }.stateIn(
            scope = viewModelScope,
            SharingStarted.Eagerly,
            ""
        )
    }


    fun startMyWork() {
        workManager.beginWith(myWork1Request)
            .then(myWork2Request)
            .enqueue()
    }

    private fun stateString(state:WorkInfo.State):String =
        when (state){
            WorkInfo.State.ENQUEUED -> "enqueued"
            WorkInfo.State.RUNNING -> "running"
            WorkInfo.State.SUCCEEDED ->"succeeded"
            WorkInfo.State.FAILED -> "failed"
            WorkInfo.State.BLOCKED-> "blocked"
            WorkInfo.State.CANCELLED-> "cancelled"
            else ->""
        }

}
