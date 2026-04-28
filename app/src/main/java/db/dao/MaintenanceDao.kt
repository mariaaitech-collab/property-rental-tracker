package com.example.propertyrentaltracker.data.db.dao

import androidx.room.*
import com.example.propertyrentaltracker.data.db.entity.Maintenance
import kotlinx.coroutines.flow.Flow

@Dao
interface MaintenanceDao {

    @Query("SELECT * FROM maintenance ORDER BY date DESC")
    fun getAllMaintenance(): Flow<List<Maintenance>>

    @Query("SELECT * FROM maintenance WHERE propertyId = :propertyId ORDER BY date DESC")
    fun getMaintenanceByProperty(propertyId: Int): Flow<List<Maintenance>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaintenance(maintenance: Maintenance): Long

    @Update
    suspend fun updateMaintenance(maintenance: Maintenance)

    @Delete
    suspend fun deleteMaintenance(maintenance: Maintenance)

    @Query("SELECT SUM(cost) FROM maintenance")
    fun getTotalMaintenanceCost(): Flow<Double?>
}