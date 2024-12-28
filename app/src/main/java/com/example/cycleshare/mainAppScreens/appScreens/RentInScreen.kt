package com.example.cycleshare.mainAppScreens.appScreens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentInScreen() {
    val ownerName = remember { mutableStateOf("") }
    val cycleName = remember { mutableStateOf("") }
    val model = remember { mutableStateOf("") }
    val isGear = remember { mutableStateOf(false) }
    val rentAmount = remember { mutableStateOf("") }
    val contactNumber = remember { mutableStateOf("") }
    val additionalNotes = remember { mutableStateOf("") }
    val context = LocalContext.current
    val navController: NavController? = null
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Put Cycle on Rent") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Owner's Name
            TextField(
                value = ownerName.value,
                onValueChange = { ownerName.value = it },
                label = { Text("Your Name") },
                placeholder = { Text("Enter your name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Cycle Name
            TextField(
                value = cycleName.value,
                onValueChange = { cycleName.value = it },
                label = { Text("Cycle Name") },
                placeholder = { Text("Enter cycle name (e.g., Hero Sprint)") },
                modifier = Modifier.fillMaxWidth()
            )

            // Model (Optional)
            TextField(
                value = model.value,
                onValueChange = { model.value = it },
                label = { Text("Model (Optional)") },
                placeholder = { Text("Enter model name or number") },
                modifier = Modifier.fillMaxWidth()
            )

            // Gear or Without Gear
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Gear Cycle?", modifier = Modifier.weight(1f))
                Switch(
                    checked = isGear.value,
                    onCheckedChange = { isGear.value = it }
                )
            }

            // Rent Amount
            TextField(
                value = rentAmount.value,
                onValueChange = { rentAmount.value = it },
                label = { Text("Rent Amount (per day)") },
                placeholder = { Text("Enter amount in ₹") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Contact Number
            TextField(
                value = contactNumber.value,
                onValueChange = { contactNumber.value = it },
                label = { Text("Contact Number") },
                placeholder = { Text("Enter your contact number") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            // Additional Notes
            TextField(
                value = additionalNotes.value,
                onValueChange = { additionalNotes.value = it },
                label = { Text("Additional Notes (Optional)") },
                placeholder = { Text("Any special instructions or details") },
                modifier = Modifier.fillMaxWidth()
            )

            // Submit Button
            Button(
                onClick = {
                    // Validate and handle submission logic
                    if (ownerName.value.isNotBlank() && cycleName.value.isNotBlank() && rentAmount.value.isNotBlank() && contactNumber.value.isNotBlank()) {
                        Toast.makeText(context,
                            "Cycle listed successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController?.popBackStack() // Navigate back
                    } else {
                        Toast.makeText(
                            context,
                            "Please fill all mandatory fields.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Submit")
            }
        }
    }
}
