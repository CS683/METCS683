package edu.bu.metcs.projectportal

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import edu.bu.metcs.projectportal.prefs.UserPreferences
import edu.bu.metcs.projectportal.prefs.UserPreferencesRepository
import edu.bu.metcs.projectportal.prefs.loadAccessTime
import edu.bu.metcs.projectportal.prefs.loadAccessTimeFromDataStore
import edu.bu.metcs.projectportal.prefs.saveAccessTime
import edu.bu.metcs.projectportal.prefs.saveAccessTimetoDataStore
import edu.bu.metcs.projectportal.ui.theme.ProjectPortalTheme
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var br: MyBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column() {
                      AccessTimeText(context = LocalContext.current)
//                      AccessTimeText(UserPreferencesRepository.getInstance(dataStore, LocalContext.current))
 //                       AccessTimeText()
                        NavGraph()

                    }
                }
            }
        }



        //register a broadcast receiver
        br = MyBroadcastReceiver()
        registerReceiver(br,
            IntentFilter().apply{
                addAction(Intent.ACTION_BATTERY_LOW)
            })


    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }
}

//This composable gets data directly from shared preferences
@Composable
fun AccessTimeText(context:Context){
    val accessTime =  loadAccessTime(context)
    Text ("Last accessed at $accessTime")
    saveAccessTime(context, Date().toString())
}

@Composable
fun AccessTimeTextFromDataStore(context:Context){
    val accessTimeFlow =  loadAccessTimeFromDataStore(context)
    val accessTime = accessTimeFlow.collectAsState(initial = "")
    Text ("Last accessed at $accessTime")
    LaunchedEffect(Unit) {
        saveAccessTimetoDataStore(context, Date().toString())
    }
}

//This composable gets data directly from data store repo
@Composable
fun AccessTimeText(userPrefRepo: UserPreferencesRepository){
    val userPreferencesFlow = userPrefRepo.userPreferencesFlow
    val uiState by userPreferencesFlow.collectAsState(UserPreferences(""))

    Text ("Last accessed at ${uiState.accessTime}")
    LaunchedEffect(Unit){
        userPrefRepo.updateAccessTime(Date().toString())
    }
}

//This composable gets data directly from data store repo and use hilt for datastore construction
@Composable
fun AccessTimeText(userPreViewModel: PrefViewModel = hiltViewModel()){
    val uiState by userPreViewModel.uiState.collectAsState()

    Text ("Last accessed at ${uiState.accessTime}")
    userPreViewModel.updateAccessTime(Date().toString())

}

