package com.example.myapplication.data.api

import com.example.myapplication.data.dto.response.ReactionsResponse
import com.example.myapplication.data.dto.UserDto
import com.example.myapplication.data.dto.UserPresenceDto
import com.example.myapplication.data.dto.response.*
import com.example.myapplication.data.dto.response.queue.EventsQueueResponse
import com.example.myapplication.data.dto.response.queue.RegisterQueueResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface MessengerApi {

    @GET("streams")
    fun getAllStreams(): Single<StreamsResponse>

    @GET("users/me/subscriptions")
    fun getSubscriptions(): Single<SubscriptionsResponse>

    @GET("users/me/{stream_id}/topics")
    fun getTopicsInStream(
        @Path("stream_id") streamId: Int
    ): Single<TopicsResponse>

    @GET("users")
    fun getUsers(): Single<UsersResponse>

    @GET("users/me")
    fun getOwnUser(): Single<UserDto>

    @FormUrlEncoded
    @POST("messages")
    fun sendMessageToTopic(
        @Field("type") type: String = "stream",
        @Field("to") streamName: String,
        @Field("topic") topicName: String,
        @Field("content") text: String
    ): Single<Response>

    @GET("messages")
    fun getMessagesFromTopic(
        @Query("anchor") anchor: String = "newest",
        @Query("num_before") numBefore: Int = 100,
        @Query("num_after") numAfter: Int = 0,
        @Query("apply_markdown") rawContent: Boolean = false,
        @Query("narrow") narrow: String
    ): Single<MessagesResponse>

    @FormUrlEncoded
    @POST("messages/{message_id}/reactions")
    fun addEmojiReaction(
        @Path("message_id") messageId: Int,
        @Field("emoji_name") emojiName: String,
        @Field("emoji_code") emojiCode: String,
        @Field("reaction_type") type: String = "unicode_emoji"
    ): Single<Response>

    @DELETE("messages/{message_id}/reactions")
    fun removeEmojiReaction(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String,
        @Query("emoji_code") emojiCode: String,
        @Query("reaction_type") type: String = "unicode_emoji"
    ): Single<Response>

    @GET("users/{user_id_or_email}/presence")
    fun getUserPresence(
        @Path("user_id_or_email") param: Int
    ): Single<UserPresenceDto>

    @FormUrlEncoded
    @POST("register")
    fun registerEventQueue(
        @Field("event_types") eventTypes: String,
        @Field("fetch_event_types") fetchEventTypes: String,
        @Field("apply_markdown") rawContent: Boolean = false,
        @Field("narrow") narrow: String? = null,
        @Field("slim_presence") slimPresence: Boolean = true
    ): Single<RegisterQueueResponse>

    @GET("events")
    fun getEventsFromQueue(
        @Query("queue_id") queueId: String,
        @Query("last_event_id") lastEventId: Int
    ): Observable<EventsQueueResponse>

    @DELETE("events")
    fun deleteEventQueue(
        @Query("queue_id") queueId: String
    ): Single<Response>

    @GET("/static/generated/emoji/emoji_codes.json")
    fun getEmojiReactions(): Single<ReactionsResponse>

}