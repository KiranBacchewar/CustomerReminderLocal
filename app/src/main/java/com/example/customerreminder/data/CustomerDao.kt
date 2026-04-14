package com.example.customerreminder.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CustomerDao {
    @Insert
    suspend fun insertCustomer(customer: Customer): Long

    @Query("SELECT * FROM customers ORDER BY name COLLATE NOCASE ASC")
    suspend fun getAllCustomers(): List<Customer>

    @Query("SELECT * FROM customers WHERE name LIKE :query OR address LIKE :query OR phone LIKE :query OR phase LIKE :query ORDER BY name COLLATE NOCASE ASC")
    suspend fun searchCustomers(query: String): List<Customer>

    @Query("SELECT * FROM customers ORDER BY reminderMillis ASC")
    suspend fun getAllReminders(): List<Customer>

    @Query("SELECT * FROM customers WHERE name LIKE :query OR address LIKE :query OR phone LIKE :query OR phase LIKE :query ORDER BY reminderMillis ASC")
    suspend fun searchReminders(query: String): List<Customer>
}
