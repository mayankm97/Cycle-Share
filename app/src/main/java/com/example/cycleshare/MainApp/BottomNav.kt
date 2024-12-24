package com.example.cycleshare.MainApp

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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cycleshare.auth.gsignin.GoogleSignInViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import com.example.cycleshare.MainApp.appScreens.NavItem
import com.example.cycleshare.MainApp.appScreens.ProfileScreen
import com.example.cycleshare.MainApp.appScreens.RentInScreen
import com.example.cycleshare.MainApp.appScreens.RentOutScreen

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
        0 -> RentInScreen()
        1 -> RentOutScreen()
        2 -> ProfileScreen(navController)
    }
}

/*
@Composable
fun HomeScreen(navController: NavController, googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()) {
    // Observe the user's login status
    val user by googleSignInViewModel.user.collectAsState()
    val context = LocalContext.current

    val currentUser = FirebaseAuth.getInstance().currentUser

    // Remember the selected bottom navigation item
    var selectedItem by remember { mutableStateOf("home") }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .navigationBarsPadding() // Adds padding to avoid overlap with system navigation bar
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Add, contentDescription = "Rent In") },
                    label = { Text("Rent In") },
                    selected = selectedItem == Routes.rentInTab,
                    onClick = {
                        selectedItem = Routes.rentInTab
                        navController.navigate(Routes.rentInTab) { launchSingleTop = true }
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Add, contentDescription = "Rent Out") },
                    label = { Text("Rent Out") },
                    selected = selectedItem == Routes.rentOutTab,
                    onClick = {
                        selectedItem = Routes.rentOutTab
                        navController.navigate(Routes.rentOutTab) { launchSingleTop = true }
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedItem == Routes.profileTab,
                    onClick = {
                        selectedItem = Routes.profileTab
                        navController.navigate(Routes.profileTab) { launchSingleTop = true }
                    }
                )
            }
        },
        content = { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = selectedItem,
                Modifier.padding(paddingValues)
            ) {
                composable(Routes.rentInTab) { RentInScreen(navController) }
                composable(Routes.rentOutTab) { RentOutScreen(navController) }
                composable(Routes.profileTab) { ProfileScreen() }
            }
        }
        /*content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                if (user == null && currentUser == null) {
                    Text("Loading user data...")  // Show loading state
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ProfileItem(title = currentUser?.displayName.toString())
                        //user?.email?.let { ProfileItem(title = currentUser?.displayName.toString()) }
                        Button(
                            onClick = {
                                googleSignInViewModel.signOut(navController)
                            }) {
                            Text(text = "Sign out")
                        }
                    }
                }
            }*/

    )
}
*/
@Composable
fun ProfileItem(title: String) {
    Text(text = title, style = TextStyle(fontSize = 18.sp))
}
