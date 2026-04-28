package com.example.propertyrentaltracker.data.repository

import com.example.propertyrentaltracker.data.db.AppDatabase
import com.example.propertyrentaltracker.data.db.entity.Tenant
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TenantRepository @Inject constructor(private val database: AppDatabase) {

    private val tenantDao = database.tenantDao()

    fun getAllTenants(): Flow<List<Tenant>> = tenantDao.getAllTenants()

    fun getTenantsByProperty(propertyId: Int): Flow<List<Tenant>> = tenantDao.getTenantsByProperty(propertyId)

    suspend fun getTenantById(tenantId: Int): Tenant? = tenantDao.getTenantById(tenantId)

    suspend fun insertTenant(tenant: Tenant): Long = tenantDao.insertTenant(tenant)

    suspend fun updateTenant(tenant: Tenant) = tenantDao.updateTenant(tenant)

    suspend fun deleteTenant(tenantId: Int) = tenantDao.deleteTenant(tenantId)

    fun getTenantCount(): Flow<Int> = tenantDao.getTenantCount()
}
