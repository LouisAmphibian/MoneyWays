package com.example.moneywaysapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moneyways.R
import com.example.moneywaysapp.data.AppDatabase
import com.example.moneywaysapp.data.Category
import com.example.moneywaysapp.data.Expense
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.util.Calendar

class AddExpenseActivity: AppCompatActivity() {
    private lateinit var dropBox: AutoCompleteTextView

    private lateinit var descriptionInput: TextInputEditText
    private lateinit var dateInput: TextInputEditText
    private lateinit var startTimeInput: TextInputEditText
    private lateinit var endTimeInput: TextInputEditText
    private lateinit var amountInput: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var addPhotoBtn: Button
    private lateinit var photoPreview: ImageView

    private var selectedImageUri: Uri? = null
    private lateinit var database: AppDatabase
    private var userId: Long = 1 // Replace with actual user ID from your login logic
    private var categories: List<Category> = emptyList()

    // Image picker launcher
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            photoPreview.setImageURI(uri)
        } else {
            Toast.makeText(this, "Image not selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("AddExpense", "Activity created")  // Check if this appears in logs

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        // Initialize database
        database = AppDatabase.getDatabase(this)

        Log.d("DEBUG", "ACTIVITY IS RUNNING") // Check if this appears

        //UI components
        descriptionInput = findViewById(R.id.expenseDescription)
        dateInput = findViewById(R.id.expenseDate)
        startTimeInput = findViewById(R.id.startTime)
        endTimeInput = findViewById(R.id.endTime)
        amountInput = findViewById(R.id.minGoalInput)
        saveButton = findViewById(R.id.saveExpenseBtn)
        addPhotoBtn = findViewById(R.id.addPhotoBtn)
        photoPreview = findViewById(R.id.photoPreview)

        // Set click listeners
        saveButton.setOnClickListener { saveExpense() }
        addPhotoBtn.setOnClickListener { pickImageLauncher.launch("image/*") }

        //select category method
        loadCategories()

        //Time/Date inputs when clicked
        dateInput.setOnClickListener { showDatePicker(dateInput) }
        startTimeInput.setOnClickListener { showTimePicker(startTimeInput) }
        endTimeInput.setOnClickListener { showTimePicker(endTimeInput) }
    }

    //SelectCategory
    private fun loadCategories() {
        lifecycleScope.launch {
            try {
                categories = database.appDao().getUserCategories(userId)
                if (categories.isEmpty()) {
                    // If no categories exist, create default ones
                    createDefaultCategories()
                    categories = database.appDao().getUserCategories(userId)
                }
                setupCategoryDropdown()
            } catch (e: Exception) {
                Log.e("AddExpense", "Error loading categories", e)
                Toast.makeText(this@AddExpenseActivity, "Error loading categories", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //default category
    private suspend fun createDefaultCategories() {
        val defaultCategories = listOf(
            Category(userId = userId, name = "Groceries", color = "#4CAF50"),
            Category(userId = userId, name = "Entertainment", color = "#2196F3"),
            Category(userId = userId, name = "Transportation", color = "#FF9800"),
            Category(userId = userId, name = "Utilities", color = "#9C27B0"),
            Category(userId = userId, name = "Dining Out", color = "#F44336"),
            Category(userId = userId, name = "Savings", color = "#607D8B")
        )

        defaultCategories.forEach { category ->
            database.appDao().insertCategory(category)
        }
    }

    //setup method
    private fun setupCategoryDropdown() {
        dropBox = findViewById(R.id.categorySpinner)

        val categoryNames = categories.map { it.name }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            categoryNames
        )
        dropBox.setAdapter(adapter)

        dropBox.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Selected: ${categories[position].name}", Toast.LENGTH_SHORT).show()
        }

        dropBox.setOnClickListener {
            dropBox.showDropDown()
        }
    }

    /**Time/Date methods
     * ShowDatePicker
     * ShowTimePicker (START)
     * showTimePicker (END)
     * **/
    private fun showDatePicker(target: TextInputEditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, day ->
            val dateStr = String.format("%04d-%02d-%02d", year, month + 1, day)
            target.setText(dateStr)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showTimePicker(target: TextInputEditText) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(this, { _, hour, minute ->
            val timeStr = String.format("%02d:%02d", hour, minute)
            target.setText(timeStr)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
    }

    //Save Expenses method
    private fun saveExpense() {
        val desc = descriptionInput.text.toString().trim()
        val date = dateInput.text.toString().trim()
        val startTime = startTimeInput.text.toString().trim().takeIf { it.isNotEmpty() }
        val endTime = endTimeInput.text.toString().trim().takeIf { it.isNotEmpty() }
        val amountStr = amountInput.text.toString().trim()
        val selectedCategoryName = dropBox.text.toString().trim()

        if (desc.isEmpty() || date.isEmpty() || amountStr.isEmpty() || selectedCategoryName.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDoubleOrNull() ?: run {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedCategory = categories.find { it.name == selectedCategoryName }
        if (selectedCategory == null) {
            Toast.makeText(this, "Invalid category selected", Toast.LENGTH_SHORT).show()
            return
        }

        val expense = Expense(
            userId = userId,
            categoryId = selectedCategory.id,
            date = date,
            startTime = startTime,
            endTime = endTime,
            description = desc,
            amount = amount,
            photoPath = selectedImageUri?.toString()
        )

        lifecycleScope.launch {
            try {
                database.appDao().insertExpense(expense)
                Toast.makeText(this@AddExpenseActivity, "Expense saved", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Log.e("AddExpense", "Error saving expense", e)
                Toast.makeText(this@AddExpenseActivity, "Failed to save expense", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
