package com.example.tublessin_montir.domain.chat.recycleview

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tublessin_montir.domain.chat.Conversation


class ChatRecycleViewAdapter(
    private val messageList: List<Conversation>
) :
    RecyclerView.Adapter<ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.example.tublessin_montir.R.layout.chat_recycle_view, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.message.text = messageList[position].message
        holder.senderId.text = messageList[position].sender_id
        holder.date.text = messageList[position].date_created
    }
}

class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val message = view.findViewById<TextView>(com.example.tublessin_montir.R.id.text_message_body)
    val senderId = view.findViewById<TextView>(com.example.tublessin_montir.R.id.text_message_name)
    val date = view.findViewById<TextView>(com.example.tublessin_montir.R.id.text_message_time)
}