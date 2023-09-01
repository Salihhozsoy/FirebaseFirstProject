package com.example.firebasefirstproject.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.firebasefirstproject.R
import com.example.firebasefirstproject.data.model.User
import com.example.firebasefirstproject.data.model.getFullNameOrEmail
import com.example.firebasefirstproject.data.state.UserListState
import com.example.firebasefirstproject.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)

        observeUserListState()
        viewModel.getUsers()
        initListeners()
    }

    private fun observeUserListState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.userListState.collect {
                    binding.swipeLayout.isRefreshing = false
                    when (it) {
                        UserListState.Idle -> {}
                        UserListState.Empty -> {}
                        UserListState.Loading -> {}
                        is UserListState.Result -> {
                            binding.rvUsers.adapter = UserAdapter(requireContext(), it.users, this@DashboardFragment::onUserAdapterOnClick)
                        }
                        is UserListState.Error -> {}
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.getUsers()
        }
        binding.btnGecis.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_addNewsFragment)
        }
    }
    private fun onUserAdapterOnClick(user: User) {}
}