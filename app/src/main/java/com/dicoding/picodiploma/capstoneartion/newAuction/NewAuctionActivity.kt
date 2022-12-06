package com.dicoding.picodiploma.capstoneartion.newAuction

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityNewAuctionBinding
import com.dicoding.picodiploma.capstoneartion.utils.Helper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NewAuctionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewAuctionBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAuctionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance()
        auth = Firebase.auth
        val user = auth.currentUser

        val itemRef = db.getReference(TABLE_AUCTION_ITEMS)

        binding.createAuctionButton.setOnClickListener {
            val intSelectButton: Int = binding.radioGroup.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)
            val category = radioButton.text.toString()
            val timeCounter: Int = getTimeCounter()
            val item = AuctionItem(
                user?.displayName.toString(),
                binding.etWorkTitle.text.toString(),
                binding.etDescriptionWork.text.toString(),
                binding.frAddPhoto.toString(), //Masih belum sesuai buat foto
                category,
                binding.etStartingPrice.text.toString().toInt(),
                binding.etBuyoutPrice.text.toString().toInt(),
                binding.etStartingPrice.text.toString().toInt(),
                timeCounter
            )
            itemRef.push().setValue(item).addOnSuccessListener {
                binding.etStartingPrice.text.clear()
                binding.etBuyoutPrice.text.clear()
                binding.etStartingPrice.text.clear()
                binding.radioGroup.clearCheck()
                binding.etDescriptionWork.text.clear()
                binding.etWorkTitle.text.clear()
                binding.etAuctionDay.text.clear()
                binding.etAuctionHour.text.clear()

                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun getTimeCounter(): Int {
        val day = binding.etAuctionDay.text.toString().toInt() * 24
        val hour = binding.etAuctionHour.text.toString().toInt()
        return Helper.hourToMinute(day + hour)
    }

    companion object {
        const val TABLE_AUCTION_ITEMS = "AuctionItems"
    }
}