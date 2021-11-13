package com.example.myapplication.data.provider

import android.graphics.Color
import com.example.myapplication.domain.channels.entity.ChannelsItem
import com.example.myapplication.domain.channels.entity.Stream
import com.example.myapplication.domain.channels.entity.Topic
import com.example.myapplication.domain.chat.entity.*
import com.example.myapplication.domain.people.entity.User

class DataProvider {

    private val generalTopics = getGeneralStreamTopics()
    private val designTopics = getDesignStreamTopics()

    fun getAllStreams(): List<ChannelsItem> {
        return arrayListOf(
            Stream("#general", generalTopics),
            Stream("#announcements", emptyList()),
            Stream("#Development", emptyList()),
            Stream("#random", emptyList()),
            Stream("#Sharing", emptyList()),
            Stream("#Design", designTopics),
            Stream("#PR", emptyList())
        )
    }

    fun getUsers(): List<User> {
        return arrayListOf(
            User(2, "Barack Obama", "baracka@gmail.com", false),
            User(3, "Emmanuel Macron", "emmanuel_mac@gmail.com", true),
            User(10, "Vladimir Putin", "vlad_Put_In_2036@mail.com", true),
            User(4, "Angela Merkel", "bundes_tag@gmail.com", true),
            User(5, "Joe Biden", "grandpa_best@yahoo.com", false),
        )
    }

    fun getSubscribedStreams(): List<ChannelsItem> {
        val subscribedStreams = listOf("#general", "#Development", "#Design", "#PR")
        return getAllStreams().filter { (it as Stream).streamName in subscribedStreams }
    }

    private fun getGeneralStreamTopics(): List<Topic> {
        return listOf(
            Topic("Testing", "#general", Color.parseColor("#2A9D8F"), getTestingTopicItems()),
            Topic("Bruh", "#general", Color.parseColor("#E9C46A"), getBruhTopicItems())
        )
    }

    private fun getDesignStreamTopics(): List<Topic> {
        return listOf(
            Topic("Meetings", "#Design", Color.parseColor("#FF6200EE"), getWebTopicItems())
        )
    }

    private fun getTestingTopicItems(): List<ChatItem> {
        return arrayListOf(
            Date(2, 2),
            IncomeMessage(
                getUsers()[3],
                "В общем, я решила. Я ухожу в отпуск, сколько можно уже сидеть. Че думаете?",
                listOf(
                    Reaction("🤫", mutableListOf(10)),
                    Reaction("😪", mutableListOf(5, 10)),
                    Reaction("😒", mutableListOf(3, 10))
                )
            ),
            OutcomeMessage("Вот это новости", emptyList()),
            IncomeMessage(
                getUsers()[1],
                "Извините за мой французский, но wtf?",
                listOf(
                    Reaction("😶", mutableListOf(10, 2))
                )
            ),
            Date(28, 2),
            IncomeMessage(
                getUsers()[2],
                "Ну и что там с Евросоюзом будет? Ладно, еще увидимся...",
                emptyList()
            ),
            OutcomeMessage("Еще будут комментарии? Или закрываем топик", emptyList()),
            Date(17, 5),
            IncomeMessage(
                getUsers()[4],
                "Donald Trump sucks! Haha, bye bye everyone. See ya later",
                listOf(
                    Reaction("😜", mutableListOf(4, 2)),
                    Reaction("🥰", mutableListOf(3, 4, 5)),
                )
            )
        )
    }

    private fun getBruhTopicItems(): List<ChatItem> {
        return arrayListOf(
            IncomeMessage(
                getUsers()[2],
                "Добрый день уважаемые, это Вова",
                emptyList()
            ),
            OutcomeMessage("На канал можете подписаться?", emptyList()),
            Date(12, 6),
            IncomeMessage(
                getUsers()[2],
                "Не понял, что подписать то надо?",
                listOf(
                    Reaction("😜", mutableListOf(4, 2, 5, 3))
                )
            ),
            Date(10, 7),
            IncomeMessage(
                getUsers()[4],
                "You are a killer",
                emptyList()
            ),
            OutcomeMessage("Все всем пока", emptyList()),
        )
    }

    private fun getWebTopicItems(): List<ChatItem> {
        return arrayListOf(
            Date(20, 10),
            OutcomeMessage("Когда будет дизайн, сколько еще ждать?", emptyList()),
            Date(22, 10),
            IncomeMessage(
                getUsers()[0],
                "Все готово брат, отправил на почту!",
                listOf(
                    Reaction("🥰", mutableListOf(3, 4, 5)),
                    Reaction("☺", mutableListOf(10, 2))
                )
            ),
            IncomeMessage(
                getUsers()[2],
                "Лучший! Столько бабла сейчас заработаем, не сомневался в тебе",
                listOf(
                    Reaction("😍", mutableListOf(2)),
                    Reaction("😳", mutableListOf(3, 10)),
                    Reaction("😛", mutableListOf(4, 2, 5))
                )
            )
        )
    }
}