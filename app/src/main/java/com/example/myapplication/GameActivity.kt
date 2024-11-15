package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    // 定義組件
    private lateinit var heartIcon: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var levelText: TextView
    private lateinit var moneyAmount: TextView
    private lateinit var characterName: TextView

    private lateinit var interactionButton: ImageButton
    private lateinit var playButton: ImageButton
    private lateinit var playText: TextView
    private lateinit var giftButton: ImageButton
    private lateinit var giftText: TextView
    private lateinit var chatButton: ImageButton
    private lateinit var chatText: TextView

    private lateinit var taskButton: ImageButton
    private lateinit var shopButton: ImageButton
    private lateinit var backpackButton: ImageButton
    private lateinit var messageBox: TextView

    // 定義送禮選項按鈕布局及按鈕
    private lateinit var giftOptionsLayout: LinearLayout
    private lateinit var giftFlowerButton: ImageButton
    private lateinit var giftChocolateButton: ImageButton
    private lateinit var giftToyButton: ImageButton

    // 定義好感度
    private var affectionLevel = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)  // 你的 XML 布局文件名稱

        // 初始化組件
        heartIcon = findViewById(R.id.heart_icon)
        progressBar = findViewById(R.id.progressBar)
        levelText = findViewById(R.id.level_text)
        moneyAmount = findViewById(R.id.money_amount)
        characterName = findViewById(R.id.character_name)

        interactionButton = findViewById(R.id.interaction_button)
        playButton = findViewById(R.id.play_button)
        playText = findViewById(R.id.play_text)
        giftButton = findViewById(R.id.gift_button)
        giftText = findViewById(R.id.gift_text)
        chatButton = findViewById(R.id.chat_button)
        chatText = findViewById(R.id.chat_text)

        taskButton = findViewById(R.id.task_button)
        shopButton = findViewById(R.id.shop_button)
        backpackButton = findViewById(R.id.backpack_button)

        messageBox = findViewById(R.id.message_box)

        // 初始化送禮選項按鈕
        giftOptionsLayout = findViewById(R.id.gift_options_layout)
        giftFlowerButton = findViewById(R.id.gift_flower_button)
        giftChocolateButton = findViewById(R.id.gift_chocolate_button)
        giftToyButton = findViewById(R.id.gift_toy_button)

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
            // 顯示送禮選項
            showGiftOptions()
        }

        chatButton.setOnClickListener {
            // 跳轉到聊天界面
            openChatScreen()
        }

        taskButton.setOnClickListener {
            // 跳轉到任務界面
            openTaskScreen()
        }

        shopButton.setOnClickListener {
            // 跳轉到商城界面
            openShopScreen()
        }

        backpackButton.setOnClickListener {
            // 跳轉到背包界面
            openBackpackScreen()
        }

        // 設置送禮選項按鈕的點擊事件
        giftFlowerButton.setOnClickListener {
            selectGift("flower")
        }

        giftChocolateButton.setOnClickListener {
            selectGift("chocolate")
        }

        giftToyButton.setOnClickListener {
            selectGift("toy")
        }

        // 顯示初始數值
        updateUI()
    }

    private fun showAdditionalButtons() {
        playButton.visibility = View.VISIBLE
        giftButton.visibility = View.VISIBLE
        chatButton.visibility = View.VISIBLE
        playText.visibility = View.VISIBLE
        giftText.visibility = View.VISIBLE
        chatText.visibility = View.VISIBLE
    }

    private fun hideAdditionalButtons() {
        playButton.visibility = View.GONE
        giftButton.visibility = View.GONE
        chatButton.visibility = View.GONE
        playText.visibility = View.GONE
        giftText.visibility = View.GONE
        chatText.visibility = View.GONE
    }

    private fun playInteraction() {
        // 增加好感度
        affectionLevel += 10
        // 更新進度條和訊息框
        progressBar.progress = affectionLevel
        messageBox.text = getString(R.string.play_interaction_message, affectionLevel)

        // 隱藏互動按鈕
        hideAdditionalButtons()

        // 更新 UI
        updateUI()
    }

    private fun showGiftOptions() {
        // 顯示送禮選項布局
        giftOptionsLayout.visibility = View.VISIBLE
        // 隱藏主要的互動按鈕
        hideAdditionalButtons()
        // 清空訊息框
        messageBox.text = ""
    }

    private fun selectGift(giftType: String) {
        when (giftType) {
            "flower" -> {
                affectionLevel += 10
                messageBox.text = getString(R.string.gift_option_flower)
            }
            "chocolate" -> {
                affectionLevel += 10
                messageBox.text = getString(R.string.gift_option_chocolate)
            }
            "toy" -> {
                affectionLevel += 10
                messageBox.text = getString(R.string.gift_option_toy)
            }
        }
        // 更新進度條
        progressBar.progress = affectionLevel
        // 隱藏送禮選項
        giftOptionsLayout.visibility = View.GONE
        // 更新 UI
        updateUI()
    }

    private fun openChatScreen() {
        // 跳轉到聊天界面
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
    }

    private fun openTaskScreen() {
        // 跳轉到任務界面
        val intent = Intent(this, mission::class.java) // 假設任務活動名為 MissionActivity
        startActivity(intent)
    }

    private fun openShopScreen() {
        // 跳轉到商城界面
        val intent = Intent(this, ShopActivity::class.java)
        startActivity(intent)
    }

    private fun openBackpackScreen() {
        // 跳轉到背包界面
        val intent = Intent(this, PackageActivity::class.java) // 假設背包活動名為 PackageActivity
        startActivity(intent)
    }

    private fun updateUI() {
        // 更新心形進度條、等級、金錢等顯示
        progressBar.progress = affectionLevel  // 使用當前好感度來更新進度條
        levelText.text = getString(R.string.level_text)  // 通過字符串資源動態顯示等級
        moneyAmount.text = getString(R.string.money_amount)  // 通過字符串資源動態顯示金錢
        characterName.text = getString(R.string.character_name)  // 通過字符串資源動態顯示角色名稱
    }
}
