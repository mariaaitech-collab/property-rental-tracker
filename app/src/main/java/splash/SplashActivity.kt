package com.example.propertyrentaltracker.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.propertyrentaltracker.MainActivity
import com.example.propertyrentaltracker.R
import com.example.propertyrentaltracker.ui.auth.AuthViewModel
import com.example.propertyrentaltracker.ui.auth.LoginActivity
import com.example.propertyrentaltracker.ui.auth.SignupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            // Wait for both: the minimum splash time and the data loading
            delay(2500) 
            
            viewModel.isLoading.collectLatest { isLoading ->
                if (!isLoading) {
                    val user = viewModel.userState.value
                    if (user == null) {
                        startActivity(Intent(this@SplashActivity, SignupActivity::class.java))
                    } else {
                        // If user exists, we might want to go to Login (PIN) or Main
                        // Based on your preference, usually Main if already logged in, 
                        // but you asked for LoginActivity earlier.
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    }
                    finish()
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
        }
    }
}
