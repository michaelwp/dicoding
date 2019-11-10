package com.example.footballleague.util

import java.text.SimpleDateFormat
import java.util.*

class DtFormat {

    fun toDate(
        inputDtTime: String,
        dateTimeStr: String = "yyyy-MM-dd HH:mm",
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Date {
        val parser = SimpleDateFormat(dateTimeStr, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(inputDtTime)
    }

    fun formatTo(
        inputDtTime: Date,
        dateTimeFormat: String = "dd-MMM-yyyy HH:mm",
        timeZone: TimeZone = TimeZone.getDefault()
    ): String {
        val formatter = SimpleDateFormat(dateTimeFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(inputDtTime)
    }
}