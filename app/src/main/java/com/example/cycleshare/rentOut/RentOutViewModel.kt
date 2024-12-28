package com.example.cycleshare.rentOut

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RentOutViewModel @Inject constructor() : ViewModel() {
    val firebaseDatabase = Firebase.database    // provides access to the Firebase Realtime Database.
    private val _attr = MutableStateFlow<List<RentOutAttr>>(emptyList())
    val attr = _attr.asStateFlow()
    private val db = Firebase.database

    private fun getCycles() {
        firebaseDatabase.getReference("cycles").get().addOnSuccessListener { snapshot ->
            val list = mutableListOf<RentOutAttr>()
            snapshot.children.forEach { data ->
                val cycle = data.getValue(RentOutAttr::class.java)
                cycle?.let { list.add(it) } // Add only non-null cycles
            }
            _attr.value = list
        }
    }

    fun addCycle(cycleName: String, rentAmount: Int, selectedImages: List<String>, isGearCycle: Boolean) {
        val key = firebaseDatabase.getReference("cycles").push().key
        val cycle = RentOutAttr(
            db.reference.push().key ?: UUID.randomUUID().toString(),
            Firebase.auth.currentUser?.displayName ?: "na",
            cycleName = cycleName,
            imageUrls = selectedImages,
            isGearCycle = isGearCycle,
            rentAmount = rentAmount
        )
        firebaseDatabase.getReference("cycles").child(key!!).setValue(cycle).addOnSuccessListener {
            getCycles() // Refresh the list after adding
        }
    }

}