package com.example.propertyrentaltracker.ui.properties

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertyrentaltracker.data.db.entity.Property
import com.example.propertyrentaltracker.data.repository.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    private val _properties = MutableStateFlow<List<Property>>(emptyList())
    val properties: StateFlow<List<Property>> = _properties.asStateFlow()

    init {
        loadProperties()
    }

    private fun loadProperties() {
        viewModelScope.launch {
            propertyRepository.getAllProperties().collect {
                _properties.value = it
            }
        }
    }

    fun addProperty(name: String, address: String, units: Int, type: String) {
        viewModelScope.launch {
            val property = Property(
                propertyName = name,
                address = address,
                totalUnits = units,
                propertyType = type
            )
            propertyRepository.insertProperty(property)
        }
    }
}
