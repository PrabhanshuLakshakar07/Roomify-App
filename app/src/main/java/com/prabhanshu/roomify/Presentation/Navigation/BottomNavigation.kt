package com.prabhanshu.roomify.Presentation.Navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.prabhanshu.roomify.R


data class BottomNavigationItem(
    /*
    val text: String,
    val selectedIcon: @Composable () -> Unit,
    val unselectedIcon: @Composable () -> Unit,*/

    val text: String,
    val selectedIcon: @Composable () -> Unit,
    val unselectedIcon: @Composable () -> Unit
)


val bottomNavigationItems = listOf(



    BottomNavigationItem(
        text = "Home",
        selectedIcon = {
            Image(painter = painterResource(id = R.drawable.room), contentDescription =null )
        },
        unselectedIcon = {
            Image(painter = painterResource(id = R.drawable.roomy), contentDescription =null )
        }
    ),
    BottomNavigationItem(
        text = "Book",
        selectedIcon = {
            Image(painter = painterResource(id = R.drawable.bookingfocus), contentDescription =null )
        },
        unselectedIcon = {
            Image(painter = painterResource(id = R.drawable.booking), contentDescription =null )
        }
    ),
    BottomNavigationItem(
        text = "Profile",
        selectedIcon = {
            Image(painter = painterResource(id = R.drawable.profilefocus), contentDescription =null )
        },
        unselectedIcon = {
            Image(painter = painterResource(id = R.drawable.profile), contentDescription =null )
        }
    ),



    )



/////////////////////////////////////////
@Composable
fun BottomNavigation(
    items: List<BottomNavigationItem>,
    selectedItemIndex: Int,
    onItemClick: (Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        containerColor = Color(0xC1FF4200),
        tonalElevation = 20.dp,
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { onItemClick(index) },
                icon = {
                    Row(
                        modifier = Modifier.size(30.dp),
                        // horizontalAlignment = Alignment.CenterHorizontally
                        // verticalAlignment = Alignment.CenterVertically
                    ) {

                        if (selectedItemIndex == index) {
                            item.selectedIcon() // Render selected icon
                        } else {
                            item.unselectedIcon() // Render unselected icon
                        }




                    }




                    Spacer(modifier = Modifier.height(2.dp))

                }, colors = NavigationBarItemColors(

                    selectedIconColor = Color.Red,
                    selectedTextColor = Color.Yellow,
                    unselectedIconColor = Color.Transparent,
                    unselectedTextColor = Color.Transparent,
                    selectedIndicatorColor =Color(0xFFFFFFFF),
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Gray
                )
            )
        }
    }
}

