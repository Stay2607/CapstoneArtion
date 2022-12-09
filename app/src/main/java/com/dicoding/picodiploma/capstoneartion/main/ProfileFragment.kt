package com.dicoding.picodiploma.capstoneartion.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentProfileBinding
import com.dicoding.picodiploma.capstoneartion.myauction.MyAuctionAdapter
import com.dicoding.picodiploma.capstoneartion.setting.SettingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private lateinit var auctionAdapter: MyAuctionAdapter
    private lateinit var db: FirebaseDatabase

    private lateinit var auth: FirebaseAuth
    val user = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        db = FirebaseDatabase.getInstance()
        btnSetting()
        showUser()
    }

    private fun btnSetting() {
        binding?.btnSetting?.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showUser() {
        val user = db.getReference("User").child(auth.currentUser!!.uid)
        val name = auth.currentUser!!.displayName.toString()
        binding?.profileName?.text = name
        user.get().addOnSuccessListener {
            if (it.exists()) {
                val location = it.child("location").value.toString()
                binding?.profileRegional?.text = location
                val photo = it.child("avatar").value.toString()
                if (photo.isNotEmpty()) {
                    binding?.profileAvatar?.let { it1 ->
                        Glide.with(this)
                            .load(photo.toUri())
                            .circleCrop()
                            .into(it1)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView(){
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
//        val lisProduct = MyAuctionAdapter()
    }

}