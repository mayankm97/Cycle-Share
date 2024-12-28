package com.example.cycleshare.rentOut

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import java.io.File
import java.io.FileOutputStream

@Composable
fun RentOutScreen(navController: NavController) {
    val cycleName = remember { mutableStateOf("") }
    val rentAmount = remember { mutableStateOf("") }
    val selectedImages = remember { mutableStateListOf<String>() }
    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser
    val firstName = currentUser?.displayName?.split(" ")?.firstOrNull() ?: "User"
    val viewModel: RentOutViewModel = hiltViewModel()

    var isRentValid by remember { mutableStateOf(true) }
    val isGeared = remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        selectedImages.clear()
        selectedImages.addAll(uris.map { it.toString() })
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = saveBitmapToInternalStorage(context, it)
            uri?.let { selectedImages.add(it.toString()) }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Greeting with first name
            Text(
                text = "Hi $firstName, enter details below:",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            )

            // Cycle Name
            OutlinedTextField(
                value = cycleName.value,
                onValueChange = { cycleName.value = it },
                label = { Text("Cycle Name") },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Image Selection
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Select Images")
                }
                Button(
                    onClick = { cameraLauncher.launch() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Take Photo")
                }
            }

            // Display selected images
            if (selectedImages.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(selectedImages) { uri ->
                        Image(
                            painter = rememberAsyncImagePainter(model = uri),
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            // Geared Cycle Switch
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Geared:",
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = isGeared.value,
                    onCheckedChange = { isGeared.value = it }
                )
            }

            // Rent Amount
            OutlinedTextField(
                value = rentAmount.value,
                onValueChange = { input ->
                    rentAmount.value = input.filter { it.isDigit() }
                    isRentValid = rentAmount.value.toIntOrNull() in 20..80
                },
                label = {
                    Text(
                        text = if (isRentValid) "Rent Amount (20 to 80 per hour)" else "Enter valid amount",
                        color = if (isRentValid) Color.Black else Color.Red
                    )
                },
                isError = !isRentValid,
                shape = RoundedCornerShape(15.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Submit Button
            Button(
                onClick = {
                    val rentValue = rentAmount.value.toIntOrNull()
                    if (cycleName.value.isNotBlank() && rentValue in 20..80 && selectedImages.isNotEmpty()) {
                        viewModel.addCycle(
                            cycleName = cycleName.value,
                            rentAmount = rentValue!!,
                            selectedImages = selectedImages.toList(),
                            isGearCycle = isGeared.value
                        )
                        Toast.makeText(context, "Cycle listed successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Please fill all mandatory fields correctly.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF1493))
            ) {
                Text("Submit")
            }
        }
    }
}

// Helper function to save bitmap to internal storage and return its URI
private fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val filename = "${System.currentTimeMillis()}.jpg"
        val file = File(context.cacheDir, filename)
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
