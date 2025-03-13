package edu.bu.metcs.projectportal.login

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import edu.bu.metcs.projectportal.ProjectPortalArgs
import edu.bu.metcs.projectportal.projaddedit.ProjUiData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class UserLoginUiData(
    val email: String = "",
    val password: String = ""
)

class LoginViewModel: ViewModel(){
    private val _uiState = mutableStateOf(UserLoginUiData())
    val uiState: State<UserLoginUiData> = _uiState
    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun updateEmail(email: String){
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String){
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun signIn(): Boolean{
        if (email.isBlank()){
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false
        return auth(email,password)
    }

    fun auth(email: String, password: String): Boolean{
        return true
    }
}