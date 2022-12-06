package com.dicoding.picodiploma.capstoneartion.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class AuctionItem(
    val owner: String,
    val title: String,
    val description: String,
    val photoUrl: String,
    val category: String,
    val startingPrice: Int,
    val buyoutPrice: Int,
    val currentPrice: Int,
    val timeCounter: String
) {
}
