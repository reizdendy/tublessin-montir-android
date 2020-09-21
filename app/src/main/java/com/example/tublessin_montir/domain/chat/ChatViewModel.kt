package com.example.tublessin_montir.domain.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tublessin_montir.config.RetrofitBuilder

class ChatViewModel : ViewModel() {
    val chatRepository = ChatRepository(
        RetrofitBuilder.createRetrofit().create(
            ChatAPI::class.java
        )
    )

    fun getChatDetail() = chatRepository.chatLiveData as LiveData<ChatResponseMessage>

    fun RequestChatList(senderid: String, receiverid: String) =
        chatRepository.GetChatList(senderid, receiverid)

    fun getSendMessage() = chatRepository.sendMessageLiveData as LiveData<SendMessageResponse>

    fun PostNewMessage(conversation: Conversation) = chatRepository.PostNewMessage(conversation)

}