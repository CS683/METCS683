@file:OptIn(ExperimentalMaterial3Api::class)

package edu.bu.metcs.projectportal.screens.projects

import androidx.compose.material3.Text
import edu.bu.metcs.projectportal.data.Project
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import edu.bu.metcs.projectportal.R
import edu.bu.metcs.projectportal.screens.login.AuthViewModel
import edu.bu.metcs.projectportal.ui.theme.ProjectPortalTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjsScreen(
    canDelete: Boolean = true,
    onAddProj: () -> Unit,
    onSelectProj: (Project) -> Unit,
    onSignOut: () -> Unit,
    onGithubProj: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProjectsViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text (stringResource(id = R.string.app_name))},
                navigationIcon = {},
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "More")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text("Sign Out") },
                            onClick = {
                                authViewModel.signOut()
                                menuExpanded = false
                                onSignOut()
                            })
                        DropdownMenuItem(
                            text = { Text("GithubProjects") },
                            onClick = {
                                onGithubProj()
                                menuExpanded = false

                            })
                    }
                })
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.common_padding)),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProj) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.add_project))
            }
        }
    ){
        paddingValues ->

        // collect value emitted by uiState from the viewModel
        val uiState by viewModel.uiState.collectAsState() // whenever the uiState value changes, the ProjList


        // will recompose.
        ProjList(uiState.allProjects, onSelectProj,
            onDeleteProj = viewModel::deleteProj,
            modifier = Modifier
                .padding(paddingValues))


    }
}



@Preview(name = "List all Projects")
@Composable
private fun ProjListPreview() {
    ProjectPortalTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            ProjList(
                projs = listOf(
                    Project ("1", "weather forcast", "this app is ..."),
                    Project ("2", "Project Portal", "this app is ..."),

                    ),
                onSelectProj = {},
                onDeleteProj = {},
                modifier = Modifier.fillMaxWidth())
        }
    }
}