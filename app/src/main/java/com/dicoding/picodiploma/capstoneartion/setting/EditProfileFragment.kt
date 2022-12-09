package com.dicoding.picodiploma.capstoneartion.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentEditProfileBinding
import com.dicoding.picodiploma.capstoneartion.main.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class EditProfileFragment : Fragment() {
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseDatabase.getInstance()
        auth = Firebase.auth

        binding?.btnSave?.setOnClickListener { updateProfile() }
    }

    private fun updateProfile() {
        val name = binding?.edtName?.text.toString()
        val location = binding?.edtLocation?.text.toString()
        if (name.isNotEmpty() || location.isNotEmpty()) {
            val userId = auth.currentUser?.uid
            db.getReference(TABLE_USER).child(userId!!).let {
                if (name.isNotEmpty()) {
                    it.child("username").setValue(name).addOnSuccessListener {
                        val profileUpdate =
                            UserProfileChangeRequest.Builder().setDisplayName(name).build()
                        auth.currentUser?.updateProfile(profileUpdate)
                        val intent = Intent(activity, HomeActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                }
                if (location.isNotEmpty()) {
                    it.child("location").setValue(location).addOnSuccessListener {
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    companion object {
        private const val TABLE_USER = "User"
    }
}