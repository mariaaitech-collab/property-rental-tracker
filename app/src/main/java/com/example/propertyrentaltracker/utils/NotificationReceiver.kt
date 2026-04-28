package com.example.propertyrentaltracker.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.propertyrentaltracker.R

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        // Get data from intent
        val tenantName = intent.getStringExtra("tenant_name") ?: "Tenant"
        val rentAmount = intent.getDoubleExtra("rent_amount", 0.0)

        // Show notification using NotificationHelper
        val notificationHelper = NotificationHelper(context)
        notificationHelper.showNotification(tenantName, rentAmount)
    }
}