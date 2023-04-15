package edu.bu.myserviceasynexample.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import edu.bu.myserviceasynexample.R
import edu.bu.myserviceasynexample.Service.MyLocalBoundService
import edu.bu.myserviceasynexample.Service.MyRemoteBoundService

class BoundServiceActivity : AppCompatActivity() {
    var myLocalBoundService: MyLocalBoundService? = null
    var isLocalBound = false
    var isRemoteBound = false
    var myMessenger: Messenger? = null
    var localConn: ServiceConnection? = null
    var remoteConn: ServiceConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bound_service)
    }

    fun startLocalBoundService(v: View?) {

        //create a service connection to monitor the connection with the boundservice
        localConn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val binder: MyLocalBoundService.MyLocalBinder = service as MyLocalBoundService.MyLocalBinder
                myLocalBoundService = binder.service
                isLocalBound = true
                Log.d(TAG, "local service is connected")
            }

            override fun onServiceDisconnected(name: ComponentName) {
                isLocalBound = false
                myLocalBoundService = null
                Log.d(TAG, "local service is disconnected")
            }
        }
        val intent = Intent(this, MyLocalBoundService::class.java)
        bindService(intent, localConn as ServiceConnection, Context.BIND_AUTO_CREATE)
    }

    fun startRemoteBoundService(v: View?) {
        remoteConn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, serviceBinder: IBinder) {
                myMessenger = Messenger(serviceBinder)
                isRemoteBound = true
                Log.d(TAG, "remote service is connected")
            }

            override fun onServiceDisconnected(name: ComponentName) {
                isRemoteBound = false
                Log.d(TAG, "remote service is disconnected")
            }
        }
        val intent = Intent(this, MyRemoteBoundService::class.java)
        bindService(intent, remoteConn as ServiceConnection, Context.BIND_AUTO_CREATE)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun showTime(v: View?) {
        findViewById<TextView>(R.id.textView).text =
            myLocalBoundService?.currentTime
    }

    fun sendMsg(v: View?) {
        if (!isRemoteBound) return
        val msg = Message.obtain()
        val bundle = Bundle()
        bundle.putString("MyString", "Message Received")
        msg.data = bundle
        try {
            myMessenger?.send(msg)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    fun unBindRemoteService(v: View?) {
        if (isRemoteBound)
            remoteConn?.let { unbindService(it) }
        isRemoteBound = false
    }

    fun unBindLocalService(v: View?) {
        if (isLocalBound) localConn?.let { unbindService(it) }
        isLocalBound = false
    }

    companion object {
        private const val TAG = "BoundServiceActivity"
    }
}