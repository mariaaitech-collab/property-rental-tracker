package com.example.propertyrentaltracker.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.propertyrentaltracker.MainActivity
import com.example.propertyrentaltracker.databinding.ActivitySignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            val name = binding.etFullName.text.toString().trim()
            val businessName = binding.etBusinessName.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val pinStr = binding.etPassword.text.toString().trim()

            if (name.isEmpty() || businessName.isEmpty() || phone.isEmpty() || pinStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pinStr.length != 4) {
                Toast.makeText(this, "PIN must be 4 digits", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.registerUser(name, businessName, phone, pinStr.toInt())
            Toast.makeText(this, "Profile Created Successfully", Toast.LENGTH_SHORT).show()
            
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
}