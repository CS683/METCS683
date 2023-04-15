package edu.bu.myserviceasynexample.Service

import android.util.Log
import java.lang.Exception

/**
 * Created by danazh on 10/3/17.
 */
class LongOperation {
    companion object {
        fun longWait(tag: String?, id: Int) {
            for (i in 0..2) {
                Log.i(tag, "$id  start running $i")
                val endTime = System.currentTimeMillis() + 5 * 1000
                while (System.currentTimeMillis() < endTime) {
                    for (j in 0..10000000);
                }
                Log.i(tag, "$id  Still running")
            }
        }
    }
}