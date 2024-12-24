package com.example.cycleshare.MainApp.appScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun RentInScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFCF1616)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Rent In Screen",
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
            )
    }
}