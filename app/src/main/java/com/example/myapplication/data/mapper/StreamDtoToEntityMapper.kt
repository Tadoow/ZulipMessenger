package com.example.myapplication.data.mapper

import com.example.myapplication.data.dto.StreamDto
import com.example.myapplication.domain.entity.channels.Stream
import com.example.myapplication.domain.entity.channels.Topic

internal class StreamDtoToEntityMapper : (StreamDto, List<Topic>) -> (Stream) {

    override fun invoke(stream: StreamDto, topicList: List<Topic>): Stream {
        return Stream(
            streamName = stream.name,
            topics = topicList
        )
    }

}