package com.example.moneywaysapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moneyways.R
import com.example.moneywaysapp.data.AppDatabase
import com.example.moneywaysapp.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val rePasswordInput = findViewById<EditText>(R.id.passwordInput2)
        val registerBtn = findViewById<Button>(R.id.signUpBtn)
        val alreadyHaveAcc = findViewById<TextView>(R.id.textView9)

        //already have account
        alreadyHaveAcc.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //when  user has login
        registerBtn.setOnClickListener {
            //assigning
            val username = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = rePasswordInput.text.toString().trim()

            //Check for empty
            if(username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
               Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // exit the click listener here
            }

            //check if password match
            if(password != confirmPassword){
                Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // exit the click listener here
            }

            // Now that the password is confirmed
            val matchPassword = password

            //Create user object
            val newUser = User(username = username, password = matchPassword)

            //block to register user in RoomDb in background then will show toast and success
            CoroutineScope(Dispatchers.IO).launch {

                // Insert the new user into the Room database using the DAO
                AppDatabase.getDatabase(this@RegisterActivity).appDao().insertUser(newUser)

                // Switch context to the Main (UI) thread after database operation
                withContext(Dispatchers.Main){
                    Toast.makeText(this@RegisterActivity, "Registered", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity,LoginActivity::class.java)) // Navigate to the LoginActivity after successful registration
                }
            }

        }
    }
}