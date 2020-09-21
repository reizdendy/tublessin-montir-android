package com.example.tublessin_montir.domain.chat

import retrofit2.Call
import retrofit2.http.*

interface ChatAPI {
    @GET("conversation/get")
    fun GetChatList(
        @Query("senderid") senderid: String,
        @Query("receiverid") receiverid: String,
        @Header("Authorization") token: String
    ): Call<ChatResponseMessage>

    @POST("conversation/new")
    fun PostNewMessage(
        @Body conversation: Conversation,
        @Header("Authorization") token: String
    ): Call<SendMessageResponse>
}