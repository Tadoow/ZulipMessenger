package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.MessageDto
import com.example.myapplication.data.repository.RepositoryImpl
import com.example.myapplication.domain.entity.chat.ChatItem
import com.example.myapplication.domain.entity.chat.Date
import com.example.myapplication.domain.entity.chat.IncomeMessage
import com.example.myapplication.domain.entity.chat.OutcomeMessage
import com.example.myapplication.domain.entity.people.User

internal class MessageDtoToEntityMapper : (MessageDto) -> (List<ChatItem>) {

    val reactionDtoToEntityMapper: ReactionDtoToEntityMapper = ReactionDtoToEntityMapper()

    override fun invoke(message: MessageDto): List<ChatItem> {
        when (message.senderId) {
            RepositoryImpl.owner.userId -> return listOf(
                Date(message.time),
                OutcomeMessage(
                    id = message.id,
                    messageText = message.text,
                    emojiList = reactionDtoToEntityMapper(message.reactions)
                )
            )
            else -> return listOf(
                Date(message.time),
                IncomeMessage(
                    id = message.id,
                    user = User(
                        message.senderId,
                        message.senderName,
                        message.senderEmail,
                        avatar = message.avatar
                    ),
                    messageText = message.text,
                    emojiList = reactionDtoToEntityMapper(message.reactions)
                )
            )
        }
    }

}