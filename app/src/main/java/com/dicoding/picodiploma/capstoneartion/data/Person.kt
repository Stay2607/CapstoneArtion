package com.dicoding.picodiploma.capstoneartion.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Person(
    val username: String,
    val email: String,
    val password: String,
    val isLogin: Boolean
)
