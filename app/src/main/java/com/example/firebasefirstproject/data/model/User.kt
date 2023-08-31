package com.example.firebasefirstproject.data.model

import com.google.firebase.Timestamp

data class User(
    val id: String? = null,
    val email: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val profileImageUrl: String? = null,
    val userRegisterTime:Timestamp = Timestamp.now()
)

fun User.getFullNameOrEmail() =if(!name.isNullOrEmpty() && !surname.isNullOrEmpty()) "$name $surname" else email
