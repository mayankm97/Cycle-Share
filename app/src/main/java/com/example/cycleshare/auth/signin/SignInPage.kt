package com.example.cycleshare.auth.signin

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cycleshare.R
import com.example.cycleshare.Others.Routes
import com.example.cycleshare.auth.gsignin.GoogleSignInViewModel
import com.example.cycleshare.ui.theme.accent1eco
import com.example.cycleshare.ui.theme.background1eco
import com.example.cycleshare.ui.theme.background1neon
import com.example.cycleshare.ui.theme.background2neon
import com.example.cycleshare.ui.theme.primary2neon

@Composable
fun SignInScreen(navController: NavController, googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()) {

    val viewModel: SignInViewModel = hiltViewModel()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is SignInState.Success -> {
                navController.navigate(Routes.homeScreen) {
                    popUpTo(0)
                }
            }
            is SignInState.Error -> {
                Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                /*   .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            primary2neon,
                            accent1eco
                        )
                    )
                )*/
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(11.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(90.dp))

                Text(
                    text = "Signin",
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center
                    /* fontWeight = FontWeight.ExtraBold,
                    //letterSpacing = 1.5.sp,
                    //  fontFamily = FontFamily.Cursive,
                    // textDecoration = TextDecoration.Underline*/
                )
                Spacer(modifier = Modifier.height(50.dp))
/*
                // Username TextField
//            OutlinedTextField(
//                value = username,
//                onValueChange = { username = it },
//                label = { Text("Username") },
//                colors = TextFieldDefaults.colors(
//                    focusedIndicatorColor = Color.Gray, // Neon Green
//                    unfocusedIndicatorColor = Color.Gray,
//                    cursorColor = Color.Gray, // Neon Cyan
//                    focusedLabelColor = Color.Gray,
//                    unfocusedLabelColor = Color.Gray
//                ),
//                shape = RoundedCornerShape(15.dp),
//                modifier = Modifier.fillMaxWidth(0.83f) // Reduce width for minimalistic look
//            )
//            Spacer(modifier = Modifier.height(15.dp))
*/
                // Email TextField
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                   /* colors = TextFieldDefaults.colors(
                        //focusedIndicatorColor = primary2neon, // Neon Green
                        //unfocusedIndicatorColor = Color.Gray,
                        cursorColor = Color.Gray, // Neon Cyan
                        focusedLabelColor = background1eco,
                        unfocusedLabelColor = Color.Gray
                    ),*/
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(0.83f) // Reduce width for minimalistic look
                )
                Spacer(modifier = Modifier.height(15.dp))

                // Password TextField
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(0.83f) // Reduce width for minimalistic look
                )
                Spacer(modifier = Modifier.height(25.dp))

                // Sign Up Button
                if (uiState.value == SignInState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF1493) // Neon Pink
                        ),
                        onClick = {
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                viewModel.signIn(email, password)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Enter your email and password.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.5f) // Reduced width for a sleeker look
                            .padding(5.dp, 0.dp),
                        enabled = email.isNotEmpty() && password.isNotEmpty() && (uiState.value == SignInState.Nothing || uiState.value == SignInState.Error)
                    ) {
                        Text(text = "Sign In", color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

                TextButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {
                        navController.navigate(Routes.signUpScreen)
                    },
                ) {
                    Text(
                        text = "Don't have an account?\nSignUp",
                        color = background2neon,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(10.dp, 0.dp),
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))

                // Row for social sign-in buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        onClick = {
                            googleSignInViewModel.handleGoogleSignIn(googleSignInViewModel, context, navController)
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Sign in",
                            fontSize = 10.sp,
                            color = background1neon
                        )
                    }
                    /*
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF3b5998) // Facebook color
                                    ),
                                    onClick = { /* TODO */ },
                                    modifier = Modifier.width(120.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.facebook),
                                        contentDescription = "",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(text = "Sign in", fontSize = 10.sp)
                                }*/
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    SignInScreen(navController = rememberNavController())
}