package edu.bu.metcs.projectportal.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bu.metcs.projectportal.data.Project
import edu.bu.metcs.projectportal.data.ProjectRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProjectsUiState(
    val allProjects: List<Project> = emptyList(),
    val favorite: Boolean = false,
    val searchWord: String = ""
)
@OptIn(kotlinx. coroutines. ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProjectsViewModel @Inject constructor (
    private val projectRepository: ProjectRepository
): ViewModel() {
    val uiState: StateFlow<ProjectsUiState>
    val _isFavorite = MutableStateFlow(false)
    val _keyWord = MutableStateFlow("")

    val _searchResult:MutableStateFlow<List<Project>> = MutableStateFlow(emptyList())
    val searchResult:StateFlow<List<Project>>
        get() = _searchResult

    val filteredProjs: StateFlow<List<Project>>

    init {
        // use combine to combine two flows into one
        // it is better to use stateIn to convert the flow to a stateflow to provide to UI
        // this provides lazy flow execution and automatic cancellation
        uiState = combine(
            flow = projectRepository.getProjectsFlow(),
            flow2 = _isFavorite,
            flow3 = _keyWord
        ) { projects, isFav, word ->
            ProjectsUiState(
                allProjects = projects,
                favorite = isFav,
                searchWord = word
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ProjectsUiState()
        )

        // There are two ways to provide search results to the UI
        // One is to perform a filter operation on the returned query for all projects
        // this is preferred if a simple process is needed
        // the other is to query the database with the search word

        // instead of using database query, we apply a simple filter function
        // for the search. When the search word changes, the value of filterProjs
        // state flow will also be updated, and will trig the recomposition of the UI composable
//        filteredProjs = combine(
//            projectRepository.getProjectsFlow(), _keyWord
//        ) { projs, word ->
//            if (word.isEmpty()) projs else
//                projs.filter {
//                    it.title.contains(word)
//                }
//        }.stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000),
//            emptyList()
//        )

        // the other way is to query the database with the search word
        filteredProjs = _keyWord
          //  .debounce(300)
            .flatMapLatest {
                (if (it.isEmpty())
                    projectRepository.getProjectsFlow()
                else
                    projectRepository.searchProjectsFlowByTitle(it))
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
    }



    fun updateSearchWord(word: String){
        _keyWord.update {word}
    }

    fun deleteProj(projId:String){
        viewModelScope.launch {
            projectRepository.deleteProject(projId)
        }
    }

}