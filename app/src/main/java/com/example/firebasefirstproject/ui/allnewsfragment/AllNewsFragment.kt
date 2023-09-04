package com.example.firebasefirstproject.ui.allnewsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.firebasefirstproject.Constants
import com.example.firebasefirstproject.R
import com.example.firebasefirstproject.data.state.AllNewsState
import com.example.firebasefirstproject.databinding.FragmentAllNewsBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllNewsFragment : Fragment(R.layout.fragment_all_news) {

    private lateinit var binding: FragmentAllNewsBinding
    private val viewModel: AllNewsFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllNewsBinding.bind(view)

        val userId = arguments?.getString(Constants.EDITOR_ID, "")

        observeAllNewsState()
        viewModel.getAllNews(userId)
    }

    private fun observeAllNewsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.allNewsState.collect {
                    when (it) {
                        AllNewsState.Idle -> {}
                        AllNewsState.Empty -> {}
                        AllNewsState.Loading -> {}
                        is AllNewsState.Result -> {
                            binding.rvNews.adapter = AllNewsAdapter(requireContext(), it.news)
                        }

                        is AllNewsState.Error -> {}
                    }
                }
            }
        }
    }
}