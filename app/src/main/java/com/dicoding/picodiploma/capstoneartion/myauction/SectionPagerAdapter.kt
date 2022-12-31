package com.dicoding.picodiploma.capstoneartion.myauction

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.picodiploma.capstoneartion.main.MyAuctionFragment

class SectionsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AuctionFragment()
            1 -> return BidFragment()
            2 -> return HistoryFragment()
        }
        return MyAuctionFragment()
    }
}