package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.ReactionDto
import com.example.myapplication.data.repository.RepositoryImpl
import com.example.myapplication.domain.entity.chat.Reaction

internal class ReactionDtoToEntityMapper : (List<ReactionDto>) -> (List<Reaction>) {

    override fun invoke(emojiList: List<ReactionDto>): List<Reaction> {
        return emojiList.map {
            Reaction(
                emojiName = it.emojiName,
                emojiCode = it.emojiCode,
                reactedUsersIds = calculateReactedUsers(it, emojiList),
                reactionClickState = clickedByOwnUser(it, emojiList)
            )
        }.distinct()
    }

    fun calculateReactedUsers(emoji: ReactionDto, emojiList: List<ReactionDto>): List<Int> {
        val reactedUsers = mutableListOf<Int>()
        emojiList.forEach {
            if (it.emojiName == emoji.emojiName) reactedUsers.add(it.userId)
        }
        return reactedUsers
    }

    private fun clickedByOwnUser(emoji: ReactionDto, emojiList: List<ReactionDto>): Boolean {
        val reactionList = emojiList.filter { it.emojiName == emoji.emojiName }
        return if (reactionList.size <= 1) false else reactionList.any { it.userId == RepositoryImpl.owner.userId }
    }

}