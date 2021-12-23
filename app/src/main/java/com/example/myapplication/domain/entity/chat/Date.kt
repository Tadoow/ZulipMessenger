package com.example.myapplication.domain.entity.chat

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.TextStyle
import java.util.*

@Parcelize
class Date(
    private val epochSeconds: Long
) : ChatItem() {

    @IgnoredOnParcel
    private lateinit var monthName: String

    @IgnoredOnParcel
    private lateinit var day: String

    init {
        calculateDateItem()
    }

    private fun calculateDateItem() {
        val instant = Instant.ofEpochSecond(epochSeconds)
        val zdt: ZonedDateTime = ZonedDateTime.ofInstant(
            instant,
            ZoneId.of("Europe/Moscow")
        )

        day = zdt.dayOfMonth.toString()

        monthName = zdt.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        monthName = monthName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
        monthName = monthName.substring(0..2)
    }

    override fun toString(): String {
        return "$day $monthName"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Date

        if (monthName != other.monthName) return false
        if (day != other.day) return false

        return true
    }

    override fun hashCode(): Int {
        var result = monthName.hashCode()
        result = 31 * result + day.hashCode()
        return result
    }

}