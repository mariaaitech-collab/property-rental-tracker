package com.example.propertyrentaltracker.ui.tenants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertyrentaltracker.data.db.entity.Tenant
import com.example.propertyrentaltracker.data.repository.TenantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TenantViewModel @Inject constructor(
    private val tenantRepository: TenantRepository
) : ViewModel() {

    private val _tenants = MutableStateFlow<List<Tenant>>(emptyList())
    val tenants: StateFlow<List<Tenant>> = _tenants.asStateFlow()

    init {
        loadTenants()
    }

    private fun loadTenants() {
        viewModelScope.launch {
            tenantRepository.getAllTenants().collect {
                _tenants.value = it
            }
        }
    }

    fun addTenant(
        propertyId: Int,
        name: String,
        phone: String,
        cnic: String,
        unit: String,
        rent: Double,
        dueDay: Int
    ) {
        viewModelScope.launch {
            val tenant = Tenant(
                propertyId = propertyId,
                tenantName = name,
                phoneNumber = phone,
                cnicNumber = cnic,
                unitNumber = unit,
                rentAmount = rent,
                contractStart = System.currentTimeMillis(),
                contractEnd = System.currentTimeMillis() + 31536000000L, // 1 year default
                rentDueDay = dueDay
            )
            tenantRepository.insertTenant(tenant)
        }
    }
}
