package com.example.moneyways

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moneywaysapp.Signup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGetStarted: Button = findViewById(R.id.btnGetStarted)

        btnGetStarted.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
    }

}