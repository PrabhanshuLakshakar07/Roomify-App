@file:OptIn(ExperimentalMaterial3Api::class)

package com.prabhanshu.roomify.Presentation.Main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward

import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prabhanshu.roomify.Model.Data.Listing
import com.prabhanshu.roomify.Viewmodel.ListingViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.prabhanshu.roomify.Presentation.Navigation.Route
import com.prabhanshu.roomify.R
import com.prabhanshu.roomify.Viewmodel.BookingViewModel


@Composable
fun HomeScreen(
    viewModel: ListingViewModel= hiltViewModel(), navController: NavHostController

){

    val listings = viewModel.listings
    val isLoading = viewModel.isLoading
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    LaunchedEffect(Unit) {
        viewModel.fetchAll()
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                "Roomify",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF000000),

                fontWeight = FontWeight.ExtraBold
            )}, navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.newroomify),
                        contentDescription = "Roomify Logo",
                        tint = Color.Unspecified, // prevent tint if it's a full-color logo
                        modifier = Modifier
                            .size(92.dp)
                            .padding(start = 12.dp)
                            .clickable {
                                viewModel.fetchAll()
                            }
                    )

                }

            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Route.CreateListingScreen)
            },  containerColor = Color(0xFFFF9800)) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Black
                )
            }
        }
    ) { padding ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.fetchAll()
            },
           // modifier = Modifier.padding(padding)
        ) {

            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(contentPadding = padding) {
                    items(listings) { listing ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable {
                                    navController.navigate("detail/${listing._id}")
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFAFE9FF)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .height(200.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = listing.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = listing.location,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Price section
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "₹${listing.price}",
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                }

                                // Book Button
                                Button(
                                    onClick = {
                                        navController.navigate(Route.BookingScreen(listingId = listing._id))
                                    },
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(

                                                            Color(0xFFFF6B6B), Color(0xFFFF8E53)

                                                )
                                            ),

                                            // Alternative gradient options:
                                            // Orange to Pink
                                            // brush = Brush.horizontalGradient(
                                            //     colors = listOf(Color(0xFFFF6B6B), Color(0xFFFF8E53))
                                            // ),

                                            // Blue to Teal
                                            // brush = Brush.horizontalGradient(
                                            //     colors = listOf(Color(0xFF667EEA), Color(0xFF764BA2))
                                            // ),

                                            // Green gradient
                                            // brush = Brush.horizontalGradient(
                                            //     colors = listOf(Color(0xFF11998E), Color(0xFF38EF7D))
                                            // ),
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(
                                     //   defaultElevation = 4.dp,
                                        pressedElevation = 8.dp
                                    ),
                                    shape = RoundedCornerShape(18.dp),
                                   // contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                                ) {
                                    Text(
                                        text = "Book Now",

                                        color = Color(0xFF000000),
                                        modifier = Modifier,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 20.sp

                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }


}

