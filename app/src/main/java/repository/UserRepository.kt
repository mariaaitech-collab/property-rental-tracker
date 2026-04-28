package com.example.propertyrentaltracker.data.repository

import com.example.propertyrentaltracker.data.db.AppDatabase
import com.example.propertyrentaltracker.data.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val database: AppDatabase) {

    private val userDao = database.userDao()

    fun getUser(): Flow<UserEntity?> = userDao.getUser()

    suspend fun getUserSync(): UserEntity? = userDao.getUserSync()

    suspend fun insertUser(user: UserEntity): Long = userDao.insertUser(user)

    suspend fun updateUser(user: UserEntity) = userDao.updateUser(user)
}
