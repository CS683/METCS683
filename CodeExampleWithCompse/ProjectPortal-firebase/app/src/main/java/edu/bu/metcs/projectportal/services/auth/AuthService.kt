package edu.bu.metcs.projectportal.services.auth

import kotlinx.coroutines.flow.Flow

data class User (
    val id: String,
    val email: String
)
interface AuthService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
}