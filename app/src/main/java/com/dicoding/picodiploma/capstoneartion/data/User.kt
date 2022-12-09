package com.dicoding.picodiploma.capstoneartion.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val username: String,
    val email: String,
    val password: String?,
    val location: String?,
    val isLogin: Boolean
)
