package edu.bu.metcs.projectportallab.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import edu.bu.metcs.projectportallab.Project
import edu.bu.metcs.projectportallab.R
import edu.bu.metcs.projectportallab.ui.theme.ProjectPortalLabTheme

@Composable
fun ProjEdit(proj: Project,
             onEditDone:()->Unit){
    var projTitle by remember {mutableStateOf(proj.title)}
    var projDesp by remember{ mutableStateOf(proj.description)}

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.common_padding))
            .fillMaxWidth(),
    ){
        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black
        )

        Button(
            onClick = {
                proj.title = projTitle
                proj.description = projDesp
                onEditDone()
            },
            modifier = Modifier.align(Alignment.End)
        ){
            Text("Submit" )
        }

        OutlinedTextField(
            value = projTitle,
            onValueChange = {projTitle = it},
            textStyle = MaterialTheme.typography.titleLarge,
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.common_padding))
        )

        Divider(Modifier.fillMaxWidth().
        padding(dimensionResource(id = R.dimen.common_padding)))

        OutlinedTextField (
            value = projDesp,
            onValueChange = {projDesp = it},
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
       )
    }
}
@Preview(name = "ProjectEdit")
@Composable
private fun ProjEditPreview() {
    ProjectPortalLabTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ProjEdit(Project.project, {})
        }
    }
}