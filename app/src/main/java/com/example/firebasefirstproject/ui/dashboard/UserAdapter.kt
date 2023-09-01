package com.example.firebasefirstproject.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.firebasefirstproject.data.model.User
import com.example.firebasefirstproject.data.model.getFullNameOrEmail
import com.example.firebasefirstproject.databinding.UserListItemBinding


class UserAdapter(
    private val context: Context,
    private val users: List<User>,
    val onClick: (user: User) -> Unit
) : RecyclerView.Adapter<UserAdapter.CustomViewHolder>() {

    class CustomViewHolder(binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvUserFullName = binding.tvUserFullName
        val tvUserRegisterTime = binding.tvUserRegisterTime
        val ivUserImage =binding.ivUserImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            UserListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder:CustomViewHolder, position: Int) {
        val user = users[position]
        holder.tvUserFullName.text = user.getFullNameOrEmail()
        holder.tvUserRegisterTime.text=user.userRegisterTime.toString()
        holder.ivUserImage.load(user.profileImageUrl)


        holder.itemView.setOnClickListener {
            onClick(user)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}