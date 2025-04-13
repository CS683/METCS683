package edu.bu.metcs.servicesexample.services

import android.util.Log
import java.lang.Exception

/**
 * Created by danazh on 10/3/17.
 */
class LongOperation {
    companion object {
        fun longWait(tag: String?, id: Int) {
            Log.i(tag, "$id  start running in thread ${Thread.currentThread().name}")
            var j = 0
            for ( i in 0..10000000)
                for (k in  0 until 1000)
                    j ++
            Log.i(tag, "$id  end running at $j in thread ${Thread.currentThread().name}")

        }
    }
}