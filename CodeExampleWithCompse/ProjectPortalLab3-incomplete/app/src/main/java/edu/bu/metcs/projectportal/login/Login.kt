package edu.bu.metcs.projectportal.login

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import edu.bu.metcs.projectportal.R


@Composable
fun LoginScreen(
    openDashboard: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState
    var error by remember { mutableStateOf("") }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.email),
                )
            }
        )
        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::updatePassword,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.pwd),
                )
            }
        )

        Button(onClick = {
            error = ""
            if (uiState.email.isBlank()){
                error += "Email cannot be blank\n"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches())
                error += "Invalid email address\n"

            if (uiState.password.isBlank())
                error += "password cannot be blank"
            if (error.isBlank()) {
                viewModel.signIn()
                openDashboard()
            } },
            modifier = Modifier
                .fillMaxWidth()) {
            Text(text = "Sign in")
        }

        if (error.isNotBlank()) {
            Text(text = error)
        }
    }

}