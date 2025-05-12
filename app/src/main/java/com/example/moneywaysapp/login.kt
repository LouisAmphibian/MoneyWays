package com.example.moneywaysapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.moneyways.R

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginactivity)
        val email = findViewById<EditText>(R.id.emailInput)
        val password = findViewById<EditText>(R.id.passwordInput)
        val loginBtn = findViewById<Button>(R.id.loginBtn)

        loginBtn.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                // TODO: Validate login
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }

            }

        }
    }