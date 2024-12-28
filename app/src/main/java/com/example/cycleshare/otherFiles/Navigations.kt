package com.example.cycleshare.otherFiles

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cycleshare.auth.gsignin.GoogleSignInViewModel
import com.example.cycleshare.auth.signin.SignInScreen
import com.example.cycleshare.auth.signup.SignUpScreen
import com.example.cycleshare.mainAppScreens.BottomNav
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MyAppNavs(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    val currentUser = FirebaseAuth.getInstance().currentUser
    val start = if (currentUser != null) Routes.homeScreen else Routes.signInScreen
    val googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()
    val context = LocalContext.current

    NavHost(navController, startDestination = start, builder = {
        composable(Routes.signUpScreen) {
            SignUpScreen(navController)
        }
        composable(Routes.signInScreen) {
            SignInScreen(navController, googleSignInViewModel)
            //googleSignInViewModel.handleGoogleSignIn(context,navController)
        }
        composable(Routes.homeScreen) {
            BottomNav(navController, googleSignInViewModel)
        }
    })
}