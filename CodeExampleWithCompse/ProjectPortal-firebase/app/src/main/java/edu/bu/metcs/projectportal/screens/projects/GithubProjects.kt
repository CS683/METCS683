package edu.bu.metcs.projectportal.screens.projects

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import edu.bu.metcs.projectportal.R
import edu.bu.metcs.projectportal.data.Project
import edu.bu.metcs.projectportal.screens.login.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GithubProjsScreen(
    viewModel: GithubProjectsViewModel = hiltViewModel(),
) {

    // collect value emitted by uiState from the viewModel
    val uiState by viewModel.uiState.collectAsState() // whenever the uiState value changes, the ProjList

        // will recompose.
        ProjList(uiState.allProjects,
            onSelectProj = {},
            onDeleteProj = {},
            modifier = Modifier,
            canDelete =  false)
}




