package com.example.moneywaysapp.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.moneyways.R
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class DateRangePickerDialog(
    context: Context,
    private val onDateRangeSelected: (startDate: String, endDate: String) -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_date_range_picker)

        val startDateInput = findViewById<TextInputEditText>(R.id.startDateInput)
        val endDateInput = findViewById<TextInputEditText>(R.id.endDateInput)
        val applyButton = findViewById<Button>(R.id.applyButton)

        startDateInput.setOnClickListener { showDatePicker(startDateInput) }
        endDateInput.setOnClickListener { showDatePicker(endDateInput) }

        applyButton.setOnClickListener {
            val startDate = startDateInput.text.toString()
            val endDate = endDateInput.text.toString()

            if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                onDateRangeSelected(startDate, endDate)
                dismiss()
            } else {
                Toast.makeText(context, "Please select both dates", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDatePicker(target: TextInputEditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(context, { _, year, month, day ->
            val dateStr = String.format("%04d-%02d-%02d", year, month + 1, day)
            target.setText(dateStr)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }
}