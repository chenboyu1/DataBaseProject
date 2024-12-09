package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.GlobalVariable.Companion.missionbutton
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class ChatActivity : AppCompatActivity() {

    // 定義變數，分別對應 RecyclerView、Adapter、輸入框與按鈕
    private lateinit var recyclerViewChat: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: AppCompatImageButton  // 修改為 AppCompatImageButton
    private lateinit var buttonBack: ImageButton  // 圓形返回按鈕
    private val messageList = mutableListOf<Message>() // 儲存訊息列表

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)  // 指定佈局文件 activity_chat.xml

        // 連接佈局中的 RecyclerView、EditText 和 ImageButton
        recyclerViewChat = findViewById(R.id.recyclerViewChat)
        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)  // 找到 ImageButton
        buttonBack = findViewById(R.id.buttonBack)  // 找到返回按鈕

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
            if(missionbutton[3] == 0){
                missionbutton[3] = 2
                sendSelectedButtonToServer(missionbutton)
            }
            if (message.isNotEmpty()) {
                // 添加用戶訊息到列表
                messageList.add(Message(message, MessageType.USER))
                chatAdapter.notifyDataSetChanged() // 通知 RecyclerView 更新
                editTextMessage.text.clear() // 清空輸入框

                // 發送用戶訊息到後端 API
                sendMessageToApi(message)
            }
        }

        // 設置返回按鈕點擊事件
        buttonBack.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    // 模擬 AI 回覆的函數
    private fun simulateAIResponse() {
        val aiMessage = "這是模擬的 AI 回覆訊息。"  // 可以替換成真正的 AI 回覆邏輯
        messageList.add(Message(aiMessage, MessageType.AI)) // 添加 AI 回覆訊息到列表中
        chatAdapter.notifyDataSetChanged() // 通知 RecyclerView 更新顯示
    }

    // 用於發送訊息到後端並獲取 AI 回覆
    private fun sendMessageToApi(userMessage: String) {
        val url = "http://140.136.151.129:3000/sendMessage" // 替換為實際 API 地址
        val client = OkHttpClient()

        // 建立 JSON 請求體
        val jsonBody = JSONObject()
        jsonBody.put("message", userMessage)

        // 使用 MediaType.toMediaType() 代替 get() 和 parse()
        val mediaType = "application/json; charset=utf-8".toMediaType()

        val requestBody = RequestBody.create(mediaType, jsonBody.toString())

        // 建立請求
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        // 發送請求
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    // 錯誤處理，例如通知用戶網路問題
                    messageList.add(Message("無法連接到伺服器，請稍後再試。", MessageType.AI))
                    chatAdapter.notifyDataSetChanged()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()  // 這部分已經正確
                    val aiMessage = responseBody ?: "無回應內容"
                    runOnUiThread {
                        // 更新 AI 的回覆到 RecyclerView
                        messageList.add(Message(aiMessage, MessageType.AI))
                        chatAdapter.notifyDataSetChanged()
                    }
                } else {
                    runOnUiThread {
                        // 處理伺服器錯誤
                        messageList.add(Message("伺服器錯誤，請稍後再試。", MessageType.AI))
                        chatAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
    private fun sendSelectedButtonToServer(missionbutton: IntArray) {
        val client = OkHttpClient()
        val username = GlobalVariable.getName()
        val json = """
        {
          "username": "$username",
          "timer": "${missionbutton[0]}",
          "timer2": "${missionbutton[1]}",
          "timer3": "${missionbutton[2]}",
          "timer4": "${missionbutton[3]}"
        }
        """
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("http://140.136.151.129:3000/dailymission")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@ChatActivity, "失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ChatActivity, "成功: $responseBody", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ChatActivity, "失敗: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
