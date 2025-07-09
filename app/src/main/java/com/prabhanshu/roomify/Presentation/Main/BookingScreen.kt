@file:OptIn(ExperimentalMaterial3Api::class)

package com.prabhanshu.roomify.Presentation.Main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.prabhanshu.roomify.Model.Local.PreferencesManager
import com.prabhanshu.roomify.Viewmodel.BookingViewModel
import kotlinx.coroutines.delay
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale



@Composable
fun BookingScreen(
    listingId: String,
    navController: NavHostController,
    bookingViewModel: BookingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var checkIn by remember { mutableStateOf("") }
    var checkOut by remember { mutableStateOf("") }
    var showCheckInDialog by remember { mutableStateOf(false) }
    var showCheckOutDialog by remember { mutableStateOf(false) }
    var showSuccessAnimation by remember { mutableStateOf(false) }

    val preferencesManager = remember { PreferencesManager(context) }

    // Date formatters
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

   // val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    //val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

  //  val checkInDate = dateFormatter.parse(checkIn)
  //  val checkOutDate = dateFormatter.parse(checkOut)

  val calendar = Calendar.getInstance()

    // Calculate nights - moved outside try-catch

    val nights = remember(checkIn, checkOut) {
        if (checkIn.isNotEmpty() && checkOut.isNotEmpty()) {
            val checkInDate = dateFormatter.parse(checkIn)
            val checkOutDate = dateFormatter.parse(checkOut)
            if (checkInDate != null && checkOutDate != null) {
                val diffInMillis = checkOutDate.time - checkInDate.time
                (diffInMillis / (1000 * 60 * 60 * 24)).toInt()
            } else 0
        } else 0
    }

    // Check-in Date Picker Dialog

    if (showCheckInDialog) {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                checkIn = dateFormatter.format(calendar.time)
                showCheckInDialog = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis()
            show()
        }
    }

    // Check-out Date Picker Dialog

    if (showCheckOutDialog) {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                checkOut = dateFormatter.format(calendar.time)
                showCheckOutDialog = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            // Set minimum date
            val minDate = if (checkIn.isNotEmpty()) {
                val checkInDate = dateFormatter.parse(checkIn)
                if (checkInDate != null) {
                    val nextDay = Calendar.getInstance()
                    nextDay.time = checkInDate
                    nextDay.add(Calendar.DAY_OF_MONTH, 1)
                    nextDay.timeInMillis
                } else {
                    System.currentTimeMillis()
                }
            } else {
                System.currentTimeMillis()
            }
            datePicker.minDate = minDate
            show()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f)
        ) {
            Text(
                text = "Book Your Stay",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                ),
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = "Listing ID: $listingId",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )

        // Check-in Date Field
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 2.dp
        ) {
            OutlinedTextField(
                value = checkIn,
                onValueChange = {},
                label = { Text("Check-in Date") },
                placeholder = { Text("Select check-in date") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendar",
                        tint = MaterialTheme.colors.primary
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { showCheckInDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Select Date"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCheckInDialog = true }
                    .padding(8.dp),
                readOnly = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
                )
            )
        }

        // Check-out Date Field
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 2.dp
        ) {
            OutlinedTextField(
                value = checkOut,
                onValueChange = {},
                label = { Text("Check-out Date") },
                placeholder = { Text("Select check-out date") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendar",
                        tint = MaterialTheme.colors.primary
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { showCheckOutDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Select Date"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCheckOutDialog = true }
                    .padding(8.dp),
                readOnly = true,
                enabled = checkIn.isNotEmpty(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                    disabledBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
                )
            )
        }

        // Booking Summary
        if (checkIn.isNotEmpty() && checkOut.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 2.dp,
                backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Booking Summary",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Check-in:", style = MaterialTheme.typography.body2)
                        Text(checkIn, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Medium)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Check-out:", style = MaterialTheme.typography.body2)
                        Text(checkOut, style = MaterialTheme.typography.body2, fontWeight = FontWeight.Medium)
                    }

                    // Display nights if calculation is valid
                    if (nights > 0) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Nights:", style = MaterialTheme.typography.body2)
                            Text("$nights nights", style = MaterialTheme.typography.body2, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
        ////animation

        Spacer(modifier = Modifier.weight(1f))

        // Confirm Booking Button
        Button(
            onClick = {
                if (checkIn.isNotEmpty() && checkOut.isNotEmpty()) {
                    try {
                        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                        val checkInDate = dateFormatter.parse(checkIn)
                        val checkOutDate = dateFormatter.parse(checkOut)

                        if (checkInDate != null && checkOutDate != null) {
                            // Optional: Ensure check-out is after check-in
                            if (checkOutDate.before(checkInDate)) {
                                Toast.makeText(context, "Check-out must be after check-in.", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            val checkInFormatted = outputFormat.format(checkInDate)
                            val checkOutFormatted = outputFormat.format(checkOutDate)

                            val token = preferencesManager.getAuthToken()
                            bookingViewModel.createBooking(
                                token = token,
                                listingId = listingId,
                                checkIn = checkInFormatted,
                                checkOut = checkOutFormatted
                            )
                            showSuccessAnimation = true

                        } else {
                            Toast.makeText(context, "Please enter valid dates.", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: ParseException) {
                        Toast.makeText(context, "Invalid date format. Please use dd/MM/yyyy.", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(context, "Please select both check-in and check-out dates.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = checkIn.isNotEmpty() && checkOut.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                disabledBackgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    text = "Confirm Booking",
                    style = MaterialTheme.typography.button,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    ////////animation

    if (showSuccessAnimation) {
        val composition by rememberLottieComposition(LottieCompositionSpec.Asset("done.json"))

        Dialog(onDismissRequest = { showSuccessAnimation = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize().fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = composition,
                    iterations = 1,
                    speed = 1.5f,
                    modifier = Modifier.size(300.dp).fillMaxWidth()
                )

                LaunchedEffect(Unit) {
                    delay(3000) // show for 4 seconds
                    showSuccessAnimation = false
                    navController.popBackStack()
                    Toast.makeText(context, "Booking confirmed!", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
















}