package com.example.moneywaysapp.data

import androidx.lifecycle.AndroidViewModel
/*

class BudgetViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BudgetRepository

    // LiveData for categories
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    // LiveData for expenses
    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> = _expenses

    // LiveData for budget goal
    private val _budgetGoal = MutableLiveData<BudgetGoal?>()
    val budgetGoal: LiveData<BudgetGoal?> = _budgetGoal

    init {
        val budgetDao = BudgetDatabase.getDatabase(application).budgetDao()
        repository = BudgetRepository(budgetDao)
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _categories.value = repository.getAllCategories()
            _expenses.value = repository.getAllExpenses()
            _budgetGoal.value = repository.getBudgetGoal()
        }
    }

    // Category operations
    suspend fun addCategory(name: String, color: String): Long {
        val id = repository.addCategory(name, color)
        _categories.value = repository.getAllCategories()
        return id
    }

    suspend fun deleteCategory(category: Category) {
        repository.deleteCategory(category)
        _categories.value = repository.getAllCategories()
    }

    // Expense operations
    suspend fun addExpense(
        categoryId: Long,
        date: String,
        startTime: String,
        endTime: String,
        description: String,
        amount: Double,
        photoPath: String? = null
    ): Long {
        val id = repository.addExpense(categoryId, date, startTime, endTime, description, amount, photoPath)
        _expenses.value = repository.getAllExpenses()
        return id
    }

    suspend fun getExpensesByDateRange(startDate: String, endDate: String): List<Expense> {
        return repository.getExpensesByDateRange(startDate, endDate)
    }

    suspend fun getExpensesByCategoryAndDate(categoryId: Long, startDate: String, endDate: String): List<Expense> {
        return repository.getExpensesByCategoryAndDate(categoryId, startDate, endDate)
    }

    suspend fun deleteExpense(expense: Expense) {
        repository.deleteExpense(expense)
        _expenses.value = repository.getAllExpenses()
    }

    // Budget goal operations
    suspend fun setBudgetGoal(minMonthlyGoal: Double, maxMonthlyGoal: Double): Long {
        val id = repository.setBudgetGoal(minMonthlyGoal, maxMonthlyGoal)
        _budgetGoal.value = repository.getBudgetGoal()
        return id
    }
}

 */