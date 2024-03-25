package edu.bu.metcs.projectportal.projects

import androidx.lifecycle.ViewModel
import edu.bu.metcs.projectportal.data.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class ProjectsUiState(
    val allProjects: List<Project> = emptyList(),
    val loading: Boolean = false
)
class ProjectsViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<ProjectsUiState>
            = MutableStateFlow(ProjectsUiState())

    val uiState: StateFlow<ProjectsUiState>
        get() = _uiState

    init{
        //_uiState.value = ProjectsUiState(Project.projects)
        _uiState.update {
            it.copy(allProjects = Project.projects.toList())
        }
    }

    fun deleteProj(projId:String){

        // to do: will be changed later to use a real data storage layer
        val project: Project? = Project.projects.find{it.id == projId}
        Project.projects.remove(project)

        _uiState.update {
            it.copy(allProjects = Project.projects.toList())
        }
    }

}