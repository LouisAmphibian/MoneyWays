package com.example.moneywaysapp

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moneyways.R

class AddExpenseActivity: AppCompatActivity() {
    private lateinit var dropBox: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("AddExpense", "Activity created")  // Check if this appears in logs

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        Log.d("DEBUG", "ACTIVITY IS RUNNING") // Check if this appears

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
