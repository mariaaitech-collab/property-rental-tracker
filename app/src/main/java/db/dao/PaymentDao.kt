package com.example.propertyrentaltracker.data.db.dao

import androidx.room.*
import com.example.propertyrentaltracker.data.db.entity.Payment
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {

    @Query("SELECT * FROM payments ORDER BY paymentDate DESC")
    fun getAllPayments(): Flow<List<Payment>>

    @Query("SELECT * FROM payments WHERE tenantId = :tenantId ORDER BY paymentDate DESC")
    fun getPaymentsByTenant(tenantId: Int): Flow<List<Payment>>

    @Query("SELECT * FROM payments WHERE monthYear = :monthYear")
    fun getPaymentsByMonth(monthYear: String): Flow<List<Payment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: Payment): Long

    @Update
    suspend fun updatePayment(payment: Payment)

    @Delete
    suspend fun deletePayment(payment: Payment)

    @Query("SELECT SUM(amountPaid) FROM payments WHERE paymentStatus = 'Paid'")
    fun getTotalIncome(): Flow<Double?>

    @Query("SELECT SUM(amountPaid) FROM payments WHERE paymentStatus = 'Pending'")
    fun getTotalPending(): Flow<Double?>
}