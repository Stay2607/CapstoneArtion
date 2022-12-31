package com.dicoding.picodiploma.capstoneartion.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentHomeBinding
import com.dicoding.picodiploma.capstoneartion.loading.Loading
import com.dicoding.picodiploma.capstoneartion.myauction.MyAuctionAdapter
import com.dicoding.picodiploma.capstoneartion.newAuction.NewAuctionActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var rvProduct: RecyclerView
    private val list = ArrayList<AuctionItem>()
    private lateinit var listProduct: ArrayList<AuctionItem>
    private lateinit var db: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        db = FirebaseDatabase.getInstance()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.addAuctionButtonHome?.setOnClickListener {
            val intent = Intent(activity, NewAuctionActivity::class.java)
            startActivity(intent)
        }
        listProduct = arrayListOf()
        getListProduct()
    }

    private fun getListProduct() {
        val loading = Loading()
        loading.showLoading(true, binding!!.progBar)
        db.getReference(TABLE_AUCTION_ITEMS).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (auctionItem in snapshot.children) {
                        val item = auctionItem.getValue(AuctionItem::class.java)
                        list.clear()
                        listProduct.add(item!!)
                    }
                    showRecyclerList()
                    loading.showLoading(false, binding!!.progBar)
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
        val listHeroAdapter = MyAuctionAdapter(list)
        rvProduct.adapter = listHeroAdapter
    }

    companion object {
        const val TABLE_AUCTION_ITEMS = "AuctionItems"
    }
}
