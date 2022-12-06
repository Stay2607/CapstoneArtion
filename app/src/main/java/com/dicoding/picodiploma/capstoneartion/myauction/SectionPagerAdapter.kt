package com.dicoding.picodiploma.capstoneartion.myauction

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.picodiploma.capstoneartion.main.MyAuctionFragment

class SectionsPagerAdapter(fragment: MyAuctionFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = AuctionFragment()
            1 -> fragment = BidFragment()
            2 -> fragment = HistoryFragment()
        }
        return fragment as Fragment
    }

}