package com.example.firebasefirstproject.ui.login1.signUp

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
import com.example.firebasefirstproject.data.state.RegisterState
import com.example.firebasefirstproject.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel:SingUpViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        initListeners()
        observeRegisterState()
        observeMessage()
    }

    private fun observeRegisterState(){
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.registerState.collect{
                    when(it){
                        RegisterState.Idle->{}
                        RegisterState.Loading->{}
                        RegisterState.Success->{
                            Toast.makeText(requireContext(),"register is succesful",Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_signUpFragment_to_dashboardFragment)
                        }
                        is RegisterState.Error->{
                            Snackbar.make(binding.btnSignUp,"Something went wrong: ${it.throwable?.message}",Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
    private fun observeMessage(){
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.message.collect{
                    Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnSignUp.setOnClickListener {
            viewModel.register(binding.etEmail.text.toString().trim(),binding.etPassword.text.toString().trim(),binding.etPasswordAgain.text.toString().trim())
        }
        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }
}