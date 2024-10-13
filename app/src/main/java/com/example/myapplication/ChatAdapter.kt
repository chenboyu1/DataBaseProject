package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 定義訊息類型的列舉，區分用戶訊息與 AI 訊息
enum class MessageType {
    USER, AI
}

// 定義訊息資料類別，包含訊息內容與訊息類型
data class Message(val content: String, val type: MessageType)

// 聊天訊息的適配器，用於 RecyclerView
class ChatAdapter(private val messageList: List<Message>) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    // 定義視圖持有者，用來綁定 RecyclerView 的每個訊息項目
    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textViewMessage) // 綁定訊息的 TextView
    }

    // 根據訊息類型 (用戶/AI) 動態加載不同的佈局
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        // 根據訊息類型選擇佈局文件
        val layoutId = when (viewType) {
            MessageType.USER.ordinal -> R.layout.item_user_message // 用戶訊息佈局，訊息靠右
            MessageType.AI.ordinal -> R.layout.item_ai_message // AI 訊息佈局，訊息靠左
            else -> throw IllegalArgumentException("Invalid view type") // 非法的 viewType 則拋出異常
        }

        // 加載佈局並創建 ViewHolder
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return MessageViewHolder(view)
    }

    // 返回每個項目的視圖類型 (USER 或 AI)
    override fun getItemViewType(position: Int): Int {
        return messageList[position].type.ordinal // 根據訊息類型回傳對應的視圖類型
    }

    // 將訊息綁定到 ViewHolder，並設置顯示的訊息與樣式
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        // 獲取當前訊息
        val message = messageList[position]
        holder.textView.text = message.content // 將訊息內容設置到 TextView

        // 根據訊息類型設置背景顏色和文字顏色
        if (message.type == MessageType.AI) {
            holder.textView.setBackgroundResource(R.drawable.ai_message_background) // AI 訊息背景顏色
            holder.textView.setTextColor(android.graphics.Color.WHITE) // AI 訊息的文字顏色
        } else {
            holder.textView.setBackgroundResource(R.drawable.user_message_background) // 用戶訊息背景顏色
            holder.textView.setTextColor(android.graphics.Color.BLACK) // 用戶訊息的文字顏色
        }

        // 調整訊息背景的長度，根據訊息內容的長度自適應
        val params = holder.textView.layoutParams
        holder.textView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)) // 測量 TextView 的寬度
        val textWidth = holder.textView.measuredWidth // 獲取文字寬度
        params.width = textWidth // 設定背景寬度與文字相匹配
        holder.textView.layoutParams = params // 更新佈局參數
    }

    // 返回訊息列表的大小，即顯示的項目數量
    override fun getItemCount(): Int {
        return messageList.size // 返回訊息列表的總數
    }
}
