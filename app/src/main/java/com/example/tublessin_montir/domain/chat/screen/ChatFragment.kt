package com.example.tublessin_montir.domain.chat.screen

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tublessin_montir.R
import com.example.tublessin_montir.domain.chat.ChatViewModel
import com.example.tublessin_montir.domain.chat.Conversation
import com.example.tublessin_montir.domain.chat.recycleview.ChatRecycleViewAdapter
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(), View.OnClickListener {

    private val chatViewModel = ChatViewModel()
    private lateinit var montirId: String
    private lateinit var customerId: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_chatbox_send.setOnClickListener(this)

        montirId = Prefs.getString("id", "0")
        customerId = Prefs.getString("userId", "0")
        println("montir id pas chat : ${montirId}")
        println("cust id pas chat : ${customerId}")

        reyclerview_chat.layoutManager = LinearLayoutManager(this.context)
        chatViewModel.RequestChatList(montirId, customerId)

        chatViewModel.getChatDetail().observe(viewLifecycleOwner, Observer{
            if (!it.Results.conversation.isNullOrEmpty()){
                reyclerview_chat.adapter  = ChatRecycleViewAdapter(it.Results.conversation.reversed())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val handler = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                chatViewModel.RequestChatList(montirId, customerId)
                handler.postDelayed(this, 2000)
            }
        }
//Start
        handler.postDelayed(runnable, 2000)
    }

    override fun onClick(v: View?) {
        when (v) {
            button_chatbox_send -> {
                chatViewModel.PostNewMessage(Conversation(
                    sender_id = montirId,
                    receiver_id = customerId,
                    message = edittext_chatbox.text.toString()
                ))
            }
        }
    }
}