package com.example.propertyrentaltracker.data.repository

import com.example.propertyrentaltracker.data.db.AppDatabase
import com.example.propertyrentaltracker.data.db.entity.Payment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRepository @Inject constructor(private val database: AppDatabase) {

    private val paymentDao = database.paymentDao()

    fun getAllPayments(): Flow<List<Payment>> = paymentDao.getAllPayments()

    fun getPaymentsByTenant(tenantId: Int): Flow<List<Payment>> = paymentDao.getPaymentsByTenant(tenantId)

    suspend fun insertPayment(payment: Payment): Long = paymentDao.insertPayment(payment)

    suspend fun updatePayment(payment: Payment) = paymentDao.updatePayment(payment)

    fun getTotalIncome(): Flow<Double?> = paymentDao.getTotalIncome()

    fun getTotalPending(): Flow<Double?> = paymentDao.getTotalPending()
}
