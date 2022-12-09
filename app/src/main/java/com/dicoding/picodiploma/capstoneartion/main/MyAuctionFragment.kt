package com.dicoding.picodiploma.capstoneartion.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentMyAuctionBinding
import com.dicoding.picodiploma.capstoneartion.myauction.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MyAuctionFragment : Fragment() {

    private var _binding: FragmentMyAuctionBinding? = null
    private val binding get() = _binding
    private val TAB_TITLES = arrayOf(
        "My Auction",
        "My Bid",
        "History"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyAuctionBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val viewPager = binding!!.viewPager
        val tabLayout = binding!!.tabs
        val adapter = SectionsPagerAdapter(parentFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
    }
}