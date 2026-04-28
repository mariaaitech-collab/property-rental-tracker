package com.example.propertyrentaltracker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "properties")
data class Property(
    @PrimaryKey(autoGenerate = true)
    val propertyId: Int = 0,
    val propertyName: String,
    val address: String,
    val totalUnits: Int = 1,
    val propertyType: String, // Residential / Commercial / Mixed
    val createdDate: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)