package com.dicoding.picodiploma.capstoneartion.newAuction

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityNewAuctionBinding
import com.dicoding.picodiploma.capstoneartion.loading.Loading
import com.dicoding.picodiploma.capstoneartion.main.HomeActivity
import com.dicoding.picodiploma.capstoneartion.utils.Helper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NewAuctionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewAuctionBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private var selectedImg: Uri? = null
    private var storageUri: String = ""
    var storage: FirebaseStorage? = null
    private lateinit var storageReference: StorageReference
    private lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAuctionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = FirebaseDatabase.getInstance()
        auth = Firebase.auth

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        createAuctionBtn()
        imgBtn()
    }

    private fun createAuctionBtn() {
        val loading = Loading()
        binding.createAuctionButton.setOnClickListener {
            val user = auth.currentUser
            val uid = user!!.uid
            val itemRef = db.getReference(TABLE_AUCTION_ITEMS).push()
            val postId = itemRef.key //get post ID
            val myAuctionRef = db.getReference(USER_TABLE).child(uid).child(AUCTION).child(postId!!)

            if (selectedImg != null) {
                val radioGroup = binding.radioGroup.checkedRadioButtonId
                val photo = selectedImg!!
                val owner = user.displayName.toString()
                val ownerId = user.uid
                val title = binding.etWorkTitle.text.toString()
                val description = binding.etDescriptionWork.text.toString()
                val startingPrice = binding.etStartingPrice.text.toString()
                val buyoutPrice = binding.etBuyoutPrice.text.toString()
                val priceIncrement = binding.etPriceIncrement.text.toString()
                val day = binding.etAuctionDay.text.toString()
                val hour = binding.etAuctionHour.text.toString()
                val currentPrice = startingPrice

                when {
                    title.isEmpty() -> binding.etWorkTitle.error =
                        getString(R.string.field_required)
                    description.isEmpty() -> binding.etDescriptionWork.error =
                        getString(R.string.field_required)
                    radioGroup < 0 -> binding.rb3d.error = getString(R.string.choose_catagory)
                    startingPrice.isEmpty() -> binding.etStartingPrice.error =
                        getString(R.string.field_required)
                    buyoutPrice.isEmpty() -> binding.etBuyoutPrice.error =
                        getString(R.string.field_required)
                    day.isEmpty() -> binding.etAuctionDay.error = getString(R.string.field_required)
                    hour.isEmpty() -> binding.etAuctionHour.error =
                        getString(R.string.field_required)
                    priceIncrement.isEmpty() -> binding.etPriceIncrement.error =
                        getString(R.string.field_required)
                    startingPrice.isNotValidNumber() -> binding.etStartingPrice.error =
                        getString(R.string.must_be_number)
                    buyoutPrice.isNotValidNumber() -> binding.etBuyoutPrice.error =
                        getString(R.string.must_be_number)
                    priceIncrement.isNotValidNumber() -> binding.etPriceIncrement.error =
                        getString(R.string.must_be_number)
                    day.isNotValidNumber() -> binding.etBuyoutPrice.error =
                        getString(R.string.must_be_number)
                    hour.isNotValidNumber() -> binding.etAuctionHour.error =
                        getString(R.string.must_be_number)
                    startingPrice.toInt() >= buyoutPrice.toInt() -> binding.etBuyoutPrice.error =
                        getString(R.string.warn_buy_out_over_start_price)

                    else -> {
                        val timeCounter: Int = getTimeCounter(day.toInt(), hour.toInt())
                        radioButton = findViewById(radioGroup)
                        val category = radioButton.text.toString()
                        setToFireStorage(photo, postId)
                        val item = AuctionItem(
                            postId,
                            owner,
                            ownerId,
                            title,
                            description,
                            storageUri,
                            category,
                            startingPrice.toInt(),
                            buyoutPrice.toInt(),
                            currentPrice.toInt(),
                            priceIncrement.toInt(),
                            timeCounter,
                            ""
                        )
                        lifecycleScope.launch {
                            myAuctionRef.setValue(item)
                        }
                        itemRef.setValue(item)
                    }
                }
            } else {
                Toast.makeText(
                    this@NewAuctionActivity,
                    getString(R.string.image_required),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //Buat set storage
    private fun setToFireStorage(imageUri: Uri, postId: String) {
        val dialogBinding = layoutInflater.inflate(R.layout.add_auction_loading_dialog, null)
        val myDialog = Dialog(this@NewAuctionActivity)
        myDialog.setContentView(dialogBinding)

        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val user = auth.currentUser
        val itemRef = db.getReference(TABLE_AUCTION_ITEMS)
        val myAuctionRef = db.getReference(USER_TABLE).child(user!!.uid).child(AUCTION)
        val storageReference =
            FirebaseStorage.getInstance().getReference("ImageFolder").child(user.uid)
                .child(fileName)
        storageReference.putFile(imageUri).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                storageUri = uri.toString()

                //update RTD
                itemRef.child(postId).child("photoUrl").setValue(storageUri)
                myAuctionRef.child(postId).child("photoUrl").setValue(storageUri)

                myDialog.dismiss()
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            binding.btAddPhoto.setImageURI(null)
            Toast.makeText(this, "Successfully Uploaded!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Upload Failed!", Toast.LENGTH_SHORT).show()
        }
    }

    //Intent Gallery Launcher
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            selectedImg = result.data?.data as Uri
            binding.btAddPhoto.setImageURI(selectedImg)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun imgBtn() {
        binding.btAddPhoto.setOnClickListener {
            startGallery()
        }
    }

    private fun getTimeCounter(day: Int, hour: Int): Int {
        return Helper.hourToMinute((day * 24) + hour)
    }

    companion object {
        const val TABLE_AUCTION_ITEMS = "AuctionItems"
        const val USER_TABLE = "User"
        const val AUCTION = "Auction"
    }
}

private fun String.isNotValidNumber(): Boolean {
    return when (toIntOrNull()) {
        null -> true
        else -> false
    }
}
