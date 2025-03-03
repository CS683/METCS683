package edu.bu.metcs.projectportal.screens.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bu.metcs.projectportal.data.Project
import edu.bu.metcs.projectportal.data.ProjectRepository
import edu.bu.metcs.projectportal.services.API.GithubService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class GithubProjectsViewModel @Inject constructor (
    private val githubService: GithubService
): ViewModel() {

    val _isFavorite = MutableStateFlow(false)
    val uiState: StateFlow<ProjectsUiState>

    // no need now as we will not directly change the stateflow.
    // instead, we will use stateIn() to convert the flow to stateflow
    //  private val _uiState: MutableStateFlow<ProjectsUiState>
    //         = MutableStateFlow(ProjectsUiState())


    init {
        uiState = combine(
            githubService.projectsFlow.map{
                if (it == null) return@map emptyList<Project>()
                it.map { repo ->
                    Project(repo.id.toString(), repo.title, repo.desc?:"")
                }
            },
            _isFavorite
        ) { projects, isFav ->
            ProjectsUiState(allProjects = projects, favorite = isFav)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ProjectsUiState()
        )
    }
}
