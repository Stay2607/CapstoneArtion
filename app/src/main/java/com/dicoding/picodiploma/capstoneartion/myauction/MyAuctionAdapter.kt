package com.dicoding.picodiploma.capstoneartion.myauction

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.capstoneartion.R
import com.dicoding.picodiploma.capstoneartion.data.AuctionItem
import com.dicoding.picodiploma.capstoneartion.databinding.ItemProductBinding
import com.dicoding.picodiploma.capstoneartion.detail.AuctionItemDetails

class MyAuctionAdapter(private val listProduct: ArrayList<AuctionItem>): RecyclerView.Adapter<MyAuctionAdapter.MyAuctionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAuctionViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyAuctionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyAuctionViewHolder, position: Int) {
        val product = listProduct[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = listProduct.size

//    class MyAuctionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var tvOwner: TextView = itemView.findViewById(R.id.tv_artist)
//        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
//        var imgPhoto: ImageView = itemView.findViewById(R.id.imageView)
//        var tvCategory: TextView = itemView.findViewById(R.id.tv_category)
//        var tvBidPrice: TextView = itemView.findViewById(R.id.tv_bid_price)
//        var tvBuyOut: TextView = itemView.findViewById(R.id.tv_buyout_price)
//    }

    class MyAuctionViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: AuctionItem) {
            with(binding){
                tvTitle.text = user.title
                tvAuctionTime.text = user.timeCounter.toString()
                tvBidPrice.text = user.startingPrice.toString()
                tvBuyoutPrice.text = user.buyoutPrice.toString()
                tvCategory.text = user.category
                tvArtist.text = user.owner
                Glide.with(itemView.context)
                    .load(user.photoUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_broken_image))
                    .into(imageView)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, AuctionItemDetails::class.java)
                    intent.putExtra(AuctionItemDetails.ITEM_ID, user.itemId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}
