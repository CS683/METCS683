package edu.bu.metcs.handlerthread

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class TaskUseExecutor {
    // if you only need one thread
    companion object {
        private  val executor1: Executor = Executors.newSingleThreadExecutor()
    }

    // If you want to use multiple threads
    //    private final ThreadPoolExecutor executor2 = new ThreadPoolExecutor(2, 4,
    //            60L,
    //            TimeUnit.SECONDS,
    //            new LinkedBlockingQueue<Runnable>()
    //    );
    private val mainHandler = Handler(Looper.getMainLooper())

    interface Callback {
        fun updateUI(str: String?)
    }

    fun executeTask(callback: Callback, delay: Long) {
        executor1.execute { // do something
            val i = LongOperation.run(delay)
            val threadid = Thread.currentThread().id
            mainHandler.post { callback.updateUI("use executor Done: threadid $threadid i = $i for dely = $delay") }
        }
    }
}