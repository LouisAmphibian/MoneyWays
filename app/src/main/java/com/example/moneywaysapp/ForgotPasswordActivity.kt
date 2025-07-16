package com.example.moneywaysapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moneyways.R

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var reciveEmailBtn : Button
    private lateinit var rememberPassword: TextView

    override fun  onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgot_password)

        //asign xml feilds/buttom
        emailInput = findViewById(R.id.emailInput)
        reciveEmailBtn = findViewById(R.id.reciveEmailBtn)
        rememberPassword = findViewById(R.id.rememberPasswordLink)

        rememberPassword.setOnClickListener{
            startActivity(Intent(this, LoginActivity ::class.java))
        }

        //Revieve email
        reciveEmailBtn.setOnClickListener {
            val recipient = emailInput.text.toString().trim()//Get entered email

            if(recipient.isEmpty()){
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Craete an Intent to open email apps(e.g Gmail, Yahoo)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822" //only show email apps
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))  //User email
                putExtra(Intent.EXTRA_SUBJECT, "Moneyways Password Rest") //Email subject
                putExtra(Intent.EXTRA_TEXT, """
                    Hi,

                    You requested to reset your password. Please contact support or visit the app to update it manually.

                    - MoneyWays Support
                """.trimIndent()) //EMAIL body
                }
             try {
                 startActivity(Intent.createChooser(intent, "Send email via..."))
             }catch (ex: Exception) {
                 //No email app found
                 Toast.makeText(this, "No email apps install.", Toast.LENGTH_SHORT).show()
             }

            }

        }

    }

