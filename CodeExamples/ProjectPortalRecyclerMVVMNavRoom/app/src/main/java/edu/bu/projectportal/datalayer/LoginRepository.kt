package edu.bu.projectportal.datalayer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import edu.bu.projectportal.firebaseutil.FirebaseLogin

class LoginRepository (val dataSource: FirebaseLogin) {
    var loggedInUser: MutableLiveData<LoggedInUser> = dataSource.loggedInUser

    fun logout() {
        dataSource.logout()
    }

    fun login(username: String, password: String) {
        // handle login
        dataSource.login(username, password)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun signUp(username: String, password: String) {
        // handle login
        dataSource.signUp(username, password,username.split("@").get(0))
    }
}