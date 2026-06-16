package com.jesil.ghostguard.logs.presentation.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Long.toNormalTime(): String = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(this))

fun Long.toDayString(): String {
    // assume your timestamp string is in a known format, e.g. "yyyy-MM-dd HH:mm:ss"
    val date = Date(this)
    val calendar = Calendar.getInstance()
    calendar.time = date


    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -1)
    }

    calendar.time = date

    return when {
        isSameDay(calendar, today) -> "Today"
        isSameDay(calendar, yesterday) -> "Yesterday"
        else -> {
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            outputFormat.format(date)
        }
    }
}
private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean =
    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
