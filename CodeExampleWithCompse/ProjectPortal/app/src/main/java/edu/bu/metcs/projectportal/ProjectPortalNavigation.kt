package edu.bu.metcs.projectportal

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.bu.metcs.projectportal.ProjPortalDests.ADD_EDIT_PROJECT_ROUTE
import edu.bu.metcs.projectportal.ProjPortalDests.PROJECTS_ROUTE
import edu.bu.metcs.projectportal.ProjectPortalArgs.PROJECT_ID_ARG
import edu.bu.metcs.projectportal.ProjectPortalScreens.ADD_EDIT_PROJECT_SCREEN
import edu.bu.metcs.projectportal.ProjectPortalScreens.PROJECTS_SCREEN
import edu.bu.metcs.projectportal.projaddedit.ProjAddEdit
import edu.bu.metcs.projectportal.projects.ProjsScreen

// define all screens, routes and arguments
object ProjectPortalScreens {
    const val PROJECTS_SCREEN = "Projects"
    const val ADD_EDIT_PROJECT_SCREEN = "addEitProject"
//  const val PROJECT_DETAIL_SCREEN = "ProjectDetail"
}

object ProjectPortalArgs {
    const val PROJECT_ID_ARG = "projId"
}
object ProjPortalDests {
    const val PROJECTS_ROUTE = "$PROJECTS_SCREEN"
    const val ADD_EDIT_PROJECT_ROUTE = "$ADD_EDIT_PROJECT_SCREEN/?{$PROJECT_ID_ARG}"
 // const val PROJECT_DETAIL_ROUTE = "$PROJECT_DETAIL_SCREEN/{$PROJECT_ID_ARG}"
}


/*
    Need to have three components: a navHost, a navController, and build a navGraph
    To build a navGraph, we need to list all composables and specify their navigation
    We can pass the navigation functions to the next level composables through composable
    function arguments
 */

@Composable
fun NavGraph(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = PROJECTS_ROUTE) {
        composable(route = PROJECTS_ROUTE) {
            ProjsScreen(
                onAddProj = {
                    navController.navigate("$ADD_EDIT_PROJECT_SCREEN/")
                },
                onSelectProj = {
                    navController.navigate("$ADD_EDIT_PROJECT_SCREEN/?${it.id}")
                },
            )
        }
        composable(
            route = ADD_EDIT_PROJECT_ROUTE,
            arguments = listOf(
                navArgument(PROJECT_ID_ARG) {
                    type = NavType.StringType
                    nullable = true
                }),
        ) { entry ->
            val curId = entry.arguments?.getString(PROJECT_ID_ARG)
            if (curId == null) {
                ProjAddEdit(true,
                    onAdd = {
                        navController.navigate(PROJECTS_ROUTE){
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                            launchSingleTop = true
                        }
                    })
            }
            else
                ProjAddEdit(false, {})
        }
    }
}

