package edu.bu.metcs.projectportallab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
        setContent {
            ProjectPortalLabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph()
                }
            }
        }
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ProjectPortalDests.PROJ_DETAIL_ROUTE) {
        composable(route = "ProjectDetail") {
            ProjDetail(Project.project,
                onEdit = {
                navController.navigate(ProjectPortalDests.PROJECT_EDIT_ROUTE)
            })
        }
        composable(route = "ProjectEdit") {
            ProjEdit(Project.project, onEditDone = {
                navController.navigate(ProjectPortalDests.PROJ_DETAIL_ROUTE)
            })
        }
    }
}

object ProjectPortalDests {
    const val PROJ_DETAIL_ROUTE = "ProjectDetail"
    const val PROJECT_EDIT_ROUTE = "ProjectEdit"
}






