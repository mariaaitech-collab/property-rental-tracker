package com.example.propertyrentaltracker.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val name: String,
    val businessName: String,
    val phone: String,
    val pinPassword: Int,
    val isBiometricEnabled: Boolean = false
)
