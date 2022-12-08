package com.dicoding.picodiploma.capstoneartion.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentAuctionBinding
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentHomeBinding
import com.dicoding.picodiploma.capstoneartion.myauction.MyAuctionAdapter
import com.dicoding.picodiploma.capstoneartion.newAuction.NewAuctionActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var auctionAdapter: MyAuctionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.addAuctionButtonHome?.setOnClickListener {
            val intent = Intent(activity, NewAuctionActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val TABLE_AUCTION_ITEMS = "AuctionItems"
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView(){
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
//        val lisProduct = MyAuctionAdapter()
    }

}