package com.example.moneywaysapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


// User.kt
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val email: String,
    val password: String
)


/*
// Expense.kt
@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val categoryId: Long,
    val date: String, // Stored as "YYYY-MM-DD"
    val startTime: String, // Stored as "HH:MM"
    val endTime: String, // Stored as "HH:MM"
    val description: String,
    val amount: Double,
    val photoPath: String? = null
)

// BudgetGoal.kt
@Entity(tableName = "budget_goals")
data class BudgetGoal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val minMonthlyGoal: Double,
    val maxMonthlyGoal: Double
)

 */