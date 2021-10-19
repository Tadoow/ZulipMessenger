package com.example.myapplication.model

import android.util.Log
import java.text.DateFormatSymbols
import java.util.*

class DateMessage : Message {

    var day: Int = 0
    var month: Int = 0
    var monthName: String = ""

    init {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
    }

    override fun getType(): Int {
        return MessageTypes.DATE.ordinal
    }

    fun getCurrentDate(daysBefore: Int = 0): DateMessage {
        day -= daysBefore
        monthName = DateFormatSymbols().shortMonths[month]
        monthName = monthName.substring(0, monthName.length - 1)
        monthName = monthName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        return this
    }

    override fun toString(): String {
        return "$day $monthName"
    }
}