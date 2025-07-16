package com.example.moneywaysapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

// AppDao.kt
@Dao
interface AppDao {
    // User operations

    //for registering
    @Insert
    suspend fun insertUser(user: User): Long

    //for login using username or email
    @Query("SELECT * FROM users WHERE (username = :identifier OR email = :identifier) AND password = :password LIMIT 1")
    suspend fun getUserByUsernameOrEmail(identifier: String, password: String): User?

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

    /*
    //view
    // Query to retrieve total spent per category
    @Query("""
    SELECT c.name AS categoryName, c.color AS categoryColor, SUM(e.amount) AS totalAmount
    FROM expenses e
    JOIN categories c ON e.categoryId = c.id
    WHERE e.userId = :userId AND e.date BETWEEN :startDate AND :endDate
    GROUP BY e.categoryId
""")
    suspend fun getCategoryTotals(userId: Long, startDate: String?, endDate: String?): List<CategoryTotal>

    // Query to retrieve all expenses with joined category data
    //or joining category + expense for ReportViewActivity
    @Query("""
    SELECT e.*, c.name AS categoryName, c.color AS categoryColor
    FROM expenses e
    JOIN categories c ON e.categoryId = c.id
    WHERE e.userId = :userId AND e.date BETWEEN :startDate AND :endDate
    ORDER BY e.date DESC
""")
    suspend fun getExpensesWithCategories(userId: Long, startDate: String?, endDate: String?): List<ExpenseWithCategory>

*/
    /*
    // Budget goal operations
    @Insert
    suspend fun insertBudgetGoal(goal: BudgetGoal): Long

    @Query("SELECT * FROM budget_goals WHERE userId = :userId LIMIT 1")
    suspend fun getUserBudgetGoal(userId: Long): BudgetGoal?

    @Update
    suspend fun updateBudgetGoal(goal: BudgetGoal)
    */

}

