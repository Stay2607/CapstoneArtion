package com.dicoding.picodiploma.capstoneartion.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val uid: String,
    val username: String,
    val email: String,
    val password: String?,
    val location: String?,
    val avatar: String?,
)
