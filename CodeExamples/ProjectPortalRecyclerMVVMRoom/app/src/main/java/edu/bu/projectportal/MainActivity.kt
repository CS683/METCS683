package edu.bu.projectportal

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.bu.projectportal.datalayer.Project
import edu.bu.projectportal.fragments.ProjListRecyclerViewFragment
import java.util.*

class MainActivity: AppCompatActivity(), ProjListRecyclerViewFragment.OnProjectClickListener {

    private lateinit var accessTime: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_slidepane)
        accessTime = loadAccessTime()
        Toast.makeText(this, accessTime, Toast.LENGTH_LONG).show()
        saveAccessTime()


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val navController = findViewById<FragmentContainerView>(R.id.detailContainerId)
                ?.findNavController()
            if (navController?.currentDestination?.id == R.id.detailFragment) {
                navController.navigate(R.id.action_detailFragment_to_addFragment)
                findViewById<SlidingPaneLayout>(R.id.slidepane).open()
            }
        }
    }


    // this is only used for sliding pane
    override fun onBackPressed() {
        findViewById<SlidingPaneLayout>(R.id.slidepane).close()
        hideKeyboard()
    }

    // this is only used for sliding pane
    override fun onProjectClick(project: Project) {
        findViewById<SlidingPaneLayout>(R.id.slidepane).open()
    }

    // this is only used for sliding pane
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


    // this is an example of reading data from shared preferences
    fun loadAccessTime(): String {
        // get the last access time from the shared preferences. The file name is accesstime.xml
        return getSharedPreferences(getString(R.string.access_time),
            Context.MODE_PRIVATE).getString(getString(R.string.last_access_time),
            getString(R.string.first_time_access)) ?: ""
    }

    // this is an example of writing data to shared preferences
    fun saveAccessTime() {
        // get current time and write to the shared preferences file
        val curTime: String = Date().toString()
        getSharedPreferences(getString(R.string.access_time), Context.MODE_PRIVATE).edit()
            .putString(getString(R.string.last_access_time), "last access at $curTime").commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

            R.id.setting -> {
                Toast.makeText(this, accessTime, Toast.LENGTH_LONG).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}