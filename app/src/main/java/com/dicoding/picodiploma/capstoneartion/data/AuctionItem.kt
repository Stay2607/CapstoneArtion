package com.dicoding.picodiploma.capstoneartion.data

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
data class AuctionItem(
    val itemId: String = "",
    val owner: String = "",
    val ownerId: String = "",
    val title: String = "",
    val description: String = "",
    val photoUrl: String = "",
    val category: String = "",
    val startingPrice: Int = 0,
    val buyoutPrice: Int = 0,
    val currentPrice: Int = 0,
    val priceIncrement: Int = 0,
    val timeCounter: Int = 0,
    val winner: String? = ""
) : Parcelable
