package com.example.cycleshare.auth.signup

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cycleshare.R
import com.example.cycleshare.Others.Routes
import com.example.cycleshare.auth.gsignin.GoogleSignInViewModel
import com.example.cycleshare.auth.signin.SignInScreen
import com.example.cycleshare.ui.theme.accent1eco
import com.example.cycleshare.ui.theme.background1eco
import com.example.cycleshare.ui.theme.background1neon
import com.example.cycleshare.ui.theme.background2neon
import com.example.cycleshare.ui.theme.primary2neon

@Composable
fun SignUpScreen(navController: NavController, googleSignInViewModel: GoogleSignInViewModel = hiltViewModel()) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            is SignUpState.Success -> {
                navController.navigate(Routes.homeScreen) {
                    popUpTo(0)
                }
            }
            is SignUpState.Error -> {
                Toast.makeText(context, "Sign up failed", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .background(
//                    Brush.verticalGradient(
//                        colors = listOf(
//                            background1eco,
//                            accent1eco
//                        )
//                    )
//                )
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
                    text = "Signup",
                    fontSize = 32.sp,
                    color = background1neon,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))

                // Username TextField
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(0.83f)
                )
                Spacer(modifier = Modifier.height(15.dp))

                // Email TextField
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(0.83f)
                )
                Spacer(modifier = Modifier.height(15.dp))

                // Password TextField
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(0.83f)
                )
                Spacer(modifier = Modifier.height(25.dp))

                // Confirm Password TextField
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
//                    colors = TextFieldDefaults.colors(
//                        cursorColor = Color.Gray,
//                        focusedLabelColor = background1eco,
//                        unfocusedLabelColor = Color.Gray
//                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(0.83f)
                )
                Spacer(modifier = Modifier.height(25.dp))

                // Sign Up Button

                if (uiState.value == SignUpState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp),
                        onClick = {
                            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty() &&
                                confirmPassword.isNotEmpty() && password == confirmPassword) {
                                viewModel.signUp(username, email, password)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please fill in all fields and ensure passwords match.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(0.5f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF1493)
                        ),
                        enabled = username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() &&
                                confirmPassword.isNotEmpty() && password == confirmPassword
                    ) {
                        Text(text = "Sign Up", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    TextButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Text(
                            text = "Already have an account?\nSignIn",
                            color = background2neon,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(10.dp, 0.dp),
                            textAlign = TextAlign.End
                        )
                    }
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
                            text = "Sign Up",
                            fontSize = 10.sp,
                            color = background1neon
                        )
                    }
                }
            }
//            Image(
//                modifier = Modifier.fillMaxSize(),
//                painter = painterResource(id = R.drawable.cycling),
//                contentDescription = "",
//                alignment = Alignment.BottomCenter,
//                contentScale = ContentScale.Fit
//            )
        }
    }
}
