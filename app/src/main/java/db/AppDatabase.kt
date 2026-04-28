package com.example.propertyrentaltracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.propertyrentaltracker.data.db.dao.MaintenanceDao
import com.example.propertyrentaltracker.data.db.dao.PaymentDao
import com.example.propertyrentaltracker.data.db.dao.PropertyDao
import com.example.propertyrentaltracker.data.db.dao.TenantDao
import com.example.propertyrentaltracker.data.db.entity.Maintenance
import com.example.propertyrentaltracker.data.db.entity.Payment
import com.example.propertyrentaltracker.data.db.entity.Property
import com.example.propertyrentaltracker.data.db.entity.Tenant
import com.example.propertyrentaltracker.data.db.entity.UserEntity
import com.example.propertyrentaltracker.data.db.dao.UserDao

@Database(
    entities = [UserEntity::class, Property::class, Tenant::class, Payment::class, Maintenance::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun propertyDao(): PropertyDao
    abstract fun tenantDao(): TenantDao
    abstract fun paymentDao(): PaymentDao
    abstract fun maintenanceDao(): MaintenanceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "property_rental_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}