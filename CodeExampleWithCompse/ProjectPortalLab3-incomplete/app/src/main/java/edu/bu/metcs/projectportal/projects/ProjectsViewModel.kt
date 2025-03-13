package edu.bu.metcs.projectportal.projects

import androidx.lifecycle.ViewModel
import edu.bu.metcs.projectportal.data.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class ProjectsUiData(
    val projects: List<Project> = emptyList(),
)
class ProjectsViewModel: ViewModel() {
    private val _uiStateFlow: MutableStateFlow<ProjectsUiData>
            = MutableStateFlow(ProjectsUiData())

    val uiStateFlow: StateFlow<ProjectsUiData>
        get() = _uiStateFlow


    init{
        //Not good way: _uiState.value = ProjectsUiState(Project.projects)
        _uiStateFlow.update {
            it.copy(projects = Project.projects.toList())
        }
    }

    fun deleteProj(projId:String){

        // to do: will be changed later to use a real data storage layer
        val project: Project? = Project.projects.find{it.id == projId}
        Project.projects.remove(project)

        _uiStateFlow.update {
            it.copy(projects = Project.projects.toList())
        }
    }

}