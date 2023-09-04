package com.example.firebasefirstproject.ui.allnewsfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasefirstproject.News
import com.example.firebasefirstproject.databinding.NewsListItemBinding

class AllNewsAdapter(private val context: Context, private val news: List<News>):RecyclerView.Adapter<AllNewsAdapter.CustomViewHolder>() {

    class CustomViewHolder(binding:NewsListItemBinding) :RecyclerView.ViewHolder(binding.root) {
        val tvNewsTitle =binding.tvNewsTitle
        val tvNewsContent =binding.tvNewsContent
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val binding =NewsListItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {
        val news =news[position]
        holder.tvNewsTitle.text = news.title
        holder.tvNewsContent.text =news.content
    }

    override fun getItemCount(): Int {
        return news.size
    }
}