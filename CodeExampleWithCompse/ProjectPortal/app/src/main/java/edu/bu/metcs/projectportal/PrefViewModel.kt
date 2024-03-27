package edu.bu.metcs.projectportal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bu.metcs.projectportal.prefs.UserPreferences
import edu.bu.metcs.projectportal.prefs.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrefViewModel @Inject constructor (
    private val userPrefRepository: UserPreferencesRepository,
): ViewModel(){

    private val _uiState = MutableStateFlow(UserPreferences(""))
    val uiState: StateFlow<UserPreferences> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        UserPreferences("")
    )

    //initialize the project title and description as the one selected
    init {
            viewModelScope.launch {
                userPrefRepository.userPreferencesFlow.collect { userprf ->
                    _uiState.value = userprf
                }
            }
        }

    fun updateAccessTime(accessTime: String) {
        viewModelScope.launch {
            userPrefRepository.updateAccessTime(accessTime)
        }
    }
}