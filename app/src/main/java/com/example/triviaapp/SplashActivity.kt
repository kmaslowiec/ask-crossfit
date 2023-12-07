package com.example.triviaapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            splashScreen.setKeepOnScreenCondition { true }
            val intent = Intent(
                this@SplashActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }
}
