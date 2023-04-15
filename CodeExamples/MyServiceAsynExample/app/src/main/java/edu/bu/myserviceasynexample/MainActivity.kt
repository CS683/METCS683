package edu.bu.myserviceasynexample

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.bu.myserviceasynexample.Service.MyBgServiceDT
import edu.bu.myserviceasynexample.Service.MyBgServiceST
import edu.bu.myserviceasynexample.Service.MyFgService
import edu.bu.myserviceasynexample.activities.BoundServiceActivity
import edu.bu.myserviceasynexample.activities.JobSchedulerActivity
import edu.bu.myserviceasynexample.activities.WorkManagerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        findViewById<FloatingActionButton>(R.id.fab).hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val textView:TextView = findViewById<TextView>(R.id.textview)
        when (item.itemId) {
            R.id.bgserv -> {
                textView.text = "start background service"
                startService(Intent(this, MyBgServiceST::class.java))
            }
            R.id.bgservDfT -> {
                textView.text = "start background service in different thread"
                startService(Intent(this, MyBgServiceDT::class.java))
            }

            R.id.boundserv -> startActivity(Intent(this, BoundServiceActivity::class.java))
            R.id.fgserv -> {
                textView.text = "start a foreground service"
                ContextCompat.startForegroundService(this,
                    Intent(
                        MyFgService.ACTION.STARTFOREGROUND_ACTION,
                        null,
                        this,
                        MyFgService::class.java
                    )
                )
            }
            R.id.stopFgserv -> {
                textView.text = "stop the foreground service"
                startService(Intent(MyFgService.ACTION.STOPFOREGROUND_ACTION,null,
                    this,
                    MyFgService::class.java))
            }
            R.id.jobscheduler -> startActivity(Intent(this, JobSchedulerActivity::class.java))
            R.id.workManager -> startActivity(Intent(this, WorkManagerActivity::class.java))

            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}