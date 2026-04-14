package com.example.customerreminder.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock

class ReminderScheduler {
    companion object {
        fun schedule(context: Context, customer: Customer) {
            if (customer.reminderMillis <= System.currentTimeMillis()) {
                return
            }

            val intent = Intent(context, com.example.customerreminder.receiver.ReminderReceiver::class.java).apply {
                putExtra("customerName", customer.name)
                putExtra("customerPhase", customer.phase)
                putExtra("customerId", customer.id)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                customer.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, customer.reminderMillis, pendingIntent)
        }
    }
}
