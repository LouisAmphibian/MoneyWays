package com.example.moneywaysapp

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
import com.example.moneyways.R
import com.google.android.material.textfield.TextInputEditText

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

        //select category method
        selectCategories()
    }

    private fun selectCategories(){
        //assign
        dropBox = findViewById<AutoCompleteTextView>(R.id.categorySpinner)
        //Array list for categories
        val categories = listOf("Groceries", "Entertainment", "Transportation", "Utilities", "Dining Out", "Savings")

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_dropdown_item_1line,
            categories
        )
        dropBox.setAdapter(adapter)

        Log.d("DropBox", "Adapter count: ${adapter.count}") // Debug

        //Show dropdown when clicked
        dropBox.setOnClickListener {
            dropBox.showDropDown()
            Log.d("DEBUG", "Attempted to show dropdown")
        }

        //Set a listener to handle item selection
        dropBox.setOnItemClickListener{ parent, view, position,id ->
            val selectedCategory = parent.getItemAtPosition(position).toString()
            Toast.makeText(this,"Selected: $selectedCategory", Toast.LENGTH_SHORT).show()
        }
    }
}
