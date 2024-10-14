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

        // 根據訊息類型設置背景顏色
        if (message.type == MessageType.AI) {
            holder.textView.setBackgroundResource(R.drawable.ai_message_background) // AI 訊息背景
            holder.textView.setTextColor(android.graphics.Color.WHITE) // AI 訊息文字顏色
        } else {
            holder.textView.setBackgroundResource(R.drawable.user_message_background) // 用戶訊息背景
            holder.textView.setTextColor(android.graphics.Color.BLACK) // 用戶訊息文字顏色
        }

        // 根據內容長度調整背景的長度
        val params = holder.textView.layoutParams
        holder.textView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        val textWidth = holder.textView.measuredWidth
        params.width = textWidth + 2 // 加上額外的邊距，例如 24dp (可根據需要調整)
        holder.textView.layoutParams = params
    }


    override fun getItemCount(): Int {
        return messageList.size
    }
}
