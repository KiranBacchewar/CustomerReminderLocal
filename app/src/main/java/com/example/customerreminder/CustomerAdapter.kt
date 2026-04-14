package com.example.customerreminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.customerreminder.data.Customer
import com.example.customerreminder.databinding.ItemCustomerBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CustomerAdapter(private val showReminderOnly: Boolean = false) : ListAdapter<Customer, CustomerAdapter.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), showReminderOnly)
    }

    class ViewHolder(private val binding: ItemCustomerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(customer: Customer, showReminderOnly: Boolean) {
            binding.textCustomerName.text = customer.name
            binding.textCustomerDetails.text = "${customer.address} • ${customer.phone}"
            binding.textCustomerPhase.text = customer.phase
            binding.textReminderDate.text = SimpleDateFormat("EEE, MMM d, yyyy h:mm a", Locale.getDefault()).format(customer.reminderMillis)
            binding.textReminderTitle.text = if (showReminderOnly) "Reminder due" else "Reminder"
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Customer>() {
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean = oldItem == newItem
        }
    }
}
