package edu.bu.metcs.projectportal.projaddedit

import androidx.lifecycle.SavedStateHandle
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

data class ProjUiState(
    val project: Project = Project("Id", "",""),
  //  val title: String = "",
   // val description: String = ""
)

@HiltViewModel
class ProjViewModel @Inject constructor (
    private val projectRepository: ProjectRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val projId: String? = savedStateHandle["projId"]

    private val _uiState = MutableStateFlow(ProjUiState())
    val uiState: StateFlow<ProjUiState> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ProjUiState()
    )

    //initialize the project title and description as the one selected
    init {
        projId?.let {
            viewModelScope.launch {
                 projectRepository.searchProjectbyId(projId)!!.let { proj ->
                     _uiState.update {
                         it.copy(project = proj)
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
        val project = uiState.value.project
        _uiState.update {
            it.copy(project = project.copy(title = title ))
        }
    }

    fun updateDesp(desp: String){
        val project = uiState.value.project
        _uiState.update {
            it.copy(project = project.copy(description = desp ))
        }
    }

}