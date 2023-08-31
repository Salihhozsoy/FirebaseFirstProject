package com.example.firebasefirstproject.ui.login1.forgotPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebasefirstproject.R
import com.example.firebasefirstproject.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    lateinit var binding: FragmentForgotPasswordBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForgotPasswordBinding.bind(view)

        initListeners()

    }

    private fun initListeners() {
        binding.btnResetMyPassword.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }
    }
}