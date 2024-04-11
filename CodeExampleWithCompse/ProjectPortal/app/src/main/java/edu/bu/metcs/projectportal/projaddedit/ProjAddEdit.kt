package edu.bu.metcs.projectportal.projaddedit


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.bu.metcs.projectportal.R
import edu.bu.metcs.projectportal.data.Project
import edu.bu.metcs.projectportal.ui.theme.ProjectPortalTheme

@Composable
fun ProjAddEdit(
    isAdd: Boolean,
    onAdd: (Project) -> Unit,
    viewModel: ProjViewModel = hiltViewModel(),
){

    val uiState by viewModel.uiState.collectAsState()

    var canEdit by remember{mutableStateOf(isAdd)}


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.common_padding))
    ){
        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black
        )

        Button(
            onClick = {
                if (isAdd) {
                    viewModel.addProject(uiState.project.title, uiState.project.description)
                    onAdd(uiState.project)
                } else {
                    canEdit = !canEdit
                    if (!canEdit) {
                        viewModel.updateProject(uiState.project.title, uiState.project.description)
                    }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ){
            Text(if(canEdit)  "submit" else "edit")
        }

        Row() {
            Image(
                painter = painterResource(id = R.drawable.andropoj),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(50.dp) // Set the image size // Clip to a circular shape
                    .clip(CircleShape)
            )

            OutlinedTextField(
                value = uiState.project.title,
                onValueChange = viewModel::updateTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.common_padding)),
                textStyle = MaterialTheme.typography.titleLarge,
                readOnly = !canEdit,

                placeholder = {
                    Text(
                        text = stringResource(id = R.string.title),
                    )
                },
                maxLines = 1,
                colors = textFieldColors
            )
        }

        HorizontalDivider()

        OutlinedTextField(
            value = uiState.project.description,
            onValueChange = viewModel::updateDesp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.common_padding)),
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    text = stringResource(R.string.description),
                )
            },
            colors = textFieldColors,
            readOnly = !canEdit,
        )
    }
}

@Preview(name = "Add/Edit Project Detail Screen")
@Composable
private fun ProjAddEditPreview() {
    ProjectPortalTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            ProjAddEdit(
                isAdd = true,
                onAdd = {},
            )
        }
    }
}
