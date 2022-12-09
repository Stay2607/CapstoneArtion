package com.dicoding.picodiploma.capstoneartion.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentProfileBinding
import com.dicoding.picodiploma.capstoneartion.myauction.MyAuctionAdapter
import com.dicoding.picodiploma.capstoneartion.setting.SettingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private lateinit var auctionAdapter: MyAuctionAdapter
    private lateinit var rvProduct: RecyclerView
    private val list = ArrayList<AuctionItem>()

    private lateinit var auth: FirebaseAuth
    val user = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        btnSetting()
        showUser()
        showRecyclerList()
    }

    private fun btnSetting() {
        binding?.btnSetting?.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showUser() {
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            val email = user.email
            binding?.profileName?.text = name.toString()
            val photo = user.photoUrl

            Log.d("photo", photo.toString())
            Log.d("name", name.toString())
            if (photo != null) {
                binding?.profileAvatar?.let { it1 ->
                    Glide.with(this)
                        .load(photo)
                        .circleCrop()
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
    }

    private val listHeroes: ArrayList<AuctionItem>
        get() {
            val owner = "resources.getStringArray(R.array.data_name)"
            val title = "resources.etStringArray(R.array.data_description)"
            val description = "resources.getStringArray(R.array.data_description)"
            val photoUrl = "resources.ggetStringArray(R.array.data_description)"
            val category = "resources.getStringArray(R.array.data_description)"
            val starting = 123
            val buyout = 123
            val current = 123
            val time = 123
//            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listHero = ArrayList<AuctionItem>()
            val hero = AuctionItem(owner, title, description, photoUrl, category, starting, buyout, current, time)
            listHero.add(hero)
            return listHero
        }

    private fun showRecyclerList() {
        rvProduct = binding!!.recyclerView.findViewById(R.id.recyclerView)
        rvProduct.setHasFixedSize(true)
        list.addAll(listHeroes)
        rvProduct.layoutManager = LinearLayoutManager(context)
        val listHeroAdapter = MyAuctionAdapter(list)
        rvProduct.adapter = listHeroAdapter
    }

}