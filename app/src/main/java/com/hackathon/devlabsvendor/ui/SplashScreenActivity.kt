package com.hackathon.devlabsvendor.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hackathon.devlabsvendor.R
import com.hackathon.devlabsvendor.ui.authentication.AuthenticationManager
import com.hackathon.devlabsvendor.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var authenticationManager: AuthenticationManager
    private val SPLASH_TIME_OUT: Long = 1000 //1 detik
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        authenticationManager = AuthenticationManager(this)

        Handler().postDelayed({
            if (authenticationManager.checkSession(AuthenticationManager.SESSION) == true) {
                val login = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(login)
                finishAffinity()
            } else {
                startActivity(Intent(this@SplashScreenActivity, OnBoardingActivity::class.java))
                finishAffinity()
            }
        }, SPLASH_TIME_OUT)
    }
}