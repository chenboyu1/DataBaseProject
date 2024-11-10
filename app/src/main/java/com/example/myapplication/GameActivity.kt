package com.example.myapplication

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var messageBox: TextView
    private lateinit var interactionButton: ImageButton  // 修改為 ImageButton
    private lateinit var taskButton: ImageButton  // 修改為 ImageButton
    private lateinit var gameCharacter: ImageView

    private var affectionLevel = 50 // 初始親密度為 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)  // 設定佈局

        // 取得進度條和其他 UI 元件
        progressBar = findViewById(R.id.progressBar)
        messageBox = findViewById(R.id.message_box)
        interactionButton = findViewById(R.id.interaction_button)  // 改為 ImageButton
        taskButton = findViewById(R.id.task_button)  // 改為 ImageButton
        gameCharacter = findViewById(R.id.game_character)

        // 設定進度條最大值
        progressBar.max = 100
        // 直接設置親密度進度條
        progressBar.progress = affectionLevel // 設定親密度為50%

        // 設定初始訊息
        updateMessage("遊戲開始，親密度 50！")

        // 按鈕事件設定
        interactionButton.setOnClickListener {
            onInteractionButtonClicked()
        }

        taskButton.setOnClickListener {
            onTaskButtonClicked()
        }

        // 開始遊戲邏輯
        startGame()
    }

    private fun startGame() {
        // 設定進度條的初始值為親密度50
        progressBar.progress = affectionLevel // 這裡應該是50

        // 顯示遊戲開始訊息
        updateMessage("遊戲開始，親密度 50！")
    }

    private fun updateMessage(message: String) {
        messageBox.text = message
    }

    private fun onInteractionButtonClicked() {
        // 每次互動時，增加好感度
        affectionLevel += 10
        if (affectionLevel > 100) affectionLevel = 100  // 好感度最大為 100

        updateMessage("進行互動... 好感度增加！")
        updateAffectionLevel()
    }

    private fun onTaskButtonClicked() {
        // 每次完成任務時，增加好感度
        affectionLevel += 5
        if (affectionLevel > 100) affectionLevel = 100  // 好感度最大為 100

        updateMessage("執行任務... 好感度增加！: $affectionLevel")
        updateAffectionLevel()
    }

    private fun updateAffectionLevel() {
        // 更新進度條的進度
        progressBar.progress = affectionLevel
    }
}
