package com.dicoding.picodiploma.capstoneartion.myauction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentBidBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class BidFragment : Fragment() {

    private var _binding: FragmentBidBinding? = null
    private val binding get() = _binding
    private lateinit var rvProduct: RecyclerView
    private lateinit var db: FirebaseDatabase
    private lateinit var listProduct: ArrayList<AuctionItem>
    private lateinit var auth: FirebaseAuth
    private val list = ArrayList<AuctionItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBidBinding.inflate(inflater, container, false)
        db = FirebaseDatabase.getInstance()
        auth = Firebase.auth
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listProduct = arrayListOf()
        getListProduct()
    }

    private fun getListProduct() {
        val userId = auth.currentUser!!.uid
        db.getReference(TABLE_USER).child(userId).child(BID).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (auctionItem in snapshot.children) {

                        val item = auctionItem.getValue(AuctionItem::class.java)
                        list.clear()
                        listProduct.add(item!!)
                    }
                    showRecyclerList()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //nothing to do
            }

        })
    }

    private fun showRecyclerList() {
        rvProduct = binding!!.recyclerView
        rvProduct.setHasFixedSize(true)
        list.clear()
        list.addAll(listProduct)
        rvProduct.layoutManager = LinearLayoutManager(context)
        val listProductAdapter = MyAuctionAdapter(list)
        rvProduct.adapter = listProductAdapter
    }

    companion object {
        const val TABLE_USER = "User"
        const val BID = "Bid"
    }
}