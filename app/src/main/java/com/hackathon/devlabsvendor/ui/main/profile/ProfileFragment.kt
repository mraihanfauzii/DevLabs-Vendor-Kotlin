package com.hackathon.devlabsvendor.ui.main.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hackathon.devlabsvendor.R
import com.hackathon.devlabsvendor.databinding.FragmentProfileBinding
import com.hackathon.devlabsvendor.ui.authentication.AuthenticationManager
import com.hackathon.devlabsvendor.ui.authentication.LoginActivity
import com.hackathon.devlabsvendor.ui.main.profile.favorite.FavoriteActivity
import com.hackathon.devlabsvendor.utils.Setting
import com.hackathon.devlabsvendor.viewmodel.AuthenticationViewModel
import com.hackathon.devlabsvendor.viewmodel.SettingViewModel


class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var authenticationViewModel: AuthenticationViewModel
    private lateinit var viewModel: SettingViewModel
    private lateinit var authenticationManager: AuthenticationManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authenticationManager = AuthenticationManager(requireContext())
        authenticationViewModel = ViewModelProvider(this)[AuthenticationViewModel::class.java]

        authenticationViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            showLoading(loading)
        }
        authenticationViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel = ViewModelProvider(this, SettingViewModel.Factory(Setting(requireContext())))[SettingViewModel::class.java]

        val getToken = authenticationManager.getAccess(AuthenticationManager.TOKEN).toString()
        val token = "Bearer $getToken"
        val email = authenticationManager.getAccess(AuthenticationManager.EMAIL).toString()
        val name = authenticationManager.getAccess(AuthenticationManager.NAME).toString()
        val phoneNumber = authenticationManager.getAccess(AuthenticationManager.PHONE_NUMBER).toString()
        val profileDescription = authenticationManager.getAccess(AuthenticationManager.PROFILE_DESCRIPTION).toString()
        val profilePicture = "https://www.bumpy-insects-reply-yearly.a276.dcdg.xyz"+authenticationManager.getAccess(AuthenticationManager.PROFILE_PICTURE).toString()

        binding.apply {
            tvEmail.text = email
            tvName.text = name
            tvPhoneNumber.text = phoneNumber
            tvDescription.text = profileDescription
            btnFavorit.setOnClickListener {
                val intent = Intent(context, FavoriteActivity::class.java)
                startActivity(intent)
            }
            if (profilePicture != null) {
                Glide.with(this@ProfileFragment)
                    .load(profilePicture)
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .into(ivProfileImage) // sesuaikan dengan ID ImageView Anda
            }
            btnEditProfil.setOnClickListener {
                val editProfil = Intent(requireContext(), EditProfileActivity::class.java)
                editProfil.putExtra("nama_lengkap", name)
                editProfil.putExtra("nomor_telepon", phoneNumber)
                editProfil.putExtra("bio", profileDescription)
                editProfil.putExtra("profile", profilePicture)
                startActivity(editProfil)
            }
            btnLogout.setOnClickListener {
                authenticationViewModel.logout(token)
                authenticationViewModel.logoutResponse.observe(viewLifecycleOwner) {
                    if(it.code == 200) {
                        authenticationManager.setSession(AuthenticationManager.SESSION, false)
                        authenticationManager.logOut()
                        val logout = Intent(requireContext(), LoginActivity::class.java)
                        Toast.makeText(context, "Successfully logout", Toast.LENGTH_SHORT).show()
                        startActivity(logout)
                        requireActivity().finishAffinity()
                    }
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}