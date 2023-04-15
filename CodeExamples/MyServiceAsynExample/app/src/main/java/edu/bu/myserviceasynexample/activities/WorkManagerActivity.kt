package edu.bu.myserviceasynexample.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkInfo
import edu.bu.myserviceasynexample.MyWorkerViewModel
import edu.bu.myserviceasynexample.databinding.ActivityWorkManagerBinding

class WorkManagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkManagerBinding
    private lateinit var myWorkViewModel: MyWorkerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val provider = ViewModelProvider(this)
        myWorkViewModel = provider.get(MyWorkerViewModel::class.java)

        binding.button.setOnClickListener{
            startBothWorks()
            it.setEnabled( false)
        }
    }

    private fun startBothWorks(){
        Log.d("main", "thread id: " + Thread.currentThread().id)

        myWorkViewModel.startMyWork()
        binding.work1.text = "work1 is enqueued"
        binding.work2.text = "work2 is enqueued"

        myWorkViewModel.work1LiveData.observe(this) { info ->
            binding.work1.text = "work1 is " +
                    stateString(info.state) + "(id: " +
                    info.outputData.getString("outputId") +")"

            if (info.state.isFinished) {
                Toast.makeText(this, "work1 is done", Toast.LENGTH_SHORT).show()

            }
        }

        myWorkViewModel.work2LiveData.observe(this) { info ->
            binding.work2.text = "work2 is " + stateString(info.state) + "(id: " +
                    info.outputData.getString("outputId")+ ")"

            if (info.state.isFinished) {
                // change your UI here
                Toast.makeText(this, "work2 is done", Toast.LENGTH_SHORT).show()
                // info.outputData.data.getInt("id")

            }
        }
    }

    private fun stateString(state:WorkInfo.State):String =
        when (state){
            WorkInfo.State.ENQUEUED -> "enqueued"
            WorkInfo.State.RUNNING -> "running"
            WorkInfo.State.SUCCEEDED ->"succeeded"
            WorkInfo.State.FAILED -> "failed"
            WorkInfo.State.BLOCKED-> "blocked"
            WorkInfo.State.CANCELLED-> "cancelled"
        }

}