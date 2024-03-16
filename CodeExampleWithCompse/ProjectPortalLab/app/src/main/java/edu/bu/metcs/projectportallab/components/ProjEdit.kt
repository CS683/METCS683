package edu.bu.metcs.projectportallab.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.bu.metcs.projectportallab.Project
import edu.bu.metcs.projectportallab.ui.theme.ProjectPortalLabTheme

@Composable
fun ProjEdit(){
    var projTitle by remember {mutableStateOf(Project.project.title)}
    var projDesp by remember{ mutableStateOf(Project.project.description)}
    var canEdit by remember{mutableStateOf(false)}


    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
    ){
        Button(
            onClick = {
                canEdit = !canEdit
                if (!canEdit) {
                    Project.project.title = projTitle
                    Project.project.description = projDesp
                }
            },
            modifier = Modifier.align(Alignment.End)
        ){
            Text(if(canEdit)  "summit" else "edit",)
        }

        BasicTextField(
            value = projTitle,
            onValueChange = {projTitle = it},
            textStyle = TextStyle(color = Color.Blue,
                fontSize = 36.sp),
            modifier = Modifier.fillMaxWidth(),
            enabled = canEdit,
        )

        Divider()
        Spacer(Modifier.fillMaxWidth().size(32.dp))

        BasicTextField (
            value = projDesp,
            onValueChange = {projDesp = it},
            textStyle = TextStyle(
                fontSize = 18.sp),
            enabled = canEdit,
            modifier = Modifier.fillMaxWidth()

        )

    }
}
@Preview(name = "Project")
@Composable
private fun ProjEditPreview() {
    ProjectPortalLabTheme {
        ProjEdit()
    }
}