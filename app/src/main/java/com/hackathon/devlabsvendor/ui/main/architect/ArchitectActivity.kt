package com.hackathon.devlabsvendor.ui.main.architect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.hackathon.devlabsvendor.R
import com.hackathon.devlabsvendor.adapter.architect.ArchitectFragmentAdapter
import com.hackathon.devlabsvendor.databinding.ActivityArchitectBinding
import com.hackathon.devlabsvendor.ui.authentication.AuthenticationManager
import com.hackathon.devlabsvendor.viewmodel.ArchitectViewModel

class ArchitectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArchitectBinding
    private lateinit var authenticationManager: AuthenticationManager
    private val architectViewModel: ArchitectViewModel by viewModels()
    private lateinit var viewPagerAdapter: ArchitectFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArchitectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authenticationManager = AuthenticationManager(this)

        val bundle = Bundle()
        val id = intent.getStringExtra("id")
        val profilePicture = intent.getStringExtra("profile_picture")
        val profileName = intent.getStringExtra("profile_name")
        val email = intent.getStringExtra("email")
        val phoneNumber = intent.getStringExtra("phone_number")
        val profileDescription = intent.getStringExtra("profile_description")
        val role = intent.getStringExtra("role")

        bundle.putString("architect_id", id)

        val getToken = authenticationManager.getAccess(AuthenticationManager.TOKEN).toString()
        val token = "Bearer $getToken"

        architectViewModel.ratingsAverage.observe(this, Observer { ratingsAverage ->
            binding.tvUserRatingsAverage.text = "Average Rating : "+ratingsAverage.averageRating
        })

        architectViewModel.getRatingsAverage(token, id!!)

        viewPagerAdapter = ArchitectFragmentAdapter(supportFragmentManager, lifecycle, bundle)
        with(binding) {
            ViewPager.adapter = viewPagerAdapter
            TabLayoutMediator(TabLayout, ViewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "Portfolio"
                    1 -> tab.text = "Ulasan"
                }
            }.attach()
        }

        binding.apply {
            tvUserName.text = profileName
            tvDescription.text = profileDescription
            tvNoTelepon.text = phoneNumber
            Glide.with(this@ArchitectActivity)
                .load(profilePicture)
                .centerCrop()
                .placeholder(R.drawable.contoh_profile)
                .into(ivUserProfile)
        }
    }
}