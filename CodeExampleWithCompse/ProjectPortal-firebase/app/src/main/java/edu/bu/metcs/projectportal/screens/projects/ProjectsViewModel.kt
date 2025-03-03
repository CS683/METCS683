package edu.bu.metcs.projectportal.screens.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bu.metcs.projectportal.data.Project
import edu.bu.metcs.projectportal.data.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProjectsUiState(
    val allProjects: List<Project> = emptyList(),
    val favorite: Boolean = false
)

@HiltViewModel
class ProjectsViewModel @Inject constructor (
    private val projectRepository: ProjectRepository
): ViewModel() {

    val _isFavorite = MutableStateFlow(false)
    val uiState: StateFlow<ProjectsUiState>

    // no need now as we will not directly change the stateflow.
    // instead, we will use stateIn() to convert the flow to stateflow
    //  private val _uiState: MutableStateFlow<ProjectsUiState>
    //         = MutableStateFlow(ProjectsUiState())


    init{

         uiState  = combine (
            projectRepository.getProjectsFlow(), _isFavorite) {
                projects, isFav ->
            ProjectsUiState(allProjects = projects, favorite = isFav )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ProjectsUiState()
        )

//      // we will not directly change stateflow
//        viewModelScope.launch {
//            projectRepository.getProjectsFlow().collect{projs ->
//                _uiState.update{
//                    it.copy(allProjects = projs)
//                }
//            }
//        }
    }

    fun deleteProj(projId:String){
        viewModelScope.launch {
            projectRepository.deleteProject(projId)
        }
    }

}