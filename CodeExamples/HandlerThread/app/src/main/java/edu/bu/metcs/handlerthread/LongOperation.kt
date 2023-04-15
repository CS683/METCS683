package edu.bu.metcs.handlerthread

import android.util.Log

object LongOperation {
    private const val TAG = "Long Operation"
    @JvmStatic
    fun run(threadDelay: Long): Long {
        var i:Long = 0
        var x:Long =0
        Log.d(TAG, "start:  delay: $threadDelay at thread id " + Thread.currentThread().id)
        for (i in 0 .. threadDelay * 100000000) x++;
        Log.d(TAG, "done: i: $i $x" )
        return x
    }
}