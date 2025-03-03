package edu.bu.metcs.projectportal.screens.projaddedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bu.metcs.projectportal.ProjectPortalArgs
import edu.bu.metcs.projectportal.data.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProjUiState(
 //   val project: Project = Project("Id", "",""),
    val title: String = "",
    val description: String = ""
)

@HiltViewModel
class ProjViewModel @Inject constructor (
    private val projectRepository: ProjectRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val projId: String? = savedStateHandle[ProjectPortalArgs.PROJECT_ID_ARG]


    private val _uiStateFlow = MutableStateFlow(ProjUiState())
    val uiStateFlow: StateFlow<ProjUiState> = _uiStateFlow.asStateFlow()
    //initialize the project title and description as the one selected
    init {
        projId?.let {
            viewModelScope.launch {
                projectRepository.searchProjectbyId(projId)?.let { proj ->
                    _uiStateFlow.update {
                        it.copy(title = proj.title,
                                description = proj.description)
                    }
                }
            }
        }
    }


    fun addProject(title:String, desp: String) {
        // update data source
        viewModelScope.launch {
            projectRepository.addProject(title, desp)
        }
    }

    fun updateProject(title:String, desp: String) {
        // update data source
        projId?.let {
            viewModelScope.launch {
                projectRepository.editProject(projId, title, desp )
            }
        }
    }

    fun updateTitle(title: String){
        _uiStateFlow.update {
            it.copy(title = title)
        }
    }

    fun updateDesp(desp: String){
        _uiStateFlow.update {
            it.copy(description = desp )
        }
    }
}