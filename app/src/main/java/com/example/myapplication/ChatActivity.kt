package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText

class ChatActivity : AppCompatActivity() {

    // 定義變數，分別對應 RecyclerView、Adapter、輸入框與按鈕
    private lateinit var recyclerViewChat: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button
    private val messageList = mutableListOf<Message>() // 儲存訊息列表

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)  // 指定佈局文件 activity_chat.xml

        // 連接佈局中的 RecyclerView、EditText 和 Button
        recyclerViewChat = findViewById(R.id.recyclerViewChat)
        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)

        // 設置 RecyclerView 和 Adapter
        chatAdapter = ChatAdapter(messageList) // 使用訊息列表初始化 Adapter
        recyclerViewChat.adapter = chatAdapter // 將 Adapter 設置給 RecyclerView

        // 更新 RecyclerView 的 layoutManager 設置
        recyclerViewChat.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true // 設置訊息從底部開始堆疊，保持最新訊息在底部
            reverseLayout = false // 不反轉佈局，讓訊息按正確時間順序顯示
        }

        // 設置發送按鈕點擊事件
        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString() // 獲取使用者輸入的訊息
            if (message.isNotEmpty()) {
                // 當訊息不為空時，將訊息添加到列表中，並通知 Adapter 更新顯示
                messageList.add(Message(message, MessageType.USER))
                chatAdapter.notifyDataSetChanged() // 通知 RecyclerView 更新
                editTextMessage.text.clear() // 清空輸入框

                // 模擬 AI 回覆
                simulateAIResponse() // 呼叫模擬 AI 回覆的函數
            }
        }
    }

    // 模擬 AI 回覆的函數
    private fun simulateAIResponse() {
        val aiMessage = "這是AI的回覆"  // 模擬回覆內容，可以替換成真正的 AI 回覆邏輯
        messageList.add(Message(aiMessage, MessageType.AI)) // 添加 AI 回覆訊息到列表中
        chatAdapter.notifyDataSetChanged() // 通知 RecyclerView 更新顯示
    }
}
