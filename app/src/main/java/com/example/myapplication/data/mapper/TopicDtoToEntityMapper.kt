package com.example.myapplication.data.mapper

import android.graphics.Color
import com.example.myapplication.data.dto.StreamDto
import com.example.myapplication.data.dto.TopicDto
import com.example.myapplication.domain.entity.channels.Topic

internal class TopicDtoToEntityMapper : (List<TopicDto>, StreamDto) -> (List<Topic>) {

    override fun invoke(topics: List<TopicDto>, stream: StreamDto): List<Topic> {
        return topics.map {
            Topic(
                topicName = it.topicName,
                streamHostName = stream.name,
                backgroundColor = Color.parseColor(stream.color)
            )
        }
    }

}