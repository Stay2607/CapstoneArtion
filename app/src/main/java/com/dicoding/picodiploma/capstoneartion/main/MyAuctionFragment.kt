package com.dicoding.picodiploma.capstoneartion.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentMyAuctionBinding
import com.dicoding.picodiploma.capstoneartion.myauction.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MyAuctionFragment : Fragment() {

    private var _binding: FragmentMyAuctionBinding? = null
    private val binding get() = _binding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_auction_1,
            R.string.tab_auction_2,
            R.string.tab_auction_3
        )
    }

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

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding!!.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding!!.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

}