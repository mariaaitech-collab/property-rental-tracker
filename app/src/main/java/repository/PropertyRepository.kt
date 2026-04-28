package com.example.propertyrentaltracker.data.repository

import com.example.propertyrentaltracker.data.db.AppDatabase
import com.example.propertyrentaltracker.data.db.entity.Property
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropertyRepository @Inject constructor(private val database: AppDatabase) {

    private val propertyDao = database.propertyDao()

    fun getAllProperties(): Flow<List<Property>> = propertyDao.getAllProperties()

    suspend fun getPropertyById(propertyId: Int): Property? = propertyDao.getPropertyById(propertyId)

    suspend fun insertProperty(property: Property): Long = propertyDao.insertProperty(property)

    suspend fun updateProperty(property: Property) = propertyDao.updateProperty(property)

    suspend fun deleteProperty(propertyId: Int) = propertyDao.deleteProperty(propertyId)

    fun getPropertyCount(): Flow<Int> = propertyDao.getPropertyCount()
}