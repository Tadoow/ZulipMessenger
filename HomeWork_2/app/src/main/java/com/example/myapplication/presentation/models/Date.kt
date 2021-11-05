package com.example.myapplication.presentation.models

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.util.*

@Parcelize
data class Date(
    private val day: Int = 0,
    private val month: Int = 0,
    private val year: Int = 2021,
    private val hour: Int = 12,
    private val minute: Int = 0,
    private val second: Int = 0,
    private val nanoSecond: Int = 0,
) : ChatItem {

    @IgnoredOnParcel
    private var monthName: String

    init {
        val zdt: ZonedDateTime = ZonedDateTime.of(
            year, month, day,
            hour, minute, second, nanoSecond,
            ZoneId.of("Europe/Moscow")
        )

        monthName = zdt.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        monthName = monthName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
        monthName = monthName.substring(0..2)
    }

    override fun getType(): Int {
        return DATE
    }

    override fun toString(): String {
        return "$day $monthName"
    }

    companion object {
        const val DATE: Int = 100
    }
}