package com.dicoding.picodiploma.capstoneartion.newAuction

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityNewAuctionBinding
import com.dicoding.picodiploma.capstoneartion.newAuction.util.uriToFile
import com.dicoding.picodiploma.capstoneartion.utils.Helper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso


class NewAuctionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewAuctionBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    val storageRef = Firebase.storage.reference

    private val imageView: ImageView? = null

    // Uri indicates, where the image will be picked from
    private val filePath: Uri? = null

    // instance for firebase storage and StorageReference
    var storage: FirebaseStorage? = null
    private lateinit var storageReference: StorageReference

    //var radioGroup: RadioGroup? = null
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
        val user = auth.currentUser
        val itemRef = db.getReference(TABLE_AUCTION_ITEMS)
        //val db = FirebaseDatabase.getInstance()

        binding.createAuctionButton.setOnClickListener {

            //val imgSelected : Uri = binding.btAddPhoto as Uri
            //setToFireStorage(imgSelected)


            val intSelectButton: Int = binding.radioGroup.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)
            val category = radioButton.text.toString()
            val timeCounter: Int = getTimeCounter()
            val item = AuctionItem(
                user?.displayName.toString(),
                binding.etWorkTitle.text.toString(),
                binding.etDescriptionWork.text.toString(),
                binding.btAddPhoto.toString(),//retrieveData().toString(), //Masih belum sesuai buat foto
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

    private fun retrieveData() {
        val user = auth.currentUser
        val db = FirebaseDatabase.getInstance().getReference("auction_db")
            .child("AuctionImages").child(user?.displayName.toString())
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    Picasso.with(this@NewAuctionActivity).load(Uri.parse(snapshot.value.toString()))
                        .into(binding.btAddPhoto)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    //Buat set storage
    private fun setToFireStorage(imageUri: Uri) {
        val user = auth.currentUser
        val storageReference = FirebaseStorage.getInstance().reference.child("ImageFolder").child(
            user?.displayName.toString()
        ).child(binding.etWorkTitle.toString())
        val imgReference = storageReference.child(binding.etWorkTitle.toString())
        imgReference.putFile(imageUri).addOnSuccessListener {
            imgReference.downloadUrl.addOnSuccessListener { uri ->
                val db =
                    FirebaseDatabase.getInstance().getReference("auction_db")
                        .child("AuctionImages").child(user?.displayName.toString())
                db.child("newImage").setValue(uri.toString())
                Toast.makeText(
                    this@NewAuctionActivity,
                    "Successfully added to real time",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@NewAuctionActivity,
                    e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this@NewAuctionActivity, e.message, Toast.LENGTH_SHORT).show()
        }
    }


    //Intent Gallery Launcher
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@NewAuctionActivity)
            var getFile = myFile
            binding.btAddPhoto.setImageURI(selectedImg)
            //setToFireStorage(selectedImg)
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

    private fun getTimeCounter(): Int {
        val day = binding.etAuctionDay.text.toString().toInt() * 24
        val hour = binding.etAuctionHour.text.toString().toInt()
        return Helper.hourToMinute(day + hour)
    }

    companion object {
        const val TABLE_AUCTION_ITEMS = "AuctionItems"

        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}