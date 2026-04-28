package com.example.propertyrentaltracker.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tenants",
    foreignKeys = [
        ForeignKey(
            entity = Property::class,
            parentColumns = ["propertyId"],
            childColumns = ["propertyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["propertyId"])]
)
data class Tenant(
    @PrimaryKey(autoGenerate = true)
    val tenantId: Int = 0,
    val propertyId: Int,
    val tenantName: String,
    val phoneNumber: String,
    val cnicNumber: String,
    val unitNumber: String,
    val rentAmount: Double,
    val contractStart: Long,
    val contractEnd: Long,
    val rentDueDay: Int, // Day of month (1-31)
    val isActive: Boolean = true
)