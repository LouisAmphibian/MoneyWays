package com.example.moneywaysapp.data
/*
import androidx.lifecycle.AndroidViewModel

// AppViewModel.kt
class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    init {
        val appDao = AppDatabase.getDatabase(application).appDao()
        repository = AppRepository(appDao)
    }

    // User operations
    suspend fun register(username: String, password: String): Long {
        return repository.registerUser(username, password)
    }

    suspend fun login(username: String, password: String) {
        val user = repository.loginUser(username, password)
        _currentUser.postValue(user)
    }

    fun logout() {
        _currentUser.postValue(null)
    }

    // Category operations
    suspend fun addCategory(name: String, color: String): Long {
        val userId = currentUser.value?.id ?: throw Exception("User not logged in")
        return repository.addCategory(userId, name, color)
    }

    fun getUserCategories(): LiveData<List<Category>> {
        val userId = currentUser.value?.id ?: return MutableLiveData(emptyList())
        return repository.getUserCategories(userId).asLiveData()
    }

    suspend fun deleteCategory(category: Category) {
        repository.deleteCategory(category)
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
        val userId = currentUser.value?.id ?: throw Exception("User not logged in")
        val expense = Expense(
            userId = userId,
            categoryId = categoryId,
            date = date,
            startTime = startTime,
            endTime = endTime,
            description = description,
            amount = amount,
            photoPath = photoPath
        )
        return repository.addExpense(expense)
    }

    fun getUserExpenses(): LiveData<List<Expense>> {
        val userId = currentUser.value?.id ?: return MutableLiveData(emptyList())
        return repository.getUserExpenses(userId).asLiveData()
    }

    fun getExpensesByDateRange(startDate: String, endDate: String): LiveData<List<Expense>> {
        val userId = currentUser.value?.id ?: return MutableLiveData(emptyList())
        return repository.getExpensesByDateRange(userId, startDate, endDate).asLiveData()
    }

    fun getExpensesByCategoryAndDate(categoryId: Long, startDate: String, endDate: String): LiveData<List<Expense>> {
        val userId = currentUser.value?.id ?: return MutableLiveData(emptyList())
        return repository.getExpensesByCategoryAndDate(userId, categoryId, startDate, endDate).asLiveData()
    }

    suspend fun deleteExpense(expense: Expense) {
        repository.deleteExpense(expense)
    }

    // Budget goal operations
    suspend fun setBudgetGoal(minMonthlyGoal: Double, maxMonthlyGoal: Double): Long {
        val userId = currentUser.value?.id ?: throw Exception("User not logged in")
        return repository.setBudgetGoal(BudgetGoal(userId = userId, minMonthlyGoal = minMonthlyGoal, maxMonthlyGoal = maxMonthlyGoal))
    }

    fun getUserBudgetGoal(): LiveData<BudgetGoal?> {
        val userId = currentUser.value?.id ?: return MutableLiveData(null)
        return repository.getUserBudgetGoal(userId).asLiveData()
    }
}

 */