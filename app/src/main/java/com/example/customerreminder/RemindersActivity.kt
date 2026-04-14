package com.example.customerreminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customerreminder.data.AppDatabase
import com.example.customerreminder.databinding.ActivityRemindersBinding
import kotlinx.coroutines.launch

class RemindersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRemindersBinding
    private lateinit var adapter: CustomerAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemindersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        adapter = CustomerAdapter(showReminderOnly = true)

        binding.recyclerViewReminders.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewReminders.adapter = adapter
        binding.buttonSearchReminders.setOnClickListener { loadReminders(binding.editTextSearchReminders.text.toString()) }

        loadReminders("")
    }

    private fun loadReminders(query: String) {
        lifecycleScope.launch {
            val reminders = if (query.isBlank()) {
                database.customerDao().getAllReminders()
            } else {
                database.customerDao().searchReminders("%$query%")
            }
            runOnUiThread { adapter.submitList(reminders) }
        }
    }
}
