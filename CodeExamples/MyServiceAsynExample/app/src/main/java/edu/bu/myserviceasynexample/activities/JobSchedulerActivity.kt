package edu.bu.myserviceasynexample.activities

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import edu.bu.myserviceasynexample.R
import edu.bu.myserviceasynexample.Service.MyJobService
import java.util.*
import kotlin.random.Random.Default.nextInt

class JobSchedulerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_scheduler)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleJob(v: View?) {
        val jobId = (1..10).random()
        val myJobInfo: JobInfo =
            JobInfo.Builder(
            jobId,
            ComponentName(this, MyJobService::class.java))
                .apply {
                    setRequiresBatteryNotLow(true)
                    setExtras(PersistableBundle().apply{putInt("id",jobId)})
                }.build()
        val scheduler: JobScheduler =
                getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        Toast.makeText(this, "Schedule Job $jobId ", Toast.LENGTH_SHORT).show()
        scheduler.schedule(myJobInfo)

    }

}