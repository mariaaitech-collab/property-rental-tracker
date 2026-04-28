package com.example.propertyrentaltracker.ui.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertyrentaltracker.data.db.entity.Payment
import com.example.propertyrentaltracker.data.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    private val _payments = MutableStateFlow<List<Payment>>(emptyList())
    val payments: StateFlow<List<Payment>> = _payments.asStateFlow()

    init {
        loadPayments()
    }

    private fun loadPayments() {
        viewModelScope.launch {
            paymentRepository.getAllPayments().collect {
                _payments.value = it
            }
        }
    }

    fun addPayment(
        tenantId: Int,
        amount: Double,
        monthYear: String,
        status: String,
        method: String,
        notes: String
    ) {
        viewModelScope.launch {
            val payment = Payment(
                tenantId = tenantId,
                amountPaid = amount,
                monthYear = monthYear,
                paymentStatus = status,
                paymentMethod = method,
                notes = notes
            )
            paymentRepository.insertPayment(payment)
        }
    }
}
