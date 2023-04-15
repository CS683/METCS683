package edu.bu.metcs.handlerthread

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import edu.bu.metcs.handlerthread.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivityUseCoroutine: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startLongOp.setOnClickListener{

            binding.displayText.setText("execute long operation")
            Log.d ("Long Operation", "main thread id: "+ Thread.currentThread().id  )

            val delayStr = binding.delayTime!!.text.toString()
            val delay = if (delayStr!="") delayStr.toLong() else 0

            var bg = CoroutineScope(Dispatchers.Default).async {
                 longOperation(delay)
            }
            CoroutineScope(Dispatchers.Default).launch{
                var i = bg.await()
                binding.displayText.text = "Done i = $i at Thread " + Thread.currentThread().id
            }
        }
    }

    private  fun longOperation(threadDelay: Long): Long {
        var i: Long = 0
        var x: Long = 0
        Log.d("Long Operation",
            "start:  delay: $threadDelay at thread id " + Thread.currentThread().id)
        for (i in 0..threadDelay * 100000000) x++;
        Log.d("Long Operation", "done: i: $i $x")
        return x

    }
}
