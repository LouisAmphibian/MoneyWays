// DashboardActivity.kt
package com.example.moneywaysapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moneyways.R

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard) // matches your XML filename
    }
}