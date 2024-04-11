package edu.bu.metcs.projectportal.projects

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
    val favorite: Boolean = false,
    val searchWord: String = ""
)

@HiltViewModel
class ProjectsViewModel @Inject constructor (
    private val projectRepository: ProjectRepository
): ViewModel() {
    val uiState: StateFlow<ProjectsUiState>
    val _isFavorite = MutableStateFlow(false)

    val keyWord = MutableStateFlow("")

    //user List<Project> directly
    val kotlinProjs: StateFlow<List<Project>>

    val _searchResult:MutableStateFlow<List<Project>> = MutableStateFlow(emptyList())
    val searchResult:StateFlow<List<Project>>
        get() = _searchResult

    val filteredProjs: StateFlow<List<Project>>

    init{
        // use combine to combine two flows into one
        // it is better to use stateIn to convert the flow to a stateflow to provide to UI
        // this provides lazy flow execution and automatic cancellation
        uiState  = combine (
            flow = projectRepository.getProjectsFlow(), flow2 = _isFavorite
        ) {
                projects, isFav ->
            ProjectsUiState(allProjects = projects, favorite = isFav )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ProjectsUiState()
        )

        // if we just need the List<Project>, no need to wrap to ProjectsUiState

        // Here we perform a query to find all projects with the title containing "kotlin"
        // Since this is called in the init block. We can only call the query method
        // with a known parameter
        kotlinProjs = projectRepository.searchProjectsFlowByTitle("kotlin").stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        // There are two ways to provide search results to the UI
        // One is to perform a filter operation on the returned query for all projects
        // this is preferred if a simple process is needed
        // the other is to query the database with the search word

        // instead of using database query, we apply a simple filter function
        // for the search. When the search word changes, the value of filterProjs
        // state flow will also be updated, and will trig the recomposition of the UI composable
        filteredProjs = combine(
            projectRepository.getProjectsFlow(),keyWord
        ) {
          projs, word ->
            projs.filter{
                it.title.contains(word)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    }


    // This method is called to provide UI the search result when the user performs a search
    // Since this method will be called many time, it should not call the dao method that
    // returns a flow. Otherwise, every search will return a flow and UI may need to observe
    // too many flow state, which may cause too many unnecessary re-composition
    // Instead, we manually update the searchResult stateflow every time the user needs a new search
    // This state value change will be observed by the UI composable.
    fun updateSearchResult(projTitle:String){
        viewModelScope.launch {
            _searchResult.value = projectRepository.searchProjectbyTitle(projTitle)
        }
    }

    fun updateSearchWord(word: String){
        keyWord.value = word
    }

    fun deleteProj(projId:String){
        viewModelScope.launch {
            projectRepository.deleteProject(projId)
        }
    }

}