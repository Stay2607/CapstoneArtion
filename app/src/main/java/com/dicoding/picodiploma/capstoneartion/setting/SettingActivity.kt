package com.dicoding.picodiploma.capstoneartion.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.databinding.ActivitySettingBinding
import com.dicoding.picodiploma.capstoneartion.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySettingBinding
    private val user = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogout.setOnClickListener(this)
        binding.btnEditProfile.setOnClickListener(this)
        binding.btnLanguage.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonLogout -> {
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
            R.id.btn_edit_profile -> setFragment(EditProfileFragment())
            R.id.btn_language -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun setFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().replace(R.id.setting_frame, fragment, "fragment").addToBackStack("fragment").commit()
}