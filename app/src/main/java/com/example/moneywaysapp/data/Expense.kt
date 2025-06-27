package com.example.moneywaysapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Expense.kt
@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val categoryId: Long,
    val date: String, // Format: "YYYY-MM-DD"
    val startTime: String?, // Format: "HH:MM"
    val endTime: String?, // Format: "HH:MM"
    val description: String,
    val amount: Double,
    val photoPath: String? = null
)