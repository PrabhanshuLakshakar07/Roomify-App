package com.prabhanshu.roomify.Presentation.Main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prabhanshu.roomify.Model.Data.Profile
import com.prabhanshu.roomify.Model.Network.Request.ProfileRequest
import com.prabhanshu.roomify.Viewmodel.ProfileViewModel




@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val profile = viewModel.profile
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchProfile()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {


        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            viewModel.createProfile(ProfileRequest("John", "john@example.com", "Hey!", true))
        }) {
            Text("Create Profile")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            viewModel.updateProfile(ProfileRequest("John Updated", "john@updated.com", "Updated bio", false))
        }) {
            Text("Update Profile")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            viewModel.deleteProfile()
        }) {
            Text("Delete Profile")
        }

        Spacer(Modifier.height(16.dp))

        profile?.let {
            Text("User: ${it.user}")
            Text("Id: ${it.dob}")
            Text("Bio: ${it.bio}")
            Text("Is Host: ${it.location}")
        }

        if (viewModel.message.isNotEmpty()) {
            Text("✅ ${viewModel.message}", color = Color.Green)
        }

        if (viewModel.error.isNotEmpty()) {
            Text("❌ ${viewModel.error}", color = Color.Red)
        }
    }
}


