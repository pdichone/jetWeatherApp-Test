package com.bawp.jetweatherapp.utils

import android.icu.text.DateFormat
import java.text.SimpleDateFormat

fun formatDate(timestamp: Int): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}