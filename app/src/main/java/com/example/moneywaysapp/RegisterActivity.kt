package com.example.moneywaysapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.moneyways.R

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fullName = findViewById<EditText>(R.id.fullNameInput)
        val email = findViewById<EditText>(R.id.emailInput)
        val password = findViewById<EditText>(R.id.passwordInput)
        val registerBtn = findViewById<Button>(R.id.registerBtn)
        val presloginbtn = findViewById<Button>(R.id.presloginbtn) // Matches XML id

        registerBtn.setOnClickListener {
            val nameText = fullName.text.toString()
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            if (nameText.isNotEmpty() && emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                //Auto-login after registration
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        presloginbtn.setOnClickListener {
            // Simple redirect without any checks
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}