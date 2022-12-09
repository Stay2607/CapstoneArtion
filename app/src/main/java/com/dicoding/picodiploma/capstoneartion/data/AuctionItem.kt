package com.dicoding.picodiploma.capstoneartion.data

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class AuctionItem(
    val owner: String,
    val ownerId: String,
    val title: String,
    val description: String,
    val photoUrl: String,
    val category: String,
    val startingPrice: Int,
    val buyoutPrice: Int,
    val currentPrice: Int,
    val priceIncrement: Int,
    val timeCounter: Int,
    val winner: String?
) : Parcelable
