package com.example.moneywaysapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Category.kt
@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val name: String,
    val color: String = "#FF0000"
)

/*
// BudgetGoal.kt
@Entity(tableName = "budget_goals")
data class BudgetGoal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val minMonthlyGoal: Double,
    val maxMonthlyGoal: Double
)

 */