@file:OptIn(ExperimentalMaterial3Api::class)

package edu.bu.metcs.projectportal.projects

import androidx.compose.material3.Text
import edu.bu.metcs.projectportal.data.Project
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import edu.bu.metcs.projectportal.R
import edu.bu.metcs.projectportal.ui.theme.ProjectPortalTheme



@Composable
fun ProjsScreen(
    onAddProj: () -> Unit,
    onSelectProj: (Project) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProjectsViewModel = hiltViewModel(),
  //  scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold (
   //    scaffoldState = scaffoldState,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text (stringResource(id = R.string.app_name))},
                navigationIcon = {},
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                }
            ) },
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.common_padding)),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProj) {
                Icon(Icons.Filled.Add, "Add a New project")
            }
        }
    ){
        paddingValues ->

        // collect value emitted by uiState from the viewModel
        val uiState by viewModel.uiState.collectAsState()

        // whenever the uiState value changes, the ProjList
        // will recompose.
        ProjList(uiState.allProjects, onSelectProj,
            onDeleteProj = viewModel::deleteProj,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues))
    }
}


@Composable
fun ProjList (
    projs: List<Project>,
    onSelectProj: (Project) -> Unit,
    onDeleteProj: (String) -> Unit,
    modifier: Modifier
)  {
    Column(modifier = modifier,
    ){
        Text(text = "CS683 Projects",
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.common_padding)),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider(modifier=Modifier.fillMaxWidth())
        LazyColumn(modifier = modifier){
            itemsIndexed(
                items = projs,
                key = { _, proj -> proj.id }
            ) { index, proj ->
                ProjCard(
                    index,
                    proj = proj,
                    onClick = onSelectProj,
                    onDelete = onDeleteProj,
                    modifier = modifier)
            }
        }
    }
}

@Composable
fun ProjCard(
    index: Int,
    proj: Project,
    onClick: (Project) -> Unit,
    onDelete: (String)-> Unit,
    modifier: Modifier,
){
    Card(
        modifier = modifier
            .fillMaxWidth()
           // .padding(dimensionResource(id = R.dimen.common_padding))
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(proj) }
        ) {
            Text(text = (index+1).toString(),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.common_padding)))
            Text(text = proj.title,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.common_padding)))
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
              onClick = {onDelete(proj.id)}
            ) {
              Icon(Icons.Filled.Delete, "Delete the project")
          }
        }
    }
}


@Preview(name = "List all Projects")
@Composable
private fun ProjListPreview() {
    ProjectPortalTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            ProjList(
                projs = Project.projects,
                onSelectProj = {},
                onDeleteProj = {},
                modifier = Modifier.fillMaxWidth())
        }
    }
}