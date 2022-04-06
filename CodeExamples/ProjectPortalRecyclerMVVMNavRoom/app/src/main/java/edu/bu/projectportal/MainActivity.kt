package edu.bu.projectportal

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.bu.projectportal.adapter.MyProjListRecyclerViewAdapter
import edu.bu.projectportal.datalayer.Project
import java.util.*

class MainActivity : AppCompatActivity(), MyProjListRecyclerViewAdapter.OnProjectClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_slidepane)

        val accessTime =  loadAccessTime()
        Toast.makeText(this, accessTime, Toast.LENGTH_LONG).show()
        saveAccessTime()


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            val navController = findViewById<FragmentContainerView>(R.id.detailContainerId)
                ?.findNavController()
            if (navController?.currentDestination?.id == R.id.detailFragment) {
                navController.navigate(R.id.action_detailFragment_to_addFragment)
                findViewById<SlidingPaneLayout>(R.id.slidepane).open()
            }
        }
    }



    override fun onBackPressed() {
        findViewById<SlidingPaneLayout>(R.id.slidepane).close()
        hideKeyboard()
    }

    override fun onProjectClick(project: Project){
        findViewById<SlidingPaneLayout>(R.id.slidepane).open()
    }

    private fun hideKeyboard() {
        // Check if no view has focus:
        val view = currentFocus
        if (view != null) {
            val inputManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }


    fun loadAccessTime():String {
        // get the last access time from the shared preferences. The file name is accesstime.xml
        return getSharedPreferences(getString(R.string.access_time),
            Context.MODE_PRIVATE).getString(getString(R.string.last_access_time),
            getString(R.string.first_time_access))?:""
    }
    fun saveAccessTime(){
        // get current time and write to the shared preferences file
        val curTime: String = Date().toString()
        getSharedPreferences(getString(R.string.access_time), Context.MODE_PRIVATE).edit().
        putString(getString(R.string.last_access_time), "last access at $curTime").commit()
    }




}