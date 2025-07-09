package com.prabhanshu.roomify

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prabhanshu.roomify.Presentation.State.AuthUiState
import com.prabhanshu.roomify.Viewmodel.AuthViewModel


@Preview
@Composable
fun LoginScreen(


){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
  //  val uiState by viewModel.uiState.observeAsState(AuthUiState())



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF5EF)) // Set your background color here
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.apartmentrent),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterHorizontally).size(300.dp),

        )

        Image(
            painter = painterResource(id = R.drawable.newroomify),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterHorizontally).size(90.dp),

            )

        Text(
            text = "LOGIN ",
            // Set your text color
            fontSize = 24.sp,
            fontWeight = Bold,
            modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally) ,color = Color(0xFFFD715D),
        )

        Spacer(modifier = Modifier.height(5.dp))




        Column(modifier = Modifier.fillMaxWidth()) {


            // Email Input
            TextField(
                value = email,
                onValueChange = {email = it },
                label = { Text("Email", color = Color.Black, fontWeight = Bold) },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFF5DFDA5), RoundedCornerShape(25.dp))

                    .clip(RoundedCornerShape(25.dp))
                    .background(Color(0xFFFD715D))
                    .padding(horizontal = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Input
            TextField(
                value = password,
                onValueChange = {password = it },
                label = { Text("Password", color = Color.Black, fontWeight = Bold) },
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(3.dp, Color(0xFF5DFDA5), RoundedCornerShape(25.dp))
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color(0xFFFD715D))
                    .padding(horizontal = 8.dp), colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )


            Spacer(modifier = Modifier.height(22.dp))

            Button(
                onClick = {  },
                // isLoading = uiState.isLoading,
                enabled = email.isNotBlank() && password.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent// light pink background
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)

                    .padding(top = 4.dp).clip(RoundedCornerShape(28.dp)).background(Color(0xFFFD715D)),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text(
                    "LOGIN NOW",
                    modifier = Modifier,
                    color = Color(0xFF441752),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )

            }
            TextButton(onClick = {  }) {
                Text("Don't have an account? Register")
            }


            // make it lightly visible under text



        }
    }


}