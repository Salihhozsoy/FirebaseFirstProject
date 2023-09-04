package com.example.firebasefirstproject.ui.login1.forgotPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.firebasefirstproject.R
import com.example.firebasefirstproject.data.state.ForgotPasswordState
import com.example.firebasefirstproject.databinding.FragmentForgotPasswordBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel:ForgotPasswordViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForgotPasswordBinding.bind(view)

        initListeners()
        observeForgotPasswordState()
    }

    private fun observeForgotPasswordState(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.forgotPasswordState.collect{
                    when(it){
                        ForgotPasswordState.Idle->{}
                        ForgotPasswordState.Empty->{}
                        ForgotPasswordState.Loading->{}
                        ForgotPasswordState.Result->{
                            Toast.makeText(requireContext(),"password reset success",Toast.LENGTH_LONG).show()
                        }
                        is ForgotPasswordState.Error->{}
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.tvRememberPassword.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }
        binding.btnResetMyPassword.setOnClickListener {
            viewModel.resetPassword(binding.etResetMyPassword.text.toString())
        }
    }
}