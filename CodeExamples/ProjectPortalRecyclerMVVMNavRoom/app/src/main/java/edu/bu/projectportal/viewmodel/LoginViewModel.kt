package edu.bu.projectportal.viewmodel

import android.app.Application
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.bu.projectportal.ProjectPortalApplication
import edu.bu.projectportal.datalayer.LoggedInUser
import edu.bu.projectportal.datalayer.LoginRepository

class LoginViewModel(application: Application)
    :AndroidViewModel(application) {
    private val loginRepository: LoginRepository =
        (application as ProjectPortalApplication).loginRepository

    val loggedInUser: LiveData<LoggedInUser>
        get(){
            return loginRepository.loggedInUser
        }

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        loginRepository.login(username, password)
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun signUp(username: String, password: String) {
        // can be launched in a separate asynchronous job
        loginRepository.signUp(username, password)
    }


    fun logout() {
        // can be launched in a separate asynchronous job
        loginRepository.logout()
    }
    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 8
    }

}
