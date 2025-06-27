package com.example.moneywaysapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.moneyways.R

class DashboardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load the dashboard
        setContentView(R.layout.activity_dashboard)

        //Get user from intent
        val username = intent.getStringExtra("username")

        //Find the greeting TextView and set the actual name
        val greetingUsername = findViewById<TextView>(R.id.greetingText)
        greetingUsername.text = "Hello, $username"

        //adding a expense
        val button = findViewById<Button>(R.id.addExpenseButton)
        button.setOnClickListener {
            //load  add expense page
            //setContentView(R.layout.activity_add_expense)
            navigateToAddExpense()
        }
    }

    private fun navigateToAddExpense() {
        val intent = Intent(this, AddExpenseActivity::class.java)
        startActivity(intent)
    }
}
