package com.example.moneywaysapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moneyways.R

class DashboardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load the dashboard
        setContentView(R.layout.activity_dashboard)
    }
}
