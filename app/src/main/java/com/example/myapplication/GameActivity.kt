package com.example.myapplication

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var messageBox: TextView
    private lateinit var interactionButton: Button
    private lateinit var taskButton: Button
    private lateinit var profileButton: ImageView
    private lateinit var settingsButton: ImageView
    private lateinit var gameCharacter: ImageView

    private var affectionLevel = 10 // 初始好感度為 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)  // 設定佈局

        // 取得進度條和其他 UI 元件
        progressBar = findViewById(R.id.progressBar)
        messageBox = findViewById(R.id.message_box)
        interactionButton = findViewById(R.id.interaction_button)
        taskButton = findViewById(R.id.task_button)
        profileButton = findViewById(R.id.profile_button)
        settingsButton = findViewById(R.id.settings_button)
        gameCharacter = findViewById(R.id.game_character)

        // 設定進度條最大值
        progressBar.max = 100
        progressBar.progress = affectionLevel // 初始化好感度為 50%

        // 設定初始訊息
        updateMessage("遊戲開始！")

        // 按鈕事件設定
        interactionButton.setOnClickListener {
            onInteractionButtonClicked()
        }

        taskButton.setOnClickListener {
            onTaskButtonClicked()
        }

        profileButton.setOnClickListener {
            onProfileButtonClicked()
        }

        settingsButton.setOnClickListener {
            onSettingsButtonClicked()
        }

        // 開始遊戲邏輯
        startGame()
    }

    private fun startGame() {
        // 開始進度條動畫
        val animation = ObjectAnimator.ofInt(progressBar, "progress", affectionLevel, 100)
        animation.duration = 10000 // 動畫持續 10 秒
        animation.start()

        // 假設遊戲邏輯，隨著進度條更新，顯示不同訊息
        updateMessage("遊戲進行中...")
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

        updateMessage("執行任務... 好感度增加！")
        updateAffectionLevel()
    }

    private fun onProfileButtonClicked() {
        // 顯示個人資料或處理其他邏輯
        updateMessage("顯示個人資料...")
    }

    private fun onSettingsButtonClicked() {
        // 顯示設定選項或處理其他邏輯
        updateMessage("設定選項...")
    }

    private fun updateAffectionLevel() {
        // 更新進度條的進度
        val animation = ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, affectionLevel)
        animation.duration = 500 // 更新動畫的持續時間
        animation.start()
    }
}
