package com.example.propertyrentaltracker.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "maintenance",
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
data class Maintenance(
    @PrimaryKey(autoGenerate = true)
    val maintenanceId: Int = 0,
    val propertyId: Int,
    val issueDescription: String,
    val cost: Double,
    val date: Long = System.currentTimeMillis(),
    val status: String, // Done / Pending
    val contractorName: String = ""
)