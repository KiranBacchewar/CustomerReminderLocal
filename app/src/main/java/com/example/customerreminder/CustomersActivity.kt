package com.example.customerreminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customerreminder.data.AppDatabase
import com.example.customerreminder.databinding.ActivityCustomersBinding
import kotlinx.coroutines.launch

class CustomersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomersBinding
    private lateinit var adapter: CustomerAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        adapter = CustomerAdapter()

        binding.recyclerViewCustomers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCustomers.adapter = adapter
        binding.buttonSearchCustomers.setOnClickListener { loadCustomers(binding.editTextSearchCustomers.text.toString()) }

        loadCustomers("")
    }

    private fun loadCustomers(query: String) {
        lifecycleScope.launch {
            val customers = if (query.isBlank()) {
                database.customerDao().getAllCustomers()
            } else {
                database.customerDao().searchCustomers("%$query%")
            }
            runOnUiThread { adapter.submitList(customers) }
        }
    }
}
