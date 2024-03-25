package edu.bu.metcs.projectportal.projaddedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bu.metcs.projectportal.data.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

data class ProjUiState(
    val project: Project = Project("Id", "",""),
  //  val title: String = "",
   // val description: String = ""
)

@HiltViewModel
class ProjViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val projId:String? = savedStateHandle["projId"]

    private val _uiState = MutableStateFlow(ProjUiState())
    val uiState: StateFlow<ProjUiState> = _uiState.asStateFlow()

    //initialize the project title and description as the one selected
    init {
            // get data from the data source

            projId?.let {
                val project = Project.projects.find { it.id == projId }!!
                // initilize the uiState
                _uiState.update {
                    it.copy(project = project)
                }
            }
    }

    fun addProject(title:String, desp: String) {
            // update data source
            val project = Project(UUID.randomUUID().toString(), title, desp)
            Project.projects.add(project)

            // update uiState
            _uiState.update {
                it.copy(project = project)
            }
    }

    fun updateProject(title:String, desp: String) {
        // update data source
        projId?.let {
            val proj = Project.projects.find { it.id == projId }!!
            // initilize the uiState
            proj.title = title
            proj.description = desp

            //update uiState
            _uiState.update { currentState ->
                currentState.copy(project = proj)
            }
        }
    }
}