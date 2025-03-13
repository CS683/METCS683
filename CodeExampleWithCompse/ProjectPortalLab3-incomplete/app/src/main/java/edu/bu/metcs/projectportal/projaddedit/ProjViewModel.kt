package edu.bu.metcs.projectportal.projaddedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bu.metcs.projectportal.ProjectPortalArgs
import edu.bu.metcs.projectportal.data.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

data class ProjUiData(
    val title: String = "",
    val description: String = ""
)

@HiltViewModel
class ProjViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val projId:String? = savedStateHandle[ProjectPortalArgs.PROJECT_ID_ARG]

    private val _uiStateFlow = MutableStateFlow(ProjUiData())
    val uiStateFlow: StateFlow<ProjUiData> = _uiStateFlow.asStateFlow()

    //initialize the project title and description as the one selected
    init {
            // get data from the data source

            projId?.let {
                val project = Project.projects.find { it.id == projId }!!
                // initilize the uiState
                _uiStateFlow.update {
                    it.copy(title = project.title,
                        description = project.description)
                }
            }
    }
    // implement this to add a new project
    fun addProject(title:String, desp: String) {

    }

    fun updateProject(title:String, desp: String) {
        // update data source
        projId?.let {
            val proj = Project.projects.find { it.id == projId }!!
            // update the project information
            proj.title = title
            proj.description = desp

            //update uiState
            _uiStateFlow.update { currentState ->
                currentState.copy(
                    title = proj.title,
                    description = proj.description)
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