package com.example.cycleshare.mainAppScreens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cycleshare.auth.gsignin.GoogleSignInViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.cycleshare.mainAppScreens.appScreens.NavItem
import com.example.cycleshare.mainAppScreens.appScreens.ProfileScreen
import com.example.cycleshare.mainAppScreens.appScreens.RentInScreen
import com.example.cycleshare.rentOut.RentOutScreen

@Composable
fun BottomNav(navController: NavController, googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()) {

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val navItemList = listOf(
        NavItem("Add", Icons.Default.Add),
        NavItem("Search", Icons.Default.Search),
        NavItem("Profile", Icons.Default.Person)
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = "Icon")
                        },
                        label = {
                            Text(text = navItem.label)
                        }
                        )
                }
            }
        }
        ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex, navController)
    }
}

@Composable
fun ContentScreen(modifier: Modifier, selectedIndex : Int, navController: NavController) {
    when(selectedIndex) {
        0 -> RentOutScreen(navController)
        1 -> RentInScreen()
        2 -> ProfileScreen(navController)
    }
}
