package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerViewChat: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private val messageList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)  // 指定佈局文件

        recyclerViewChat = findViewById(R.id.recyclerViewChat)
        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)

        // 設置 RecyclerView 和 Adapter
        chatAdapter = ChatAdapter(messageList)
        recyclerViewChat.adapter = chatAdapter

        // 更新 layoutManager 設置以從底部堆疊訊息
        recyclerViewChat.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true // 保證訊息從底部開始堆疊
            reverseLayout = false // 不反轉佈局
        }

        // 發送訊息按鈕事件
        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                // 添加訊息到列表並更新顯示
                messageList.add(Message(message, MessageType.USER))
                chatAdapter.notifyDataSetChanged()
                editTextMessage.text.clear()

                // 模擬 AI 回覆
                simulateAIResponse()
            }
        }
    }

    private fun simulateAIResponse() {
        // 模擬 AI 回覆的邏輯，可以之後整合 AI 功能
        val aiMessage = "這是AI的回覆"  // 之後替換成真正的 AI 回覆
        messageList.add(Message(aiMessage, MessageType.AI))
        chatAdapter.notifyDataSetChanged()
    }
}
