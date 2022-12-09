package com.dicoding.picodiploma.capstoneartion.payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.databinding.FragmentPaymentBinding
import com.dicoding.picodiploma.capstoneartion.myauction.MyAuctionAdapter

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private lateinit var auctionAdapter: MyAuctionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
//        val lisProduct = MyAuctionAdapter()
    }
}