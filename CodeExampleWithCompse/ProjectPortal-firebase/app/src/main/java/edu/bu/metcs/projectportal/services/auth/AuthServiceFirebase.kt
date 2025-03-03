package edu.bu.metcs.projectportal.services.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthServiceFirebase @Inject constructor(private val auth: FirebaseAuth) : AuthService {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.uid.orEmpty().let { uid -> User(uid, auth.currentUser?.email.orEmpty()) })
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }


    override suspend fun signUp(email: String, password: String): Unit {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }

}
