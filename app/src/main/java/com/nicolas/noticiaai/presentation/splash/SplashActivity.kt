package com.nicolas.noticiaai.presentation.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nicolas.noticiaai.R
import com.nicolas.noticiaai.MainActivity
import android.content.Intent
import android.os.Handler
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },SPLASH_TIME_OUT)
    }

    companion object{
        const val SPLASH_TIME_OUT = 3000L
    }
}