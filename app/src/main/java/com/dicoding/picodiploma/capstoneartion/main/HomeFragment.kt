package com.dicoding.picodiploma.capstoneartion.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentAuctionBinding
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentHomeBinding
import com.dicoding.picodiploma.capstoneartion.myauction.MyAuctionAdapter
import com.dicoding.picodiploma.capstoneartion.newAuction.NewAuctionActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var auctionAdapter: MyAuctionAdapter
    private lateinit var rvProduct: RecyclerView
    private val list = ArrayList<AuctionItem>()

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
        rvProduct = binding!!.recyclerView.findViewById(R.id.recyclerView)
        rvProduct.setHasFixedSize(true)

        list.addAll(listHeroes)
        showRecyclerList()
    }

    companion object {
        const val TABLE_AUCTION_ITEMS = "AuctionItems"
    }

//    @SuppressLint("NotifyDataSetChanged")
//    private fun initView(){
//        auctionAdapter = MyAuctionAdapter()
//        binding!!.recyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = auctionAdapter
//        }
//        auctionAdapter.(favoriteUser)
//    }

    private val listHeroes: ArrayList<AuctionItem>
        get() {
            val owner = "resources.getStringArray(R.array.data_name)"
            val title = "resources.etStringArray(R.array.data_description)"
            val description = "resources.getStringArray(R.array.data_description)"
            val photoUrl = "resources.ggetStringArray(R.array.data_description)"
            val category = "resources.getStringArray(R.array.data_description)"
            val starting = 123
            val buyout = 123
            val current = 123
            val increment = 123
            val time = 123
//            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listHero = ArrayList<AuctionItem>()
            val hero = AuctionItem(owner, "", title, description, photoUrl, category, starting, buyout, current, increment, time, "")
            listHero.add(hero)
            return listHero
        }

    private fun showRecyclerList() {
        rvProduct.layoutManager = LinearLayoutManager(context)
        val listHeroAdapter = MyAuctionAdapter(list)
        rvProduct.adapter = listHeroAdapter
    }
}