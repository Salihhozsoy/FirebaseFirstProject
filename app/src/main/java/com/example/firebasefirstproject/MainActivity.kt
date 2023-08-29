package com.example.firebasefirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.firebasefirstproject.databinding.ActivityMainBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                            val tvNews = TextView(this)
                            tvNews.text = "${news.title} ${news.content}"
                            binding.llNews.addView(tvNews)
                        }
                        DocumentChange.Type.REMOVED -> {}
                        DocumentChange.Type.MODIFIED -> {}
                    }
                }
            }
        }
    }
}