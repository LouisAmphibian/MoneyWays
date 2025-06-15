package com.example.moneywaysapp.data
/*
// AppRepository.kt
class AppRepository(private val appDao: AppDao) {
    // User operations
    suspend fun registerUser(username: String, password: String): Long {
        if (appDao.checkUsernameExists(username) != null) {
            throw Exception("Username already exists")
        }
        return appDao.insertUser(User(username = username, password = password))
    }

    suspend fun loginUser(username: String, password: String): User {
        return appDao.getUser(username, password) ?: throw Exception("Invalid credentials")
    }

    // Category operations
    suspend fun addCategory(userId: Long, name: String, color: String): Long {
        return appDao.insertCategory(Category(userId = userId, name = name, color = color))
    }

    suspend fun getUserCategories(userId: Long): List<Category> {
        return appDao.getUserCategories(userId)
    }

    suspend fun deleteCategory(category: Category) {
        appDao.deleteCategory(category)
    }

    // Expense operations
    suspend fun addExpense(expense: Expense): Long {
        return appDao.insertExpense(expense)
    }

    suspend fun getUserExpenses(userId: Long): List<Expense> {
        return appDao.getUserExpenses(userId)
    }

    suspend fun getExpensesByDateRange(userId: Long, startDate: String, endDate: String): List<Expense> {
        return appDao.getExpensesByDateRange(userId, startDate, endDate)
    }

    suspend fun getExpensesByCategoryAndDate(userId: Long, categoryId: Long, startDate: String, endDate: String): List<Expense> {
        return appDao.getExpensesByCategoryAndDate(userId, categoryId, startDate, endDate)
    }

    suspend fun deleteExpense(expense: Expense) {
        appDao.deleteExpense(expense)
    }

    // Budget goal operations
    suspend fun setBudgetGoal(goal: BudgetGoal): Long {
        val existingGoal = appDao.getUserBudgetGoal(goal.userId)
        return if (existingGoal == null) {
            appDao.insertBudgetGoal(goal)
        } else {
            appDao.updateBudgetGoal(goal.copy(id = existingGoal.id))
            existingGoal.id
        }
    }

    suspend fun getUserBudgetGoal(userId: Long): BudgetGoal? {
        return appDao.getUserBudgetGoal(userId)
    }
}

 */