package com.example.firebasefirstproject.ui.addNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.firebasefirstproject.Constants
import com.example.firebasefirstproject.News
import com.example.firebasefirstproject.R
import com.example.firebasefirstproject.databinding.FragmentAddNewsBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddNewsFragment : Fragment(R.layout.fragment_add_news) {

    lateinit var binding: FragmentAddNewsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNewsBinding.bind(view)
        val db = Firebase.firestore

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()

            val reference = db.collection(Constants.NEWS).document()
            val news = News(id = reference.id, title = title, content = content)
            db.collection(Constants.NEWS).add(news).addOnSuccessListener {  //1. yol
                it.update(mapOf("id" to it.id))
            }
            //  db.collection(Constants.NEWS).document(reference.id).set(news)   //2.yol
        }
        db.collection(Constants.NEWS).get().addOnCompleteListener {
            if (it.isSuccessful) {
                it.result.documents.forEach {
                    println(it)
                }
            } else {
                //toast
            }
        }

        db.collection(Constants.NEWS).addSnapshotListener { value, error ->
            if (error == null) {
                value?.documentChanges?.forEach {
                    val news = it.document.toObject(News::class.java)
                    when (it.type) {
                        DocumentChange.Type.ADDED -> {
                            //  val tvNews = TextView(this)
                            //   tvNews.text = "${news.title} ${news.content}"
                            //   binding.llNews.addView(tvNews)
                        }

                        DocumentChange.Type.REMOVED -> {}
                        DocumentChange.Type.MODIFIED -> {}
                    }
                }
            }
        }
    }

}

