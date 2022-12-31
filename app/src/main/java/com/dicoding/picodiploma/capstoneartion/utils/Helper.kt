package com.dicoding.picodiploma.capstoneartion.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Helper {
    fun rupiah(number: Double): String {
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    fun hourToMinute(hour: Int): Int {
        return hour * 60
    }

    fun Int.toHour(): String {
        val hour = this / 60
        val minute = this % 60
        return String.format("%02d:%02d", hour, minute)
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        return format.format(date)
    }

    fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    fun convertDateToLong(date: String): Long? {
        val df = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        return df.parse(date)?.time
    }
}