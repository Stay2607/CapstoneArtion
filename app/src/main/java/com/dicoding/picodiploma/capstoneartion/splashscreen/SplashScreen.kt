package com.dicoding.picodiploma.capstoneartion.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.capstoneartion.databinding.ActivitySplashScreenBinding
import com.dicoding.picodiploma.capstoneartion.login.LoginActivity
import com.dicoding.picodiploma.capstoneartion.main.HomeActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        checkCurrentUser()

        binding.imageLogo.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun checkCurrentUser() {
        val firebaseUser = Firebase.auth.currentUser

        //Intent to Login or Register
        if (firebaseUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}