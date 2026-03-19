package edu.bu.metcs.projectportallab

import android.icu.number.Scale
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.bu.metcs.projectportallab.components.ProjDetail
import edu.bu.metcs.projectportallab.components.ProjEdit
import edu.bu.metcs.projectportallab.ui.theme.ProjectPortalLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectPortalLabTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) {innerPadding ->
                    NavGraph(modifier = Modifier.padding(paddingValues = innerPadding))
                }
            }
        }
    }
}

@Composable
fun NavGraph(modifier : Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ProjectPortalDests.PROJ_DETAIL_ROUTE) {
        composable(route = ProjectPortalDests.PROJ_DETAIL_ROUTE) {
            ProjDetail(Project.project,
                onEdit = {
                navController.navigate(ProjectPortalDests.PROJ_EDIT_ROUTE)
            },
                modifier = modifier)
        }
        composable(route = ProjectPortalDests.PROJ_EDIT_ROUTE) {
            ProjEdit(Project.project,
                onEditDone = {
                navController.navigate(ProjectPortalDests.PROJ_DETAIL_ROUTE)
            },
                modifier = modifier)
        }
    }
}

object ProjectPortalDests {
    const val PROJ_DETAIL_ROUTE = "ProjectDetail"
    const val PROJ_EDIT_ROUTE = "ProjectEdit"
}






