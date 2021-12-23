package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.event.Event
import com.example.myapplication.data.dto.event.PresenceEvent
import com.example.myapplication.domain.entity.people.UserPresence

internal class UserPresenceEventToEntityMapper : (List<Event>) -> (List<UserPresence>) {

    override fun invoke(events: List<Event>): List<UserPresence> {
        return events.map {
            UserPresence(
                userId = (it as PresenceEvent).userId,
                status = it.presence.website.status!!
            )
        }
    }

}