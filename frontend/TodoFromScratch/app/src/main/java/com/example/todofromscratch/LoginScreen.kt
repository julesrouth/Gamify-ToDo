package com.example.todofromscratch

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todofromscratch.model.domain.User
import com.example.todofromscratch.presenter.AuthenticatePresenter
import com.example.todofromscratch.presenter.LoginPresenter;
import com.example.todofromscratch.ui.theme.TodoFromScratchTheme;

@Composable
fun LoginScreen(
    onLoginButtonClicked: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    var username by remember { mutableStateOf("joe1") }
    var password by remember { mutableStateOf("stuff") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    class LoginView : AuthenticatePresenter.View {
        override fun showErrorMessage(message: String?) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }

        override fun hideErrorMessage() {
        }

        override fun hideInfoMessage() {
        }

        override fun showInfoMessage(message: String?) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun openMainView(user: User?) {
            Toast.makeText(
                context,
                "opening main view",
                Toast.LENGTH_SHORT
            ).show()
            onLoginButtonClicked()
        }

    }

    val presenter = LoginPresenter(LoginView())

    Surface () {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Text(
//                text = "Login",
//                modifier = Modifier
////                    .fillMaxWidth()
//                    .align(Alignment.CenterHorizontally)
//////                    .size(20.dp)
////                    .padding(15.dp)
//                    .size(20.dp)
//            )
            Spacer(
                modifier = Modifier.
                    padding(25.dp)
            )
            TextField(
                value = username,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(15.dp),
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                placeholder = { Text("Username") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),

            )
            TextField(
                value = password,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(15.dp),
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                placeholder = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Button(
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    presenter.login(username, password)
//                    onLoginButtonClicked()
                },
                enabled = username.isNotBlank() && password.isNotBlank()
            ) {
                Text(text = "Login")
            }
            Spacer(
                modifier = Modifier.
                padding(25.dp)
            )
            Text(
                text = "or click Register Page to register a new user",
                modifier = Modifier.padding(8.dp)
            )
            Button(
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
//                    actions = {
//                        RegisterButton(onClick = onRegisterButtonClicked)
//                    }
                    onRegisterClicked()
//                    presenter.login(username, password)
//                    onLoginButtonClicked()
                },
//                enabled = username.isNotBlank() && password.isNotBlank()
            ) {
                Text(text = "Register Page")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun LoginScreenPreview() {
    TodoFromScratchTheme {
        LoginScreen(onLoginButtonClicked = {}, onRegisterClicked = {})
    }
}