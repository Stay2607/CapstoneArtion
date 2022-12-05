package com.dicoding.picodiploma.capstoneartion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityMainBinding
import com.dicoding.picodiploma.capstoneartion.login.LoginActivity
import com.dicoding.picodiploma.capstoneartion.main.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out_menu -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    fun signOut() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}