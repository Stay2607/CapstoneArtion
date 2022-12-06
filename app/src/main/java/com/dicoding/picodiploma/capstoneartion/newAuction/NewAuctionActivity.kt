package com.dicoding.picodiploma.capstoneartion.newAuction

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.ActivityNewAuctionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NewAuctionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewAuctionBinding
    private lateinit var db: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAuctionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.database
        auth = Firebase.auth
        val user = auth.currentUser

        val itemRef = db.reference.child(ITEM_CHILD)

        binding.createAuctionButton.setOnClickListener {
            val item = AuctionItem(
                user?.displayName.toString(),
                binding.etWorkTitle.text.toString(),
                binding.etDescriptionWork.text.toString(),
                binding.frAddPhoto.toString(), //Masih belum sesuai buat foto
                "2D", //Ada if dulu,
                binding.etPriceWork.text.toString(),
                "700000" // Belum sesuai
            )
            itemRef.push().setValue(item) { error, _ ->
                if (error != null) {
                    Toast.makeText(this, "Error :   " + error.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    //Function Radio Button Selection
    /*private fun categorySelect(view: View): String {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.id) {
                R.id.rb_2d ->
                    if (checked) {
                        return "2D"
                    }
                R.id.rb_3d ->
                    if (checked) {
                        return "3D"
                    }
            }

        }
        return ""
    }

    //function time selection
    private fun timeSelection(view: View){
        if (view is RadioButton){
            val checked = view.isChecked

            when(view.id){
                R.id.rb_1day->
                    if (checked){
                        return
                    }
            }
        }
    }

    //Function timer
    private fun startTimerCounter(view: View, millisecond : Long){
        object : CountDownTimer(millisecond, 1000){

        }
    }*/

    companion object {
        const val ITEM_CHILD = "item"
    }

    fun radio_button_click(view: View) {}
}