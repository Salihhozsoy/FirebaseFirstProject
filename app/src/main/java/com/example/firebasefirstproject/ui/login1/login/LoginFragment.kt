package com.example.firebasefirstproject.ui.login1.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.firebasefirstproject.R
import com.example.firebasefirstproject.data.state.AuthState
import com.example.firebasefirstproject.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        initListeners()
        observeLoginState()
    }

    private fun observeLoginState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.loginState.collect {
                    stateForViews(it is AuthState.Loading)
                    when (it) {
                        AuthState.Idle -> {}
                        AuthState.Loading -> {}
                        AuthState.UserNotFound -> {
                            Snackbar.make(binding.btnLogin, "User Not Found", Snackbar.LENGTH_LONG).setAction("Signup"){
                                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
                            }.show()
                        }
                        AuthState.Success -> {
                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                        }
                        is AuthState.Error -> {
                            Snackbar.make(binding.btnLogin, "Something went wrong", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun stateForViews(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.btnLogin.isVisible = !isLoading
        binding.etEmail.isEnabled = !isLoading
        binding.etPassword.isEnabled = !isLoading
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etEmail.text.toString().trim(), binding.etPassword.text.toString().trim())
        }
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }
}