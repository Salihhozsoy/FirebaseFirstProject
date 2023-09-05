package com.example.firebasefirstproject.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.firebasefirstproject.R
import com.example.firebasefirstproject.data.model.getFullNameOrEmail
import com.example.firebasefirstproject.data.state.ProfilePhotoUpdateState
import com.example.firebasefirstproject.data.state.UserProfileState
import com.example.firebasefirstproject.databinding.FragmentUserProfileBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private lateinit var binding: FragmentUserProfileBinding
    private val viewModel: UserProfileViewModel by activityViewModels()

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.ivUserProfileImage.load(uri)
                viewModel.uploadProfileImage(uri)
            } else {
                // no media selected
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserProfileBinding.bind(view)

        observeUserProfileState()
        observeProfilePhotoUpdateState()
        initListeners()
        viewModel.getUser()
    }

    private fun observeUserProfileState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.userProfileState.collect {
                    when (it) {
                        UserProfileState.Idle -> {}
                        UserProfileState.Loading -> {}
                        is UserProfileState.Result -> {
                            binding.tvUserName.text = it.user.getFullNameOrEmail()
                            binding.ivUserProfileImage.load(it.user.profileImageUrl)
                        }
                        is UserProfileState.Error -> {}
                    }
                }
            }
        }
    }

    private fun observeProfilePhotoUpdateState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.profilePhotoUpdateState.collect {
                    binding.pbImageUpload.isVisible = it is ProfilePhotoUpdateState.Progress
                    when (it) {
                        ProfilePhotoUpdateState.Idle -> {}
                        ProfilePhotoUpdateState.Loading -> {}
                        is ProfilePhotoUpdateState.Progress -> {
                            binding.pbImageUpload.progress = it.pr
                        }
                        ProfilePhotoUpdateState.Success -> {
                            Snackbar.make(binding.tvUserName, "image uploaded", Snackbar.LENGTH_LONG).show()
                        }
                        is ProfilePhotoUpdateState.Error -> {
                            Snackbar.make(binding.tvUserName, "something went wrong", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.ivUserProfileImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnUpdate.setOnClickListener {
            viewModel.updateProfile(binding.tvUserName.text.toString().trim(), binding.tvUserSurname.text.toString().trim())
        }
    }
}