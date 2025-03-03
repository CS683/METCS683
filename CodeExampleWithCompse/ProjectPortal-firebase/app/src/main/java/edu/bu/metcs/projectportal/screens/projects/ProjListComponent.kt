package edu.bu.metcs.projectportal.screens.projects

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import edu.bu.metcs.projectportal.R
import edu.bu.metcs.projectportal.data.Project

@Composable
fun ProjList (
    projs: List<Project>,
    onSelectProj: (Project) -> Unit,
    onDeleteProj: (String) -> Unit,
    modifier: Modifier,
    canDelete: Boolean = true,
)  {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = dimensionResource(id = R.dimen.common_padding))
    ){
        Text(text = stringResource(id = R.string.app_header),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.common_padding)),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider(modifier= Modifier)
        LazyColumn{
            itemsIndexed(
                items = projs,
                key = { _, proj -> proj.id }
            ) { index, proj ->
                ProjCard(
                    index,
                    proj = proj,
                    canDelete = canDelete,
                    onClick = onSelectProj,
                    onDelete = onDeleteProj)
            }
        }
    }
}

@Composable
fun ProjCard(
    index: Int,
    proj: Project,
    canDelete: Boolean,
    onClick: (Project) -> Unit,
    onDelete: (String)-> Unit,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.common_padding))
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
            if (canDelete) {
                IconButton(
                    onClick = { onDelete(proj.id) }
                ) {
                    Icon(Icons.Filled.Delete, stringResource(id = R.string.del_project))
                }

            }
        }
    }
}

