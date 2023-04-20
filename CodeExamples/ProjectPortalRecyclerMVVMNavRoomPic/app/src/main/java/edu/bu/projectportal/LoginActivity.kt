package edu.bu.projectportal

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.bu.projectportal.R
import edu.bu.projectportal.databinding.ActivityLoginBinding
import edu.bu.projectportal.datalayer.LoggedInUser
import edu.bu.projectportal.viewmodel.LoginViewModel
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import edu.bu.projectportal.MainActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        if(intent.getBooleanExtra("Logout", false)){
            loginViewModel.logout()
        }

        binding.login.setOnClickListener {
            loginViewModel.login(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        binding.signup.setOnClickListener {
            loginViewModel.signUp(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }
        loginViewModel.loggedInUser.observe(this, Observer {
            it?.let{
                updateUiWithUser(it)
                finish()
            }
            //Complete and destroy login activity once successful

        })

    }


    private fun updateUiWithUser(loggedInUser: LoggedInUser) {
        val welcome = getString(R.string.welcome)
        val displayName = loggedInUser.displayName

        val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(mainIntent)

        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }


    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

}