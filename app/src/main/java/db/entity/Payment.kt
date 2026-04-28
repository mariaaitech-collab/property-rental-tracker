package com.example.propertyrentaltracker.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "payments",
    foreignKeys = [
        ForeignKey(
            entity = Tenant::class,
            parentColumns = ["tenantId"],
            childColumns = ["tenantId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["tenantId"])]
)
data class Payment(
    @PrimaryKey(autoGenerate = true)
    val paymentId: Int = 0,
    val tenantId: Int,
    val amountPaid: Double,
    val paymentDate: Long = System.currentTimeMillis(),
    val monthYear: String, // Format: "04-2025"
    val paymentStatus: String, // Paid / Pending
    val paymentMethod: String, // Cash / Bank / EasyPaisa / JazzCash
    val notes: String = ""
)