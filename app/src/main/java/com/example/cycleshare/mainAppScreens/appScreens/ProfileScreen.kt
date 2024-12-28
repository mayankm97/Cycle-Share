package com.example.cycleshare.mainAppScreens.appScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cycleshare.auth.gsignin.GoogleSignInViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        MainContent(navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun MainContent(navController: NavController, modifier: Modifier) {
    val googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()
    val user by googleSignInViewModel.user.collectAsState()

    val currentUser = FirebaseAuth.getInstance().currentUser
    Column(
        modifier
            .fillMaxSize()
            .background(Color(0xFF3F51B5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile Screen",
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
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
    }
}
@Composable
fun ProfileItem(title: String) {
    Text(text = title, style = TextStyle(fontSize = 18.sp))
}
