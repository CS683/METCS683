package edu.bu.metcs.projectportal

import android.content.Context
import android.content.Intent
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
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import edu.bu.metcs.projectportal.data.prefs.UserPreferences
import edu.bu.metcs.projectportal.data.prefs.UserPreferencesRepository
import edu.bu.metcs.projectportal.data.prefs.loadAccessTime
import edu.bu.metcs.projectportal.data.prefs.saveAccessTime
import edu.bu.metcs.projectportal.ui.theme.ProjectPortalTheme
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // create a datastore
 //   private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Intent(this, LoginActivity::class.java)
            ProjectPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    intent = Intent(this, LoginActivity::class.java)
                    Column() {
//                      AccessTimeText(context = LocalContext.current)
//                      AccessTimeText(UserPreferencesRepository.getInstance(dataStore, LocalContext.current))
                        AccessTimeText()
                        NavGraph({startActivity(intent)}

                        )

                    }
                }
            }
        }
//        // read and write a shared preferences and dispaly it in a toast message
//        val accessTime =  loadAccessTime(this)
//        Toast.makeText(this, accessTime, Toast.LENGTH_LONG).show()
//        saveAccessTime(this)
    }
}

//This composable gets data directly from shared preferences
@Composable
fun AccessTimeText(context:Context){
    val accessTime =  loadAccessTime(context)
    Text (accessTime)
    saveAccessTime(context)
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