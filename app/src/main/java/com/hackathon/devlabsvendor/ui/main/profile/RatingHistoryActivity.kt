package com.hackathon.devlabsvendor.ui.main.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hackathon.devlabsvendor.R
import com.hackathon.devlabsvendor.databinding.ActivityRatingHistoryBinding

class RatingHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRatingHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}