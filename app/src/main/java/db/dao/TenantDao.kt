package com.example.propertyrentaltracker.data.db.dao

import androidx.room.*
import com.example.propertyrentaltracker.data.db.entity.Tenant
import kotlinx.coroutines.flow.Flow

@Dao
interface TenantDao {

    @Query("SELECT * FROM tenants WHERE isActive = 1 ORDER BY tenantName ASC")
    fun getAllTenants(): Flow<List<Tenant>>

    @Query("SELECT * FROM tenants WHERE propertyId = :propertyId AND isActive = 1")
    fun getTenantsByProperty(propertyId: Int): Flow<List<Tenant>>

    @Query("SELECT * FROM tenants WHERE tenantId = :tenantId")
    suspend fun getTenantById(tenantId: Int): Tenant?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTenant(tenant: Tenant): Long

    @Update
    suspend fun updateTenant(tenant: Tenant)

    @Query("UPDATE tenants SET isActive = 0 WHERE tenantId = :tenantId")
    suspend fun deleteTenant(tenantId: Int)

    @Query("SELECT COUNT(*) FROM tenants WHERE isActive = 1")
    fun getTenantCount(): Flow<Int>
}