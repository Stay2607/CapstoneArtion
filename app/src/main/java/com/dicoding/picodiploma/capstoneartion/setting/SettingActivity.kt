package com.dicoding.picodiploma.capstoneartion.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.capstoneartion.databinding.ActivitySettingBinding
import com.dicoding.picodiploma.capstoneartion.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    val user = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnLogout()
    }

    private fun btnLogout() {
        binding?.buttonLogout?.setOnClickListener {
            Firebase.auth.signOut()

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    Firebase.auth.signOut()
                    user.signOut()
                }
            }
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}