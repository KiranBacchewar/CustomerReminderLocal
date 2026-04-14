package com.example.customerreminder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customerreminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegisterCustomer.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.buttonCustomers.setOnClickListener {
            startActivity(Intent(this, CustomersActivity::class.java))
        }

        binding.buttonReminders.setOnClickListener {
            startActivity(Intent(this, RemindersActivity::class.java))
        }
    }
}
