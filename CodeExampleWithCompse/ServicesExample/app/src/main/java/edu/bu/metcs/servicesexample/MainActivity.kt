package edu.bu.metcs.servicesexample

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import edu.bu.metcs.servicesexample.services.MyBgServiceST
import edu.bu.metcs.servicesexample.services.MyBgServiceDT
import edu.bu.metcs.servicesexample.services.MyFgService
import edu.bu.metcs.servicesexample.services.MyJobService
import edu.bu.metcs.servicesexample.ui.theme.ServicesExampleTheme
import kotlinx.coroutines.Job
import android.Manifest


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ServicesExampleTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Service Examples")
                },
                navigationIcon = {},
                actions = {
                    ServiceMenu(LocalContext.current)
                }
            )
        }
    ) { paddingValue ->
        WorkManagerScreen(
            viewModel = hiltViewModel(),
            modifier = Modifier.padding(paddingValue)
        )

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ServiceMenu(context: Context) {

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted ->
        if (isGranted) {
            context.startService(
                Intent(MyFgService.ACTION.STARTFOREGROUND_ACTION,
                    null, context,
                    MyFgService::class.java))
        } else {

        }
    }


    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Memu"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize(Alignment.TopEnd)
        ) {
            DropdownMenuItem(
                text = { Text("Background service in the same thread") },
                onClick = {
                    expanded = false
                    context.startService(Intent(context, MyBgServiceST::class.java)) }
            )
            DropdownMenuItem(
                text = { Text("Background service in different threads") },
                onClick = {
                    expanded = false
                    Log.d("main","start a service")
                    context.startService(Intent(context, MyBgServiceDT::class.java)) }
            )

            DropdownMenuItem(
                text = { Text("Foreground service") },
                onClick = {
                    expanded = false

                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.POST_NOTIFICATIONS)
                        == PackageManager.PERMISSION_GRANTED) {
                    context.startService(
                        Intent(MyFgService.ACTION.STARTFOREGROUND_ACTION,
                        null, context,
                        MyFgService::class.java)) }
                else {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                    }
            )

            DropdownMenuItem(
                text = { Text("Job service") },
                onClick = {
                    expanded = false
                    Log.d("main","start a job service")
                    scheduleJob(context) }
            )

        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun scheduleJob(context:Context) {
    val jobId = (1..10).random()
    val myJobInfo: JobInfo =
        JobInfo.Builder(
            jobId,
            ComponentName(context, MyJobService::class.java)
        )
            .apply {
                setRequiresBatteryNotLow(true)
                setExtras(PersistableBundle().apply{putInt("id",jobId)})
            }.build()
    val scheduler: JobScheduler =
        getSystemService(
            context, JobScheduler::class.java ) as JobScheduler
    scheduler.schedule(myJobInfo)

}




//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    ServicesExampleTheme {
//       MainScreen()
//    }
//}