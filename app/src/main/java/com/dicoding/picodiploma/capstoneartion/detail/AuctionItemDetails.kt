package com.dicoding.picodiploma.capstoneartion.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityAuctionItemDetailsBinding
import com.google.firebase.database.FirebaseDatabase

class AuctionItemDetails : AppCompatActivity() {
    private lateinit var binding: ActivityAuctionItemDetailsBinding
    private lateinit var db: FirebaseDatabase

    companion object {
        var USERNAME_NAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuctionItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}