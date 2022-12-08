package com.dicoding.picodiploma.capstoneartion.myauction

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentAuctionBinding

class AuctionFragment : Fragment() {

    private var _binding: FragmentAuctionBinding? = null
    private val binding get() = _binding!!
    private lateinit var auctionAdapter: MyAuctionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView(){
//        binding.recyclerView.apply {
//            layoutManager = LinearLayoutManager(activity)
//            adapter = auctionAdapter
//        }
    }

}