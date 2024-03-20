package edu.bu.metcs.projectportallab.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import edu.bu.metcs.projectportallab.Project
import edu.bu.metcs.projectportallab.R
import edu.bu.metcs.projectportallab.ui.theme.ProjectPortalLabTheme

@Composable
fun ProjDetail(
    proj: Project,
    onEdit:()->Unit ) {

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.common_padding))
            .fillMaxWidth(),
    ){
        Button(
            onClick = onEdit,
            modifier = Modifier.align(Alignment.End)
        ){
            Text( "edit")
        }

        Text(
            text = proj.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.common_padding)),
        )

        Divider(Modifier.fillMaxWidth().
        padding(dimensionResource(id = R.dimen.common_padding)))

        Text (
            text = proj.description,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "ProjectDetail")
@Composable
private fun ProjDetailPreview() {
    ProjectPortalLabTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ProjDetail(Project("ProjectPortal", "ProjectPortal is ...."), {})
        }
    }
}