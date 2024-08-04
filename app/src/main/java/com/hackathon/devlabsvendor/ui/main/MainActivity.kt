package com.hackathon.devlabsvendor.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.hackathon.devlabsvendor.R
import com.hackathon.devlabsvendor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.activityMainNavHostFragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        // Check if there's an intent to navigate to a specific fragment
        val navigateTo = intent.getStringExtra("navigate_to")
        if (navigateTo == "ProfileFragment") {
            navController.navigate(R.id.profileFragment)
        }
    }
}