package com.example.moneywaysapp.data

// Category.kt
@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val color: String
)

// Expense.kt
@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoryId: Long,
    val date: String, // Format: "YYYY-MM-DD"
    val startTime: String, // Format: "HH:MM"
    val endTime: String, // Format: "HH:MM"
    val description: String,
    val amount: Double,
    val photoPath: String? = null
)

// BudgetGoal.kt
@Entity(tableName = "budget_goals")
data class BudgetGoal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val minMonthlyGoal: Double,
    val maxMonthlyGoal: Double
)