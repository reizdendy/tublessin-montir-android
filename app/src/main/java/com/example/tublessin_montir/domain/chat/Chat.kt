package com.example.tublessin_montir.domain.chat

class ChatResponseMessage(
    val Message: String = "",
    val Code: Int = 0,
    val Status: String = "",
    val Results: Results
)

class SendMessageResponse(
    val Message: String = "",
    val Code: Int = 0,
    val Status: String = "",
    val Results: Conversation
)

class Results(
    val conversation: List<Conversation>
)

class Conversation(
    val id: String = "",
    val sender_id: String = "",
    val receiver_id: String = "",
    val message: String = "",
    val status: String = "",
    val date_created: String = ""
)