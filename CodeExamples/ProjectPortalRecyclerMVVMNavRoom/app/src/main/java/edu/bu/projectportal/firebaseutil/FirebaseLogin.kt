package edu.bu.projectportal.firebaseutil

import android.app.Application
import android.content.Context
import android.os.Build
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

import edu.bu.projectportal.LoginActivity

import com.google.firebase.auth.FirebaseUser

import com.google.firebase.auth.AuthResult

import com.google.android.gms.tasks.OnCompleteListener

import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import edu.bu.projectportal.datalayer.LoggedInUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore


class FirebaseLogin (application: Application){
    private var application = application
    private  var mAuth: FirebaseAuth
    var loggedInUser: MutableLiveData<LoggedInUser>

    init {
        FirebaseApp.initializeApp (application.applicationContext);
        mAuth = FirebaseAuth.getInstance();
        loggedInUser = MutableLiveData()
        mAuth.currentUser?.let{
            loggedInUser.setValue(
                LoggedInUser(it.uid,
                    it.email?.split("@")?.get(0)?: "",
                    it.email ?: ""))
        }
    }


    fun login(email: String, passwd: String?) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(passwd)) {
            mAuth.signInWithEmailAndPassword(email, passwd!!).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("signInExisitingUser", "Signed in exisiting user with email: $email")
                    mAuth.currentUser?.let{
                        loggedInUser.setValue(
                        LoggedInUser(it.uid,
                        it.email?.split("@")?.get(0)?: "",
                            it.email ?: ""))
                    }

                } else {
                    loggedInUser.setValue( null)
                    var errorMsg = task.exception?.message?:""
                    Log.d("FirebaseLogin failed: ", errorMsg)
                    Toast.makeText(application.applicationContext,
                        "firebase login failed: " + errorMsg, Toast.LENGTH_LONG).show()

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun signUp(email: String, password: String?, displayName: String?) {
        mAuth.createUserWithEmailAndPassword(email, password!!).addOnCompleteListener(
            application.mainExecutor,
            OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    //Sign in was a success, create intent to go to the LinkRideShareAccountsActivity
                    Log.d("createUserAccount", "Created user with email: $email")
                    mAuth.currentUser?.let{user->
                        updateUserProfile(user, displayName)
                        addUserInfotoFirebase(displayName, email)
                        loggedInUser.setValue(
                            LoggedInUser(user.uid, user.email?.split("@")?.get(0)?: "",
                                user.email ?: ""))}

                } else {
                    var errorMsg = task.exception?.message?:""
                    Log.d("FirebaseSignup failed: ", errorMsg)
                    Toast.makeText(application.applicationContext,
                        "firebase signup failed: " + errorMsg, Toast.LENGTH_LONG).show()

                }
            })
    }

    fun logout(){
        mAuth.signOut()
        loggedInUser.setValue(null)
    }

    fun addUserInfotoFirebase(userName: String?, email: String?) {
        val curUid = mAuth.uid
        val userData: MutableMap<String, String?> = HashMap()
        userData["name"] = userName
        userData["email"] = email
        FirebaseFirestore.getInstance().document("users/$curUid").set(userData)
    }


    fun updateUserProfile(user: FirebaseUser, displayName: String?) {
        //Update the user's profile with their display name entered in the form
        val userProfileChangeRequest =
            UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
        user.updateProfile(userProfileChangeRequest)
    }


}