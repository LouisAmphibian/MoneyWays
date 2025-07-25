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

    //Limits access to only inside the class AND lets me delay initialization of a non-null variable
    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var rePasswordInput: EditText
    private lateinit var registerBtn: Button
    private lateinit var alreadyHaveAcc: TextView

    //check  the last two words of the user
    private fun isValidFullName(name: String):Boolean {
        return name.trim().split("\\s+".toRegex()).size >= 2
    }

    //email having valid email values
    private fun isValidEmail(email: String): Boolean{
        val regex = Regex("^[\\\\w\\\\.-]+@[\\\\w\\\\.-]+\\\\.(com|co\\\\.za|org|net|gov)\$", RegexOption.IGNORE_CASE)
        return regex.matches(email)
    }

    //strong password
    private fun isStrongPassword(password: String): Boolean{
        val lengthOk =password.length >= 8
        val hasUppercase = password.any {it.isUpperCase()}
        val hasLowercase = password.any {it.isLowerCase()}
        val hasDigit = password.any {it.isDigit()}
        val hasSpecialChar = password.any {"!@#\$%^&*()-_=+[]{}|;:'\",.<>?/\\`~".contains(it)}

        return lengthOk && hasUppercase && hasLowercase && hasDigit && hasSpecialChar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameInput = findViewById<EditText>(R.id.nameInput)
        emailInput = findViewById<EditText>(R.id.emailInput)
        passwordInput = findViewById<EditText>(R.id.passwordInput)
        rePasswordInput = findViewById<EditText>(R.id.passwordInput2)
        registerBtn = findViewById<Button>(R.id.signUpBtn)
        alreadyHaveAcc = findViewById<TextView>(R.id.textView9)

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

            try {
                //Check for empty
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // exit the click listener here
                }

                //username contains Name and Surname
                if(!isValidFullName(username)){
                    Toast.makeText(this,"Please enter your full name(Name  and Surname", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                //check email
                if (!isValidEmail(email)) {
                    Toast.makeText(this, "Invaild email format", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                //is it strong
                if(!isStrongPassword(password)){
                    Toast.makeText(this, "Password must be at least 8 characters long and include uppercase, lowercase, number, and special character.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                //check if password match
                if (password != confirmPassword) {
                    Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // exit the click listener here
                }

                // Now that the password is confirmed
                val matchPassword = password


                //Create user object
                val newUser = User(username = username, password = matchPassword)

                //block to register user in RoomDb in background then will show toast and success
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Insert the new user into the Room database using the DAO
                        AppDatabase.getDatabase(this@RegisterActivity).appDao().insertUser(newUser)

                        // Switch context to the Main (UI) thread after database operation
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterActivity, "Registered", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(
                                Intent(
                                    this@RegisterActivity,
                                    LoginActivity::class.java
                                )
                            ) // Navigate to the LoginActivity after successful registration
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterActivity, "Error saving user: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }catch (e:Exception){
                Toast.makeText(this, "Unexpected error: ${e.message}", Toast.LENGTH_LONG).show()

                }
            }
        }
    }
