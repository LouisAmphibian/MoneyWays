package com.example.moneywaysapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.moneyways.R
import com.example.moneywaysapp.data.AppDatabase
import com.example.moneywaysapp.data.CategoryTotal
import com.example.moneywaysapp.dialog.DateRangePickerDialog
import com.example.moneywaysapp.manager.RewardsManager

class ViewStatsActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var dateRangePicker: Button
    private var startDate: String? = null
    private var endDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stats)

        database = AppDatabase.getDatabase(this)
        dateRangePicker = findViewById(R.id.dateRangePicker)

        dateRangePicker.setOnClickListener {
            showDateRangePickerDialog()
        }

        loadStatistics()
    }

    private fun showDateRangePickerDialog() {
        val dialog = DateRangePickerDialog(this,
            { start, end ->
                startDate = start
                endDate = end
                loadStatistics()
            })
        dialog.show()
    }

    private fun loadStatistics() {
        lifecycleScope.launch {
            val userId = getCurrentUserId()
            val categoryTotals = database.appDao().getCategoryTotals(
                userId = userId,
                startDate = startDate,
                endDate = endDate
            )

            // Update UI with category totals
            updateChart(categoryTotals)
            updateRewardsMessage(categoryTotals)
        }
    }

    private fun updateChart(categoryTotals: List<CategoryTotal>) {
        val chart = findViewById<BarChart>(R.id.chartView)
        val entries = categoryTotals.mapIndexed { index, total ->
            BarEntry(index.toFloat(), total.totalAmount.toFloat())
        }
        val dataSet = BarDataSet(entries, "Amount Spent per Category")
        dataSet.colors = categoryTotals.map { Color.parseColor(it.categoryColor) }
        val data = BarData(dataSet)
        chart.data = data
        chart.invalidate()

        // Budget goals visualized using limit lines
        val minGoal = 500f
        val maxGoal = 2000f
        val limitMin = LimitLine(minGoal, "Min Goal")
        val limitMax = LimitLine(maxGoal, "Max Goal")
        chart.axisLeft.addLimitLine(limitMin)
        chart.axisLeft.addLimitLine(limitMax)
    }

    private fun updateRewardsMessage(categoryTotals: List<CategoryTotal>) {
        val totalSpent = categoryTotals.sumOf { it.totalAmount }
        val (badge, message) = RewardsManager.getBadge(totalSpent)
        findViewById<TextView>(R.id.rewardTitle).text = "🏆 $badge"
        findViewById<TextView>(R.id.rewardMessage1).text = message
    }
    }



}