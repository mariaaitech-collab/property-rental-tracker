package com.example.propertyrentaltracker.data.db.dao

import androidx.room.*
import com.example.propertyrentaltracker.data.db.entity.Property
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {

    @Query("SELECT * FROM properties WHERE isActive = 1 ORDER BY createdDate DESC")
    fun getAllProperties(): Flow<List<Property>>

    @Query("SELECT * FROM properties WHERE propertyId = :propertyId")
    suspend fun getPropertyById(propertyId: Int): Property?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(property: Property): Long

    @Update
    suspend fun updateProperty(property: Property)

    @Query("UPDATE properties SET isActive = 0 WHERE propertyId = :propertyId")
    suspend fun deleteProperty(propertyId: Int)

    @Query("SELECT COUNT(*) FROM properties WHERE isActive = 1")
    fun getPropertyCount(): Flow<Int>
}