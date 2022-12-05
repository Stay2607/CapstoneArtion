package com.dicoding.picodiploma.capstoneartion.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import androidx.fragment.app.Fragment
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentProfileBinding
import com.dicoding.picodiploma.capstoneartion.login.LoginActivity
import com.dicoding.picodiploma.capstoneartion.setting.SettingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private lateinit var auth: FirebaseAuth
    val user = FirebaseAuth.getInstance()

    private var param1: String? = null
    private var param2: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        btnLogout()
        btnSetting()
        showUser()
    }

    private fun btnSetting() {
        binding?.btnSetting?.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }
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
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun showUser(){
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            val email = user.email
            binding?.profileName?.text = name.toString()
            val photo = user.photoUrl

            Log.d("photo", photo.toString())
            Log.d("name", name.toString())
            if (photo != null){
                binding?.profileAvatar?.let { it1 ->
                    Glide.with(this)
                        .load(photo)
                        .into(it1)
                }
            }

            val emailVerif = user.isEmailVerified

            val uid = user.uid
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return _binding!!.root
        //return inflater.inflate(R.layout.fragment_profile, container, false)
    }

}