package edu.bu.metcs.projectportal

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import edu.bu.metcs.projectportal.ui.theme.ProjectPortalTheme
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavGraph()
                }
            }
        }
        val accessTime =  loadAccessTime()
        Toast.makeText(this, accessTime, Toast.LENGTH_LONG).show()
        saveAccessTime()
    }

    fun loadAccessTime():String {
        // get the last access time from the shared preferences. The file name is accesstime.xml
        return getSharedPreferences(getString(R.string.access_time),
            Context.MODE_PRIVATE).getString(getString(R.string.last_access_time),
            getString(R.string.first_time_access))?:""
    }
    // this is an example of writing data to shared preferences
    fun saveAccessTime(){
        // get current time and write to the shared preferences file
        val curTime: String = Date().toString()
        getSharedPreferences(getString(R.string.access_time), Context.MODE_PRIVATE).edit().
        putString(getString(R.string.last_access_time), "last access at $curTime").apply()
    }
}
