package com.example.myapplication.data.mapper

import com.example.myapplication.domain.entity.chat.Reaction
import com.example.myapplication.domain.entity.people.User

internal class ReactionsResponseToEntityMapper :
        (Map<String, String>, User) -> (List<Reaction>) {

    override fun invoke(emojiMap: Map<String, String>, ownUser: User): List<Reaction> {
        val emojiList = arrayListOf<Reaction>()
        for ((emojiName, emojiCode) in emojiMap.entries) {
            emojiList += Reaction(
                emojiName,
                emojiCode,
                mutableListOf(ownUser.userId)
            )
        }
        return emojiList
    }

}