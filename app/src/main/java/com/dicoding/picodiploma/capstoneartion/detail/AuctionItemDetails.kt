package com.dicoding.picodiploma.capstoneartion.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityAuctionItemDetailsBinding
import com.dicoding.picodiploma.capstoneartion.utils.Helper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AuctionItemDetails : AppCompatActivity() {
    private lateinit var binding: ActivityAuctionItemDetailsBinding
    private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuctionItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance()
        val itemId = intent.getStringExtra(ITEM_ID)
        val item = db.getReference(TABLE_AUCTION_ITEM).child(itemId!!)

        item.get().addOnSuccessListener {
            if (it.exists()) {
                val owner = it.child(ITEM_OWNER).value.toString()
                val title = it.child(ITEM_TITLE).value.toString()
                val photo = it.child(ITEM_PHOTO_URL).value.toString()
                val description = it.child(ITEM_DESCRIPTION).value.toString()
                val startingBid = it.child(ITEM_STARTING_PRICE).value.toString()
                val buyOut = it.child(ITEM_BUYOUT_PRICE).value.toString()
                val priceNow = it.child(ITEM_CURRENT_PRICE).value.toString()
                val timeCounter = it.child(ITEM_TIME_COUNTER).value.toString()

                binding.apply {
                    tvTitle.text = title
                    tvArtist.text = owner
                    tvDescription.text = description
                    tvNumberStartBid.text = startingBid
                    tvNumberBuyOut.text = buyOut
                    tvNumberPriceNow.text = priceNow
                    tvTime.text = timeCounter
                    Glide.with(this@AuctionItemDetails).load(photo.toUri()).into(auctionItemImage)

                }
            }
        }
    }

    companion object {
        const val TABLE_AUCTION_ITEM = "AuctionItems"
        const val ITEM_ID = "item_id"
        const val  ITEM_OWNER = "owner"
        const val  ITEM_OWNER_ID = "ownerId"
        const val  ITEM_TITLE = "title"
        const val  ITEM_DESCRIPTION = "description"
        const val  ITEM_PHOTO_URL = "photoUrl"
        const val  ITEM_CATEGORY = "category"
        const val  ITEM_STARTING_PRICE = "startingPrice"
        const val  ITEM_BUYOUT_PRICE = "buyoutPrice"
        const val  ITEM_CURRENT_PRICE = "currentPrice"
        const val  ITEM_PRICE_INCREMENT = "priceIncrement"
        const val  ITEM_TIME_COUNTER = "timeCounter"
        const val  ITEM_WINNER = "winner"
    }
}
