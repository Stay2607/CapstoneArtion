package com.dicoding.picodiploma.capstoneartion.detail

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityAuctionItemDetailsBinding
import com.dicoding.picodiploma.capstoneartion.main.HomeActivity
import com.dicoding.picodiploma.capstoneartion.payment.PaymentActivity
import com.dicoding.picodiploma.capstoneartion.utils.Helper.rupiah
import com.dicoding.picodiploma.capstoneartion.utils.Helper.toHour
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuctionItemDetails : AppCompatActivity() {
    private lateinit var binding: ActivityAuctionItemDetailsBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuctionItemDetailsBinding.inflate(layoutInflater)
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
            tvNumberStartBid.text = rupiah(startingBid.toDouble())
            tvNumberBuyOut.text = rupiah(buyOut.toDouble())
            tvNumberPriceNow.text = rupiah(priceNow.toDouble())
            tvTime.text = timeCounter.toInt().toHour()
            Glide.with(this@AuctionItemDetails).load(photo.toUri())
                .into(auctionItemImage)
        }
        binding.btnJoinAuction.apply {
            if (userId == uid) {
                text = getString(R.string.cancel_auction)
                backgroundTintList = getColorStateList(R.color.red_pink)

                setOnClickListener {
                    val dialogBinding = layoutInflater.inflate(R.layout.cancel_auction_dialog, null)
                    val myDialog = Dialog(this@AuctionItemDetails)
                    myDialog.setContentView(dialogBinding)

                    myDialog.setCancelable(true)
                    myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    myDialog.show()

                    val btnYes = dialogBinding.findViewById<Button>(R.id.btn_yes_cancel)
                    val btnNo = dialogBinding.findViewById<Button>(R.id.btn_no_cancel)

                    btnYes.setOnClickListener {
                        item.removeValue()
                        ownerItem.removeValue()

                        val intent = Intent(this@AuctionItemDetails, HomeActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }

                    btnNo.setOnClickListener {
                        myDialog.dismiss()
                    }
                }

            } else {
                setOnClickListener {
                    val dialogBinding = layoutInflater.inflate(R.layout.joined_auction, null)
                    val myDialog = Dialog(this@AuctionItemDetails)
                    myDialog.setContentView(dialogBinding)

                    myDialog.setCancelable(true)
                    myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    myDialog.show()

                    val btnBuyout = dialogBinding.findViewById<Button>(R.id.buyout_button)
                    val btnBid = dialogBinding.findViewById<Button>(R.id.bid_button)
                    val tvBidPrice = dialogBinding.findViewById<TextView>(R.id.tv_bid_price)
                    val tvBuyout = dialogBinding.findViewById<TextView>(R.id.tv_buyout_price)
                    val ivImage = dialogBinding.findViewById<ImageView>(R.id.imageView)

                    tvBidPrice.text = rupiah((priceNow.toInt() + inc.toInt()).toString().toDouble())
                    tvBuyout.text = rupiah(buyOut.toDouble())
                    Glide.with(this).load(photo.toUri()).into(ivImage)

                    btnBuyout.setOnClickListener {
                        val item = ArrayList<String>()
                        item.add(owner)
                        item.add(title)
                        item.add(itemCategory)
                        item.add(buyOut)
                        item.add(photo)
                        item.add(itemId)
                        item.add(uid!!)
                        val intent = Intent(this@AuctionItemDetails, PaymentActivity::class.java)
                        intent.putStringArrayListExtra(PaymentActivity.ITEM, item)
                        startActivity(intent)
                    }

                    btnBid.setOnClickListener {
                        priceNow = (priceNow.toInt() + inc.toInt()).toString()
                        val auctionItem = AuctionItem(
                            itemId,
                            owner,
                            uid!!,
                            title,
                            description,
                            photo,
                            itemCategory,
                            startingBid.toInt(),
                            buyOut.toInt(),
                            priceNow.toInt(),
                            inc.toInt(),
                            timeCounter.toInt(),
                            userId
                        )
                        lifecycleScope.launch {
                            item.setValue(auctionItem)
                            userBid.setValue(auctionItem).addOnSuccessListener {
                                val intent =
                                    Intent(this@AuctionItemDetails, HomeActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }

            }
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
