package com.example.firebasefirstproject.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.firebasefirstproject.R
import com.example.firebasefirstproject.data.model.getFullNameOrEmail
import com.example.firebasefirstproject.data.state.UserProfileState
import com.example.firebasefirstproject.databinding.FragmentUserProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private lateinit var binding: FragmentUserProfileBinding
    private val viewModel:UserProfileViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserProfileBinding.bind(view)

        observeUserProfileState()
        viewModel.getUser()
    }

    private fun observeUserProfileState(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.userProfileState.collect{
                    when(it){
                        UserProfileState.Idle->{}
                        UserProfileState.Loading->{}
                        is UserProfileState.Result->{
                            binding.tvUserName.text=it.user.getFullNameOrEmail()
                            binding.ivUserProfileImage.load(it.user.profileImageUrl)
                        }
                        is UserProfileState.Error->{}
                    }
                }
            }
        }
    }
}