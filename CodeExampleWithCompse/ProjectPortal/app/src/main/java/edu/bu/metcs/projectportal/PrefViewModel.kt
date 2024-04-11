package edu.bu.metcs.projectportal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bu.metcs.projectportal.prefs.UserPreferences
import edu.bu.metcs.projectportal.prefs.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrefViewModel @Inject constructor (
    private val userPrefRepository: UserPreferencesRepository,
): ViewModel(){

    val uiState: StateFlow<UserPreferences>

    init {
        uiState = userPrefRepository.userPreferencesFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserPreferences("")
        )
    }

    fun updateAccessTime(accessTime: String) {
        viewModelScope.launch {
            userPrefRepository.updateAccessTime(accessTime)
        }
    }
}