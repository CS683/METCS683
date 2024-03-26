package edu.bu.metcs.projectportal.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bu.metcs.projectportal.data.Project
import edu.bu.metcs.projectportal.data.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProjectsUiState(
    val allProjects: List<Project> = emptyList(),
    val loading: Boolean = false
)

@HiltViewModel
class ProjectsViewModel @Inject constructor (
    private val projectRepository: ProjectRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<ProjectsUiState>
            = MutableStateFlow(ProjectsUiState())

    val uiState: StateFlow<ProjectsUiState> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ProjectsUiState()
    )

    init{

        viewModelScope.launch {
            projectRepository.getProjectsFlow().collect{projs ->
                _uiState.update{
                    it.copy(allProjects = projs)
                }
            }
        }
    }

    fun deleteProj(projId:String){
        viewModelScope.launch {
            projectRepository.deleteProject(projId)
        }
    }

}