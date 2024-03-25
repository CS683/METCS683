package edu.bu.metcs.projectportal.projaddedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import edu.bu.metcs.projectportal.data.Project
import edu.bu.metcs.projectportal.R

@Composable
fun ProjAddEdit(
    isAdd: Boolean,
    onAdd: (Project) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProjViewModel = hiltViewModel(),
){

    val uiState by viewModel.uiState.collectAsState()

    val proj = uiState.project
    var projTitle by remember {mutableStateOf(proj.title)}
    var projDesp by remember{ mutableStateOf(proj.description)}
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
                    viewModel.addProject(projTitle, projDesp)
                    onAdd(proj)
                } else {
                    canEdit = !canEdit
                    if (!canEdit) {
                        viewModel.updateProject(projTitle, projDesp)
                    }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ){
            Text(if(canEdit)  "submit" else "edit")
        }
        OutlinedTextField(
            value = projTitle,
            onValueChange = {projTitle = it},
            modifier = Modifier.fillMaxWidth()
                .padding(dimensionResource(R.dimen.common_padding)),
            textStyle = MaterialTheme.typography.titleLarge,
            readOnly = !canEdit,

            placeholder = {
                Text(
                    text = "title",
                )
            },
            maxLines = 1,
            colors = textFieldColors
        )

        HorizontalDivider()

        OutlinedTextField(
            value = projDesp,
            onValueChange = {projDesp = it},
            modifier = Modifier.fillMaxWidth()
                .padding(dimensionResource(R.dimen.common_padding)),
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    text = "description",
                )
            },
            colors = textFieldColors,
            readOnly = !canEdit,
        )
    }
}