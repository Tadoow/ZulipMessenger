package com.example.myapplication.models

import java.text.DateFormatSymbols
import java.util.*

data class DateMessage(
    private val day: Int = 0,
    private val month: Int = 0,
    override var reactions: List<Reaction> = emptyList()
) : Message {

    private var monthName: String? = null

    init {
        monthName = DateFormatSymbols().shortMonths[month]
        monthName = monthName?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
    }

    override fun getType(): Int {
        return MessageTypes.DATE.ordinal
    }

    fun getCurrentDate(daysBefore: Int = 0): DateMessage {
        val calendar = Calendar.getInstance()
        val currDay = calendar.get(Calendar.DAY_OF_MONTH) - daysBefore
        val currMonth = calendar.get(Calendar.MONTH)
        return DateMessage(currDay, currMonth)
    }

    override fun toString(): String {
        return "$day $monthName"
    }
}