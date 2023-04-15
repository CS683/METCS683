package edu.bu.metcs.handlerthread

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import edu.bu.metcs.handlerthread.R
import android.util.Log
import android.view.View
import android.widget.Toast
import edu.bu.metcs.handlerthread.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var mainHandler: Handler
    private lateinit var threadHandler: Handler
    private lateinit var mHandlerThread: HandlerThread

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initHandler()

        binding.startLongOp.setOnClickListener {
            binding.displayText.setText("execute long operation")
            Log.d("Long Operation", "main thread id: " + Thread.currentThread().id)
            val delayStr = binding.delayTime!!.text.toString()
            val delay = if (delayStr != "") delayStr.toLong() else 0

           // startLongOpSameTh(delay)
            startLongOpDiffTh(delay)
        }
    }

    // called when clicking the display button
    fun startLongOpSameTh(delay: Long) {
            val i = LongOperation.run(delay)
            binding.displayText!!.text = "Done: i = $i at Thread " + Thread.currentThread().id
            mainHandler!!.postDelayed(
                { binding.displayText!!.text = "" },
                2000)
    }


    private fun initHandler() {
        // create a handler for the main thread
        mainHandler = Handler(mainLooper)
        // create a handler thread mHandlerThread
        mHandlerThread = HandlerThread("background handler thread")
        // start mHandlerThread  by calling its start() method
        mHandlerThread!!.start()

        threadHandler = Handler(mHandlerThread.looper)
    }


    // called when clicking the display button
    fun startLongOpDiffTh(delay: Long) {
        // get the delay value from the editview

        threadHandler!!.post{
            val i = LongOperation.run(delay)
       //     Toast.makeText(this, "Done1: i = $i at Thread " + Thread.currentThread().id,  Toast.LENGTH_LONG).show()
            mainHandler!!.post{
                binding.displayText.text = "Done: i = $i at Thread " + Thread.currentThread().id
                Toast.makeText(this, "Done1: i = $i at Thread " + Thread.currentThread().id,  Toast.LENGTH_LONG).show()
                mainHandler!!.postDelayed({ binding.displayText!!.text = ""
                    Log.d("Handler","clear text")},
                    2000)
            }
        }
    }
}