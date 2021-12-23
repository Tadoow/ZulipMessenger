package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.response.queue.RegisterQueueResponse
import com.example.myapplication.domain.entity.people.UserPresence

internal class UserPresenceFetchedDataToEntityMapper :
        (RegisterQueueResponse) -> (List<UserPresence>) {

    private val timeDifferInUnix = 600
    private val activeStatus = "active"
    private val idleStatus = "idle"

    override fun invoke(registerQueue: RegisterQueueResponse): List<UserPresence> {
        val serverTimestamp = registerQueue.registerQueueTime?.toInt() ?: 0
        val usersPresencesList = arrayListOf<UserPresence>()

        for ((id, presence) in registerQueue.presences!!) {
            val timestamp = (presence.activeTimestamp ?: presence.idleTimestamp)!!
            if (serverTimestamp - timestamp <= timeDifferInUnix) {
                usersPresencesList.add(
                    UserPresence(
                        userId = id,
                        status = if (presence.activeTimestamp != null) activeStatus else idleStatus
                    )
                )
            }
        }
        return usersPresencesList
    }

}