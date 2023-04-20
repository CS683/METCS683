package edu.bu.projectportal.datalayer

data class LoginResult(
    val loggedInUser: LoggedInUser? = null,
    val error:Int? = null
)