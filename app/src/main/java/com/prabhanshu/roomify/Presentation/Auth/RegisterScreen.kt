package com.prabhanshu.roomify.Presentation.Auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prabhanshu.roomify.Presentation.State.AuthUiState
import com.prabhanshu.roomify.Viewmodel.AuthViewModel


@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val backgroundColor = Color(0xFF441752)

    // States for input fields
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isHost by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.observeAsState(AuthUiState())
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateToProfile()
            viewModel.resetState()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            // Handle error display
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF5EF)) // Set your background color here
            .padding(16.dp)
    ) {



        Text(
            text = "REGISTRATION",
            // Set your text color
            fontSize = 24.sp,
            fontWeight = Bold,
            modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally) ,color = Color(0xFF441752)
        )

        Spacer(modifier = Modifier.height(5.dp))


        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Name Input
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name", color = Color.White) },
                    textStyle = TextStyle(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(0xFF441752))
                        .padding(horizontal = 8.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Input
                TextField(
                    value = email,
                    onValueChange = {email = it },
                    label = { Text("Email", color = Color.White) },
                    textStyle = TextStyle(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(0xFF441752))
                        .padding(horizontal = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Password Input
                TextField(
                    value = password,
                    onValueChange = {password = it },
                    label = { Text("Password", color = Color.White) },
                    textStyle = TextStyle(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(0xFF441752))
                        .padding(horizontal = 8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password Input
                TextField(
                    value = confirmPassword,
                    onValueChange = {confirmPassword = it },
                    label = { Text("Confirm Password", color = Color.White) },
                    textStyle = TextStyle(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(0xFF441752))
                        .padding(horizontal = 8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    isError = password != confirmPassword && confirmPassword.isNotEmpty()
                )
                if (password != confirmPassword && confirmPassword.isNotEmpty()) {
                    Text(
                        text = "Passwords don't match",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                uiState.errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isHost,
                        onCheckedChange = { isHost = it }
                    )
                    Text(
                        text = "Register as Host",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))
                Button(
                    onClick = {   if (password == confirmPassword) {
                        Log.d("REGISTER_DEBUG", "Registering with isHost: $isHost")
                        viewModel.register(name, email, password, isHost)
                    } },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFDCDC) // light pink background
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(top = 4.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 2.dp,
                        disabledElevation = 0.dp
                    ),
                    //    isLoading = uiState.isLoading,
                    enabled = name.isNotBlank() && email.isNotBlank() &&
                            password.isNotBlank() && password == confirmPassword,
                ) {
                    Text(
                        "SIGN UP",
                        modifier = Modifier,
                        color = Color(0xFF441752),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )

                }
                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onNavigateToLogin) {
                    Text("Already have an account? Login")
                }



            }
        }
    }



}