package com.hackathon.devlabsvendor.ui.main.construction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hackathon.devlabsvendor.R
import com.hackathon.devlabsvendor.databinding.ActivityInputDesignOrProgressBinding

class InputDesignOrProgressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputDesignOrProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDesignOrProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}