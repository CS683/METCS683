package edu.bu.metcs.projectportal.screens.projaddedit

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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import edu.bu.metcs.projectportal.R

@Composable
fun ProjAddEdit(
    isAdd: Boolean,
    onAdd: () -> Unit,
    viewModel: ProjViewModel = hiltViewModel(),
){

    val uiState by viewModel.uiStateFlow.collectAsState()

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
                    viewModel.addProject(uiState.title, uiState.description)
                    onAdd()
                } else {
                    canEdit = !canEdit
                    if (!canEdit) {
                        viewModel.updateProject(uiState.title, uiState.description)
                    }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ){
            Text(if(canEdit)  "submit" else "edit")
        }
        OutlinedTextField(
            value = uiState.title,
            onValueChange = viewModel::updateTitle,
            modifier = Modifier.fillMaxWidth()
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

        HorizontalDivider()

        OutlinedTextField(
            value = uiState.description,
            onValueChange = viewModel::updateDesp,
            modifier = Modifier.fillMaxWidth()
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