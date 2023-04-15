package edu.bu.metcs.handlerthread

import android.content.Intent
import android.os.*
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import edu.bu.metcs.handlerthread.databinding.ActivityMainBinding

// This is an alternative approach using Executor
class MainActivityUseExecutor: AppCompatActivity(),TaskUseExecutor.Callback {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.displayText.text = ""
    }

    // called when clicking the display button
    fun display(v:View) {
        // get the delay value from the editview
        val delayStr = binding.delayTime!!.text.toString()
        val delay = if (delayStr!="") delayStr.toLong() else 0
        binding.displayText.text =
            """execute for (i = 0; i <${delay * 100000000}; i++) """
       TaskUseExecutor().executeTask(this, delay)
    }

    override fun updateUI(str: String?) {
        binding.displayText.text=str
    }

}