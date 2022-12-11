package com.dicoding.picodiploma.capstoneartion.payment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityPaymentBinding
import com.dicoding.picodiploma.capstoneartion.main.HomeActivity
import com.dicoding.picodiploma.capstoneartion.utils.Helper.rupiah
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance()
        auth = Firebase.auth
        val userId = auth.currentUser?.uid

        val items = intent.getStringArrayListExtra(ITEM)
        val title = items?.get(0)
        val artist = items?.get(1)
        val category = items?.get(2)
        val buyout = items?.get(3)
        val photo = items?.get(4)
        val auctionId = items?.get(5)
        val ownerId = items?.get(6)

        binding.apply {
            tvPaymentTitle.text = title
            tvPaymentArtist.text = artist
            tvPaymentCategory.text = category
            tvPaymentBuyoutPriceNumber.text = rupiah(buyout!!.toDouble())
            Glide.with(this@PaymentActivity).load(photo!!.toUri()).into(ivPaymentImageView)

            btnPayNow.setOnClickListener { payNow(auctionId, userId, ownerId) }
        }
    }

    private fun payNow(auctionId: String?, userId: String?, ownerId: String?) {
        db.getReference(TABLE_AUCTION_ITEM).child(auctionId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        db.getReference(TABLE_USER).child(userId!!).child(HISTORY).child(auctionId)
                            .setValue(snapshot.value).addOnCompleteListener {
                            db.getReference(FINISHED).child(auctionId).setValue(snapshot.value)
                                .addOnCompleteListener {
                                    db.getReference(TABLE_USER).child(ownerId!!).child(HISTORY)
                                        .child(auctionId).setValue(snapshot.value)
                                        .addOnCompleteListener {
                                            db.getReference(TABLE_AUCTION_ITEM).child(auctionId)
                                                .removeValue().addOnCompleteListener {
                                                db.getReference(TABLE_USER).child(ownerId)
                                                    .child(AUCTION).child(auctionId).removeValue()
                                                    .addOnCompleteListener {
                                                        val intent = Intent(
                                                            this@PaymentActivity,
                                                            HomeActivity::class.java
                                                        )
                                                        intent.flags =
                                                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                                        startActivity(intent)
                                                        finish()
                                                        Toast.makeText(
                                                            this@PaymentActivity,
                                                            "Payment Success",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                            }
                                        }
                                }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    companion object {
        const val ITEM = "item"
        const val TABLE_AUCTION_ITEM = "AuctionItems"
        const val TABLE_USER = "User"
        const val HISTORY = "History"
        const val FINISHED = "FinishedAuction"
        const val AUCTION = "Auction"
    }
}