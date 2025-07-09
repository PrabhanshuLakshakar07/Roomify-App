package com.prabhanshu.roomify.Presentation.Main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.prabhanshu.roomify.Model.Data.CreateListingRequest
import com.prabhanshu.roomify.Viewmodel.ListingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CreateListingScreen(
    viewModel: ListingViewModel,
    navController: NavHostController
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var imageUrls by remember { mutableStateOf("") } // comma-separated
    var amenities by remember { mutableStateOf("") } // comma-separated

    val isLoading = viewModel.isLoading
    val coroutineScope = rememberCoroutineScope()
    val createSuccess = viewModel.createSuccess
    // ✅ Show success dialog with Lottie
    if (createSuccess) {
        AlertDialog(
            onDismissRequest = {
                viewModel.resetCreateSuccess()
                navController.popBackStack()
            },
            title = null,
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("housecreatinganimation.json"))
                    LottieAnimation(
                        composition = composition,
                        iterations = 1,
                        modifier = Modifier.size(150.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Listing Created Successfully!")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        delay(1000) // for animation
                        viewModel.resetCreateSuccess()
                        navController.popBackStack() // ✅ Safe: on Main thread
                    }

                }) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Add New Listing") })
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it.filter { it.isDigit() } },
                label = { Text("Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = imageUrls,
                onValueChange = { imageUrls = it },
                label = { Text("Image URLs (comma separated)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = amenities,
                onValueChange = { amenities = it },
                label = { Text("Amenities (comma separated)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title.isNotBlank() && description.isNotBlank() && price.isNotBlank()) {
                        val req = CreateListingRequest(
                            title = title,
                            description = description,
                            location = location,
                            price = price.toInt(),
                            images = imageUrls.split(",").map { it.trim() },
                            amenities = amenities.split(",").map { it.trim() }
                        )
                        viewModel.createListing(req) {

                           // viewModel.resetCreateSuccess()

                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Listing")
            }
        }
    }
}
