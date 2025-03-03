package edu.bu.metcs.projectportal.screens.login

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import edu.bu.metcs.projectportal.R


//@Composable
//fun InitialScreen(
//    openDashboard: () -> Unit,
//    viewModel: AuthViewModel = hiltViewModel()
//){
//    val authState by viewModel.authState.collectAsState()
//    when (authState) {
//        is AuthState.Unauthenticated -> {
//            AuthScreen(openDashboard, viewModel)
//        }
//        is AuthState.Loading -> {
//            // Show a loading indicator
//        }
//        is AuthState.Authenticated -> {
//            openDashboard()
//        }
//        is AuthState.Error -> {
//            // Show an error message
//            val errorMessage = (authState as AuthState.Error).message
//            Text(text = errorMessage)
//        }
//    }
//}



@Composable
fun AuthScreen(
    openDashboard: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    var error by remember { mutableStateOf("") }
    var isSignIn by remember { mutableStateOf(true) }
    val authState by viewModel.authState.collectAsState()

    if (authState is AuthState.Authenticated) {
        openDashboard()
        return
    }else if (authState is AuthState.Error) {
        error = (authState as AuthState.Error).message

    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.common_padding)),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.email),
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::updatePassword,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.common_padding)),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.pwd),
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
            )

        if (!isSignIn) { // Show confirm password field only for sign-up
            OutlinedTextField(
                value = uiState.passwordConfirmation,
                onValueChange = viewModel::updatePasswordConfirmation,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.common_padding)),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.pwdConfirm),
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                error = ""
                if (uiState.email.isBlank()) {
                    error += "Email cannot be blank\n"
                } else if (!Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches())
                    error += "Invalid email address\n"

                if (uiState.password.isBlank())
                    error += "password cannot be blank"

                if (error.isBlank()) {
                    if (isSignIn)
                        viewModel.signIn(openDashboard)
                    else {
                        if (uiState.password != uiState.passwordConfirmation)
                            error = "Passwords do not match"
                        else {
                            viewModel.signUp()
                            isSignIn = true
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.common_padding))
        ) {
            Text(text = if (isSignIn) "Sign in" else "Sign up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isSignIn) {
            Button(onClick = {
                isSignIn = false
            }) {
                Text(text = "Need to Sign Up an account?")
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        if (error.isNotBlank()) {
            Text(text = error)
        }
    }

}

