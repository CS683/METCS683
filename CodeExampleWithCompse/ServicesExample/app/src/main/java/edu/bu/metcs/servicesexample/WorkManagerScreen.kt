package edu.bu.metcs.servicesexample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.bu.metcs.servicesexample.services.MyWorkerViewModel

@Composable
fun WorkManagerScreen(
    viewModel: MyWorkerViewModel = hiltViewModel(),
    modifier: Modifier,
){
    val work1UiStat by viewModel.work1State.collectAsState()
    val work2UiStat by viewModel.work2State.collectAsState()

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        Divider(modifier = Modifier.fillMaxWidth())

        Text(text = "Work Manager Example",
             fontSize  = 32.sp )

        Button(
            onClick = viewModel::startMyWork,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ){
            Text(text="start workers")
        }
        Text(text= "work1: $work1UiStat",
            modifier = Modifier.fillMaxWidth().padding(16.dp))


        Text(text = "work 2: $work2UiStat",
            modifier = Modifier.fillMaxWidth().padding(16.dp))
    }
}