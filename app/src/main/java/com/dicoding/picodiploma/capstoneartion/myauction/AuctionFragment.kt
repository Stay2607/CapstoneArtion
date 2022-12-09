package com.dicoding.picodiploma.capstoneartion.myauction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentAuctionBinding

class AuctionFragment : Fragment() {

    private var _binding: FragmentAuctionBinding? = null
    private val binding get() = _binding
    private lateinit var auctionAdapter: MyAuctionAdapter
    private lateinit var rvProduct: RecyclerView
    private val list = ArrayList<AuctionItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAuctionBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerList()
    }

    private val listProduct: ArrayList<AuctionItem>
        get() {
            val owner = "Rudy Atmadja"
            val title = "Karya Lukis"
            val description = "Karya ini dibuat dengan menggunakan metode .....Karya ini dibuat dengan menggunakan metode ....." +
                    "Karya ini dibuat dengan menggunakan metode .....Karya ini dibuat dengan menggunakan metode .....Karya ini dibuat dengan menggunakan metode ....."
            val photoUrl = ""
            val category = "2D"
            val starting = 123
            val buyout = 123
            val current = 123
            val time = 123
            val listProduct = ArrayList<AuctionItem>()
            val product = AuctionItem(owner, "",title, description, photoUrl, category, starting, buyout, current, 123,time, "")
            listProduct.add(product)
            return listProduct
        }

    private fun showRecyclerList() {
        rvProduct = binding!!.recyclerView
        rvProduct.setHasFixedSize(true)
        list.addAll(listProduct)
        rvProduct.layoutManager = LinearLayoutManager(context)
        val listHeroAdapter = MyAuctionAdapter(list)
        rvProduct.adapter = listHeroAdapter
    }
}