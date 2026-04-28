package com.example.propertyrentaltracker.di

import android.content.Context
import com.example.propertyrentaltracker.data.db.AppDatabase
import com.example.propertyrentaltracker.data.db.dao.MaintenanceDao
import com.example.propertyrentaltracker.data.db.dao.PaymentDao
import com.example.propertyrentaltracker.data.db.dao.PropertyDao
import com.example.propertyrentaltracker.data.db.dao.TenantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun providePropertyDao(database: AppDatabase): PropertyDao {
        return database.propertyDao()
    }

    @Provides
    fun provideTenantDao(database: AppDatabase): TenantDao {
        return database.tenantDao()
    }

    @Provides
    fun providePaymentDao(database: AppDatabase): PaymentDao {
        return database.paymentDao()
    }

    @Provides
    fun provideMaintenanceDao(database: AppDatabase): MaintenanceDao {
        return database.maintenanceDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): com.example.propertyrentaltracker.data.db.dao.UserDao {
        return database.userDao()
    }
}