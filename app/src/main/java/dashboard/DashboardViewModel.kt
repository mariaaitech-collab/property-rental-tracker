package com.example.propertyrentaltracker.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertyrentaltracker.data.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.example.propertyrentaltracker.data.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val database: AppDatabase
) : ViewModel() {

    init {
        loadDashboardStats()
    }

    // LiveData for total properties count
    private val _totalProperties = MutableLiveData<Int>()
    val totalProperties: LiveData<Int> = _totalProperties

    // LiveData for total tenants count
    private val _totalTenants = MutableLiveData<Int>()
    val totalTenants: LiveData<Int> = _totalTenants

    // LiveData for total income
    private val _totalIncome = MutableLiveData<Double>()
    val totalIncome: LiveData<Double> = _totalIncome

    // LiveData for pending payments count
    private val _pendingPayments = MutableLiveData<Int>()
    val pendingPayments: LiveData<Int> = _pendingPayments

    // Function to load dashboard statistics
    fun loadDashboardStats() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Get total properties count
                database.propertyDao().getPropertyCount().collect { count ->
                    _totalProperties.postValue(count)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        viewModelScope.launch(Dispatchers.IO) {
             try {
                // Get total tenants count
                database.tenantDao().getTenantCount().collect { count ->
                    _totalTenants.postValue(count)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Get total income
                database.paymentDao().getTotalIncome().collect { income ->
                    _totalIncome.postValue(income ?: 0.0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Get pending payments count
                database.paymentDao().getTotalPending().collect { pending ->
                    _pendingPayments.postValue(pending?.toInt() ?: 0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Refresh data manually
    fun refreshData() {
        loadDashboardStats()
    }

    override fun onCleared() {
        super.onCleared()
        // Cleanup if needed
    }
}