package com.example.todofromscratch

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.todofromscratch.model.domain.User
import com.example.todofromscratch.presenter.AuthenticatePresenter
import com.example.todofromscratch.presenter.LoginPresenter
import com.example.todofromscratch.presenter.RegisterPresenter


@Composable
fun RegisterScreen(
    onRegisterButtonClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
    var username by remember { mutableStateOf("username2") }
    var password by remember { mutableStateOf("password3") }
    var email by remember { mutableStateOf("username@123.com") }
    var firstname by remember { mutableStateOf("user") }
    var lastname by remember { mutableStateOf("name") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    class RegisterView : AuthenticatePresenter.View {
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
            onRegisterButtonClicked()
        }

    }

    val presenter = RegisterPresenter(RegisterView())

    Surface () {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
            TextField(
                value = email,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(15.dp),
                onValueChange = { email = it },
                label = { Text("email") },
                singleLine = true,
                placeholder = { Text("username@123.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),

                )
            TextField(
                value = firstname,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(15.dp),
                onValueChange = { firstname = it },
                label = { Text("firstname") },
                singleLine = true,
                placeholder = { Text("user") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),

                )
            TextField(
                value = lastname,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(15.dp),
                onValueChange = { lastname = it },
                label = { Text("lastname") },
                singleLine = true,
                placeholder = { Text("name") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),

                )
            Button(
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    presenter.registerUser(username, password, email, firstname, lastname)
//                    onLoginButtonClicked()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(ContextCompat.getColor(LocalContext.current, R.color.neutral_green))
                ),
                enabled = username.isNotBlank() && password.isNotBlank() && firstname.isNotBlank()
                        && lastname.isNotBlank() && email.isNotBlank()
            ) {
                Text(text = "Register")
            }
            Spacer(
                modifier = Modifier.
                padding(25.dp)
            )
            Text(
                text = "or click Login Page to login an existing user",
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
                    onLoginClicked()
//                    presenter.login(username, password)
//                    onLoginButtonClicked()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(ContextCompat.getColor(LocalContext.current, R.color.neutral_green))
                ),
//                enabled = username.isNotBlank() && password.isNotBlank()
            ) {
                Text(text = "Login Page")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(onRegisterButtonClicked = {}, onLoginClicked = {})
}