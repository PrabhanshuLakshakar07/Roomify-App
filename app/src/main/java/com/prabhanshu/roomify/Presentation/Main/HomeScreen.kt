@file:OptIn(ExperimentalMaterial3Api::class)

package com.prabhanshu.roomify.Presentation.Main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prabhanshu.roomify.Components.ListingsScreen
import com.prabhanshu.roomify.Components.RoundedSearchBar
import com.prabhanshu.roomify.Presentation.Navigation.Route

@Composable
fun HomeScreen(
    navController: NavController,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToCreate: () -> Unit
){
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("All") }

    val filterOptions = listOf("All", "Recent", "Favorites")
    Scaffold(
        topBar = {
            // 🔷 Logo inside the Scaffold (TopAppBar)
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Roomify",
                            color = Color.Red,
                            modifier = Modifier,
                            fontFamily = FontFamily.Cursive,
                            fontSize = 40.sp, fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge,

                            )
                    }

                },


            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Route.CreateListingScreen)
                },
                containerColor = Color.Red,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Listing")
            }
        }
    ) { innerPadding ->
        // 🔍 Search and Filter inside the Scaffold body
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Search bar
            RoundedSearchBar(
                searchQuery = searchQuery,
                onQueryChange = { searchQuery = it }
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Filter dropdown
            Box {
                Button(onClick = { expanded = true }) {
                    Text(selectedFilter)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    filterOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedFilter = option
                                expanded = false
                            }
                        )
                    }
                }
            }
            ////////////////////////////////////
     ListingsScreen(
         onNavigateToDetail = onNavigateToDetail,
         searchQuery = searchQuery,
         selectedFilter = selectedFilter
     )













        }
    }

}