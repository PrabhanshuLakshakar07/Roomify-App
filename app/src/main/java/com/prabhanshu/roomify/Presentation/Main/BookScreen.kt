@file:OptIn(ExperimentalMaterial3Api::class)

package com.prabhanshu.roomify.Presentation.Main
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prabhanshu.roomify.Viewmodel.BookedViewModel
import com.prabhanshu.roomify.Viewmodel.BookingViewModel

@Composable
fun BookScreen(
    viewModel: BookedViewModel = hiltViewModel()
){
    val bookings = viewModel.bookings
    val isLoading = viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.fetchBookings()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("My Bookings") }) }
    ) { padding ->

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(bookings) { booking ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(text = booking.listingId?.title ?: "Unknown Title", style = MaterialTheme.typography.titleMedium)
                            Text(text = booking.listingId?.location ?: "", style = MaterialTheme.typography.bodySmall)
                            Text(text = "₹${booking.listingId?.price ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Check In: ${booking.checkIn}")
                            Text(text = "Check Out: ${booking.checkOut}")
                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = { viewModel.deleteBooking(booking._id) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Cancel")
                            }
                        }
                    }
                }
            }
        }
    }



}