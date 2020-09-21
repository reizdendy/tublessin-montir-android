package com.example.tublessin_montir.domain.chat.recycleview

import android.R
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.tublessin_montir.domain.chat.Conversation
import com.pixplicity.easyprefs.library.Prefs


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
        val montirId = Prefs.getString("id", "0")
        if (messageList[position].sender_id == montirId){
            holder.messageMontir.isVisible = true
            holder.messageMontir.text = messageList[position].message
            holder.imageMontir.isVisible = true
        } else {
            holder.messageCustomer.isVisible = true
            holder.messageCustomer.text = messageList[position].message
            holder.imageCustomer.isVisible = true
        }
        holder.senderId.text = messageList[position].sender_id
        holder.date.text = messageList[position].date_created
    }
}

class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageMontir = view.findViewById<ImageView>(com.example.tublessin_montir.R.id.image_message_profile)
    val imageCustomer = view.findViewById<ImageView>(com.example.tublessin_montir.R.id.image_message_profile2)
    val messageMontir = view.findViewById<TextView>(com.example.tublessin_montir.R.id.text_message_body)
    val messageCustomer = view.findViewById<TextView>(com.example.tublessin_montir.R.id.text_message_body2)
    val senderId = view.findViewById<TextView>(com.example.tublessin_montir.R.id.text_message_name)
    val date = view.findViewById<TextView>(com.example.tublessin_montir.R.id.text_message_time)
}