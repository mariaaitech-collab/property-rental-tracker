package com.example.propertyrentaltracker.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.propertyrentaltracker.MainActivity
import com.example.propertyrentaltracker.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val pinStr = binding.etPassword.text.toString().trim()
            if (pinStr.length != 4) {
                Toast.makeText(this, "Enter 4-digit PIN", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.verifyPin(pinStr.toInt())) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid PIN", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userState.collect { user ->
                    if (user == null && !viewModel.isLoading.value) {
                        // If no user exists, they should sign up first
                        binding.tvSignup.visibility = android.view.View.VISIBLE
                    } else if (user != null) {
                        binding.tvWelcome.text = "Welcome Back, ${user.name}"
                        binding.tvSignup.visibility = android.view.View.GONE
                    }
                }
            }
        }
    }
}