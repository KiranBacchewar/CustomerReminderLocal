package com.example.customerreminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.customerreminder.data.AppDatabase
import com.example.customerreminder.data.Customer
import com.example.customerreminder.data.ReminderScheduler
import com.example.customerreminder.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: AppDatabase
    private var reminderMillis: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        setupPhaseSpinner()
        setupReminderPicker()
        binding.buttonSaveCustomer.setOnClickListener { saveCustomer() }
    }

    private fun setupPhaseSpinner() {
        val phases = listOf("Piping", "Complete", "Foundation", "Roofing", "Interior", "Final Inspection")
        binding.spinnerPhase.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, phases).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun setupReminderPicker() {
        binding.buttonSelectReminder.setOnClickListener { showDateTimePicker() }
        reminderMillis = Calendar.getInstance().timeInMillis
        updateReminderText()
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            TimePickerDialog(this, { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                reminderMillis = calendar.timeInMillis
                updateReminderText()
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateReminderText() {
        val format = SimpleDateFormat("EEE, MMM d, yyyy h:mm a", Locale.getDefault())
        binding.textReminderDateTime.text = format.format(reminderMillis)
    }

    private fun saveCustomer() {
        val name = binding.editTextName.text.toString().trim()
        val address = binding.editTextAddress.text.toString().trim()
        val phone = binding.editTextPhone.text.toString().trim()
        val phase = binding.spinnerPhase.selectedItem.toString()

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please enter name, address, and phone number", Toast.LENGTH_SHORT).show()
            return
        }

        val customer = Customer(
            name = name,
            address = address,
            phone = phone,
            reminderMillis = reminderMillis,
            phase = phase
        )

        lifecycleScope.launch {
            val rowId = database.customerDao().insertCustomer(customer)
            ReminderScheduler.schedule(this@RegisterActivity, customer.copy(id = rowId.toInt()))
            runOnUiThread {
                Toast.makeText(this@RegisterActivity, "Customer reminders saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
