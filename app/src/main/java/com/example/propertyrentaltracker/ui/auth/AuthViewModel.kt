package com.example.propertyrentaltracker.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propertyrentaltracker.data.db.entity.UserEntity
import com.example.propertyrentaltracker.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<UserEntity?>(null)
    val userState: StateFlow<UserEntity?> = _userState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val firebaseAuth = FirebaseAuth.getInstance()

    init {
        checkUserExists()
    }

    private fun checkUserExists() {
        viewModelScope.launch {
            userRepository.getUser().collect { user ->
                _userState.value = user
                _isLoading.value = false
            }
        }
    }

    fun registerUser(name: String, businessName: String, phone: String, pin: Int, email: String = "") {
        viewModelScope.launch {
            val user = UserEntity(
                name = name,
                businessName = businessName,
                phone = phone,
                pinPassword = pin
                // email = email // Add email to UserEntity if needed
            )
            userRepository.insertUser(user)
        }
    }

    fun verifyPin(pin: Int): Boolean {
        val user = _userState.value
        return user?.pinPassword == pin
    }

    fun logout() {
        viewModelScope.launch {
            // Logic to clear user or just firebase signout
            firebaseAuth.signOut()
        }
    }
}
