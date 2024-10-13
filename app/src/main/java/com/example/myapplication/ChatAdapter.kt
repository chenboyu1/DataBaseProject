package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 定義訊息類型
enum class MessageType {
    USER, AI
}

// 定義訊息資料類別
data class Message(val content: String, val type: MessageType)

// 訊息適配器
class ChatAdapter(private val messageList: List<Message>) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    // 視圖持有者
    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(android.R.id.text1) // 使用預設的 TextView ID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        // 創建訊息視圖，這裡可以根據訊息類型設置不同的佈局
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        // 根據訊息類型設置文本
        val message = messageList[position]
        holder.textView.text = message.content
        // 根據訊息類型可進一步設置不同的樣式或顏色
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}
