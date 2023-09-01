package com.example.firebasefirstproject.ui.addNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.firebasefirstproject.Constants
import com.example.firebasefirstproject.News
import com.example.firebasefirstproject.R
import com.example.firebasefirstproject.databinding.FragmentAddNewsBinding
import com.example.firebasefirstproject.ui.dashboard.DashboardFragment
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddNewsFragment : Fragment(R.layout.fragment_add_news) {

    lateinit var binding: FragmentAddNewsBinding
    private val viewModel: AddNewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNewsBinding.bind(view)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()
            viewModel.addNews(title, content)
        }
    }
}
