package com.example.moneywaysapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moneyways.R
import com.example.moneywaysapp.data.AppDatabase
import com.example.moneywaysapp.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity:AppCompatActivity() {

    //Limits access to only inside the class AND lets me delay initialization of a non-null variable
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var doNotHaveAcc: TextView
    private lateinit var forgotPassword : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load login page
        setContentView(R.layout.activity_login)

        //asigin xml field/buttons
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginBtn =  findViewById(R.id.loginBtn)
        doNotHaveAcc =  findViewById(R.id.textView9)
        forgotPassword = findViewById(R.id.resetPasswordLink)

        //for a user who doesn't have an account
        doNotHaveAcc.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginBtn.setOnClickListener {
            val rawInput = emailInput.text.toString()
            val password = passwordInput.text.toString()


            //Check if empty
            if(rawInput.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please  fill all field",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //allows login with either full name
            val identifier = if(isValidEmail(rawInput)) {
                rawInput //valid email
            } else {
                normalizeToFirstAndLast(rawInput)
            }


            //block to login user in RoomDb in background then will show toast and success
            CoroutineScope(Dispatchers.IO).launch {

                //Attempt to fetch the user from the database
                val existingUser =  AppDatabase.getDatabase(this@LoginActivity).appDao().getUserByUsernameOrEmail(rawInput, password)

                // Switch context to the Main (UI) thread after database operation
                withContext(Dispatchers.Main){

                    //if user exists
                    if(existingUser != null){
                        Toast.makeText(this@LoginActivity, "Welcome ${existingUser.username}", Toast.LENGTH_SHORT).show()

                        // Navigate to the Dashboard after successful registration
                        val intent = Intent(this@LoginActivity, DashboardActivity :: class.java)
                        intent.putExtra("username", existingUser.username)
                        startActivity(intent)
                    }else
                    {
                        Toast.makeText(this@LoginActivity,"Invalid. No user: $rawInput",Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

        forgotPassword.setOnClickListener{
            startActivity(Intent(this, ForgotPasswordActivity:: class.java))
        }

    }

    //Take the first and last of the full name
    private fun normalizeToFirstAndLast(name: String): String{
        val parts = name.trim().split("\\s+".toRegex())
        return if  (parts.size >= 2) "${parts.first()} ${parts.last()}" else name
    }

    //email having valid email values
    private fun isValidEmail(email: String): Boolean{
        val regex = Regex("^[\\w.-]+@[\\w.-]+\\.(com|co\\.za|org|net|gov)$", RegexOption.IGNORE_CASE)
        return regex.matches(email)
    }
}

/* Old code
// RegisterActivity.kt

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AppViewModel::class.java]

        binding.registerButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    viewModel.register(username, password)
                    Toast.makeText(this@RegisterActivity, "Registration successful! Please login.", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.loginButton.setOnClickListener {
            finish()
        }
    }
}

 */
