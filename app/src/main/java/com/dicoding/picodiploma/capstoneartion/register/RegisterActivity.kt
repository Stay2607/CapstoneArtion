package com.dicoding.picodiploma.capstoneartion.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.data.User
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityRegisterBinding
import com.dicoding.picodiploma.capstoneartion.login.LoginActivity
import com.dicoding.picodiploma.capstoneartion.main.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        db = FirebaseDatabase.getInstance()

        setContentView(binding.root)
        setupView()
        btnLogin()

        auth = Firebase.auth

        creteAccount()
    }

    private fun creteAccount() {
        binding.button.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            when {
                username.isEmpty() -> binding.edtUsername.error = getString(R.string.field_required)
                email.isEmpty() -> binding.edtEmail.error = getString(R.string.field_required)
                password.isEmpty() -> binding.edtPassword.error = getString(R.string.field_required)
                else -> {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "createUserWithEmail:success")
                                val user = auth.currentUser
                                val profileUpdates =
                                    UserProfileChangeRequest.Builder().setDisplayName(username)
                                        .build()
                                user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                                    updateUI(user)
                                }
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(
                                    baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                updateUI(null)
                            }
                        }
                }
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val usr = auth.currentUser!!
            val itemRef = db.getReference(TABLE_USER).child(usr.uid)
            val user = User(
                usr.uid,
                usr.displayName.toString(),
                usr.email.toString(),
                binding.edtPassword.text.toString(),
                "",
                usr.photoUrl.toString()
            )
            itemRef.setValue(user)
            startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
            finish()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun btnLogin() {
        binding.loginHere.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    companion object {
        private const val TAG = "EmailPassword"
        private const val TABLE_USER = "User"
    }
}