package com.dicoding.picodiploma.capstoneartion.newAuction

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
<<<<<<< Updated upstream
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityNewAuctionBinding
import com.dicoding.picodiploma.capstoneartion.utils.Helper
=======
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityNewAuctionBinding
import com.dicoding.picodiploma.capstoneartion.newAuction.util.reduceFileImage
import com.dicoding.picodiploma.capstoneartion.newAuction.util.uriToFile
>>>>>>> Stashed changes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File

class NewAuctionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewAuctionBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

<<<<<<< Updated upstream
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
=======
    private var getFile: File? = null
>>>>>>> Stashed changes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAuctionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance()
        auth = Firebase.auth
        val user = auth.currentUser

        val itemRef = db.getReference(TABLE_AUCTION_ITEMS)

        binding.btAddPhoto.setOnClickListener { startGallery() }

        binding.createAuctionButton.setOnClickListener {
<<<<<<< Updated upstream
            val intSelectButton: Int = binding.radioGroup.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)
            val category = radioButton.text.toString()
            val timeCounter: Int = getTimeCounter()
=======


>>>>>>> Stashed changes
            val item = AuctionItem(
                user?.displayName.toString(),
                binding.etWorkTitle.text.toString(),
                binding.etDescriptionWork.text.toString(),
<<<<<<< Updated upstream
                binding.frAddPhoto.toString(), //Masih belum sesuai buat foto
                category,
=======
                binding.btAddPhoto.let { it1 ->
                    Glide.with(this)
                        .load(photo)
                        .circleCrop()
                        .into(it1)
                }, //Masih belum sesuai buat foto
                "2D", //Ada if dulu,
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent,"Choose A Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@NewAuctionActivity)
            getFile = myFile
            binding.btAddPhoto.setImageURI(selectedImg)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this, "Cannot get permission", Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
>>>>>>> Stashed changes
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