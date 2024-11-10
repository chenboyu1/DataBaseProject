package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    // 定義元件
    private lateinit var heartIcon: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var levelText: TextView
    private lateinit var moneyAmount: TextView
    private lateinit var characterName: TextView

    private lateinit var interactionButton: ImageButton
    private lateinit var playButton: ImageButton
    private lateinit var giftButton: ImageButton
    private lateinit var chatButton: ImageButton

    private lateinit var taskButton: ImageButton
    private lateinit var shopButton: ImageButton
    private lateinit var backpackButton: ImageButton

    private lateinit var messageBox: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)  // 你的 XML 佈局文件名稱

        // 初始化元件
        heartIcon = findViewById(R.id.heart_icon)
        progressBar = findViewById(R.id.progressBar)
        levelText = findViewById(R.id.level_text)
        moneyAmount = findViewById(R.id.money_amount)
        characterName = findViewById(R.id.character_name)

        interactionButton = findViewById(R.id.interaction_button)
        playButton = findViewById(R.id.play_button)
        giftButton = findViewById(R.id.gift_button)
        chatButton = findViewById(R.id.chat_button)

        taskButton = findViewById(R.id.task_button)
        shopButton = findViewById(R.id.shop_button)
        backpackButton = findViewById(R.id.backpack_button)

        messageBox = findViewById(R.id.message_box)

        // 設置按鈕點擊事件
        interactionButton.setOnClickListener {
            // 顯示額外的按鈕
            showAdditionalButtons()
        }

        playButton.setOnClickListener {
            // 觸發「玩耍」動作，增加好感度等
            playInteraction()
        }

        giftButton.setOnClickListener {
            // 送禮邏輯
            giftInteraction()
        }

        chatButton.setOnClickListener {
            // 進入聊天界面
            chatInteraction()
        }

        taskButton.setOnClickListener {
            // 觸發「任務」界面
            openTaskScreen()
        }

        shopButton.setOnClickListener {
            // 觸發「商城」界面
            openShopScreen()
        }

        backpackButton.setOnClickListener {
            // 觸發「背包」界面
            openBackpackScreen()
        }

        // 顯示初始數值
        updateUI()
    }

    private fun showAdditionalButtons() {
        playButton.visibility = View.VISIBLE
        giftButton.visibility = View.VISIBLE
        chatButton.visibility = View.VISIBLE
    }

    private fun playInteraction() {
        // 播放角色互動動畫或邏輯
        // 更新 UI 或顯示訊息
        messageBox.text = "正在進行互動..."
    }

    private fun giftInteraction() {
        // 送禮邏輯
        messageBox.text = "送禮中..."
    }

    private fun chatInteraction() {
        // 進入聊天界面
        messageBox.text = "開啟聊天..."
    }

    private fun openTaskScreen() {
        // 進入任務界面
        messageBox.text = "開啟任務..."
    }

    private fun openShopScreen() {
        // 進入商城界面
        messageBox.text = "開啟商城..."
    }

    private fun openBackpackScreen() {
        // 進入背包界面
        messageBox.text = "開啟背包..."
    }

    private fun updateUI() {
        // 更新心形進度條、等級、金錢等顯示
        progressBar.progress = 50  // 假設進度為50%
        levelText.text = "Lv. 100"  // 假設等級為100
        moneyAmount.text = "100000"  // 假設金錢為100000
        characterName.text = "俗頭"  // 假設角色名稱為「俗頭」
    }
}
