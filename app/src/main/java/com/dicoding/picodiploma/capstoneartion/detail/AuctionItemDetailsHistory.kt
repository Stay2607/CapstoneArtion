package com.dicoding.picodiploma.capstoneartion.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityAuctionItemDetailsHistoryBinding
import com.dicoding.picodiploma.capstoneartion.utils.Helper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuctionItemDetailsHistory : AppCompatActivity() {
    private lateinit var binding: ActivityAuctionItemDetailsHistoryBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuctionItemDetailsHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        db = FirebaseDatabase.getInstance()
        val userId = auth.currentUser!!.uid
        val itemId = intent.getStringExtra(ITEM_ID)
        val item = db.getReference(TABLE_AUCTION_ITEM).child(itemId!!)
        val historyItem = db.getReference(TABLE_FINISHED_AUCTION).child(itemId)
        val uid = intent.getStringExtra(ITEM_OWNER_ID)
        val ownerItem = db.getReference(TABLE_USER).child(userId).child(AUCTION).child(itemId)
        val userBid = db.getReference(TABLE_USER).child(userId).child(BID).child(itemId)

        lifecycleScope.launch(Dispatchers.IO) {
            item.get().addOnSuccessListener {
                if (it.exists()) {
                    setUi(it)
                } else {
                    historyItem.get().addOnSuccessListener { it2 ->
                        if (it2.exists()) {
                            setUi(it2)
                        }
                    }
                }
            }
        }
    }

    private fun setUi(it: DataSnapshot) {
        val userId = auth.currentUser!!.uid
        val itemId = intent.getStringExtra(ITEM_ID)
        val item = db.getReference(TABLE_AUCTION_ITEM).child(itemId!!)
        val uid = intent.getStringExtra(ITEM_OWNER_ID)
        val ownerItem = db.getReference(TABLE_USER).child(userId).child(AUCTION).child(itemId)
        val userBid = db.getReference(TABLE_USER).child(userId).child(BID).child(itemId)
        val owner = it.child(ITEM_OWNER).value.toString()
        val title = it.child(ITEM_TITLE).value.toString()
        val photo = it.child(ITEM_PHOTO_URL).value.toString()
        val description = it.child(ITEM_DESCRIPTION).value.toString()
        val startingBid = it.child(ITEM_STARTING_PRICE).value.toString()
        val buyOut = it.child(ITEM_BUYOUT_PRICE).value.toString()
        var priceNow = it.child(ITEM_CURRENT_PRICE).value.toString()
        val inc = it.child(ITEM_PRICE_INCREMENT).value.toString()
        val timeCounter = it.child(ITEM_TIME_COUNTER).value.toString()
        val itemCategory = it.child(ITEM_CATEGORY).value.toString()
        var winner = it.child(ITEM_WINNER).value.toString()

        binding.apply {
            tvTitle.text = title
            tvArtist.text = owner
            tvDescription.text = description
            tvNumberPriceFinal.text = Helper.rupiah(priceNow.toDouble())
            Glide.with(this@AuctionItemDetailsHistory).load(photo.toUri())
                .into(auctionItemImage)
        }
    }

    companion object {
        const val TABLE_AUCTION_ITEM = "AuctionItems"
        const val TABLE_USER = "User"
        const val AUCTION = "Auction"
        const val BID = "Bid"
        const val ITEM_ID = "item_id"
        const val ITEM_OWNER_ID = "ownerId"
        const val ITEM_OWNER = "owner"
        const val ITEM_TITLE = "title"
        const val ITEM_DESCRIPTION = "description"
        const val ITEM_PHOTO_URL = "photoUrl"
        const val ITEM_CATEGORY = "category"
        const val ITEM_STARTING_PRICE = "startingPrice"
        const val ITEM_BUYOUT_PRICE = "buyoutPrice"
        const val ITEM_CURRENT_PRICE = "currentPrice"
        const val ITEM_PRICE_INCREMENT = "priceIncrement"
        const val ITEM_TIME_COUNTER = "timeCounter"
        const val ITEM_WINNER = "winner"
        const val TABLE_FINISHED_AUCTION = "FinishedAuction"
    }
}