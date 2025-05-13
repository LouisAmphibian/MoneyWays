package com.example.moneywaysapp.data

@Dao
interface BudgetDao {
    // Category operations
    @Insert
    suspend fun insertCategory(category: Category): Long

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>

    @Delete
    suspend fun deleteCategory(category: Category)

    // Expense operations
    @Insert
    suspend fun insertExpense(expense: Expense): Long

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    suspend fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getExpensesByDateRange(startDate: String, endDate: String): List<Expense>

    @Query("SELECT * FROM expenses WHERE categoryId = :categoryId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getExpensesByCategoryAndDate(categoryId: Long, startDate: String, endDate: String): List<Expense>

    @Delete
    suspend fun deleteExpense(expense: Expense)

    // Budget goal operations
    @Insert
    suspend fun insertBudgetGoal(goal: BudgetGoal): Long

    @Query("SELECT * FROM budget_goals LIMIT 1")
    suspend fun getBudgetGoal(): BudgetGoal?

    @Update
    suspend fun updateBudgetGoal(goal: BudgetGoal)
}