package edu.bu.metcs.projectportal.screens.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bu.metcs.projectportal.services.auth.AuthService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserLoginUiData(
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = ""
)

sealed class AuthState {
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Authenticated(val userId: String ) : AuthState()
    data class Error(val message: String) : AuthState()
}
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: AuthService,
    ): ViewModel() {

    private val _uiState = mutableStateOf(UserLoginUiData())
    val uiState: State<UserLoginUiData> = _uiState

    private val _authState = if (authService.hasUser)
        MutableStateFlow<AuthState>(AuthState.Authenticated(authService.currentUserId))
        else
            MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    private val passwordConfirmation
        get() = uiState.value.passwordConfirmation

    fun updateEmail(email: String){
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String){
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun updatePasswordConfirmation(passwordConfirm: String){
        _uiState.value = _uiState.value.copy(passwordConfirmation = passwordConfirm)
    }

    fun signIn(openDashboard: () -> Unit){

        viewModelScope.launch (
            CoroutineExceptionHandler {_, throwable->
                Log.d("TAG", "signIn: ${throwable.message}")// handle throwable
                _authState.value = AuthState.Error(throwable.message ?: "Unknown error")
            }) {
            _authState.value = AuthState.Loading
            authService.signIn(email, password)
            _authState.value = AuthState.Authenticated(authService.currentUserId)
            openDashboard()
        }
    }

    fun signUp(){
        viewModelScope.launch (
            CoroutineExceptionHandler {_, throwable->
                Log.d("TAG", "signUp: ${throwable.message}")// handle throwable
            }){
            authService.signUp(email, password)
        }
    }
    fun signOut() {
        viewModelScope.launch {
            authService.signOut()
        }
    }



}