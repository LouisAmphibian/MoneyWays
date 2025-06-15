package com.example.moneywaysapp.data

/*
// AppDao.kt
@Dao
interface AppDao {
    // User operations
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUser(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun checkUsernameExists(username: String): User?

    // Category operations
    @Insert
    suspend fun insertCategory(category: Category): Long

    @Query("SELECT * FROM categories WHERE userId = :userId")
    suspend fun getUserCategories(userId: Long): List<Category>

    @Delete
    suspend fun deleteCategory(category: Category)

    // Expense operations
    @Insert
    suspend fun insertExpense(expense: Expense): Long

    @Query("SELECT * FROM expenses WHERE userId = :userId ORDER BY date DESC")
    suspend fun getUserExpenses(userId: Long): List<Expense>

    @Query("SELECT * FROM expenses WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getExpensesByDateRange(userId: Long, startDate: String, endDate: String): List<Expense>

    @Query("SELECT * FROM expenses WHERE userId = :userId AND categoryId = :categoryId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getExpensesByCategoryAndDate(userId: Long, categoryId: Long, startDate: String, endDate: String): List<Expense>

    @Delete
    suspend fun deleteExpense(expense: Expense)

    // Budget goal operations
    @Insert
    suspend fun insertBudgetGoal(goal: BudgetGoal): Long

    @Query("SELECT * FROM budget_goals WHERE userId = :userId LIMIT 1")
    suspend fun getUserBudgetGoal(userId: Long): BudgetGoal?

    @Update
    suspend fun updateBudgetGoal(goal: BudgetGoal)
}

 */