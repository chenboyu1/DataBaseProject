package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    // 定义组件
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

    // 定义好感度
    private var affectionLevel = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)  // 你的 XML 布局文件名称

        // 初始化组件
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

        // 设置按钮点击事件
        interactionButton.setOnClickListener {
            // 显示额外的按钮
            showAdditionalButtons()
        }

        playButton.setOnClickListener {
            // 触发「玩耍」动作，增加好感度等
            playInteraction()
        }

        giftButton.setOnClickListener {
            // 送礼逻辑
            giftInteraction()
        }

        chatButton.setOnClickListener {
            // 跳转到聊天界面
            openChatScreen()
        }

        taskButton.setOnClickListener {
            // 触发「任务」界面
            openTaskScreen()
        }

        shopButton.setOnClickListener {
            // 触发「商城」界面
            openShopScreen()
        }

        backpackButton.setOnClickListener {
            // 触发「背包」界面
            openBackpackScreen()
        }

        // 显示初始数值
        updateUI()
    }

    private fun showAdditionalButtons() {
        playButton.visibility = View.VISIBLE
        giftButton.visibility = View.VISIBLE
        chatButton.visibility = View.VISIBLE
    }

    private fun playInteraction() {
        // 增加好感度
        affectionLevel += 10
        // 更新进度条和消息框
        progressBar.progress = affectionLevel
        messageBox.text = "你和角色玩耍，增加了好感度！当前好感度：$affectionLevel"

        // 隐藏互动按钮
        playButton.visibility = View.GONE
        giftButton.visibility = View.GONE
        chatButton.visibility = View.GONE

        // 更新 UI
        updateUI()
    }

    private fun giftInteraction() {
        // 送礼逻辑
        messageBox.text = "送礼中..."
    }

    private fun chatInteraction() {
        // 进入聊天界面
        messageBox.text = "开启聊天..."
    }

    private fun openChatScreen() {
        // 跳转到聊天界面
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
    }

    private fun openTaskScreen() {
        // 进入任务界面
        messageBox.text = "开启任务..."
    }

    private fun ShopInteraction() {
        // 进入商城界面
        messageBox.text = "开启商城..."
    }

    private fun openShopScreen() {
        // 跳转到聊天界面
        val intent = Intent(this, ShopActivity::class.java)
        startActivity(intent)
    }

    private fun openBackpackScreen() {
        // 进入背包界面
        messageBox.text = "开启背包..."
    }

    private fun updateUI() {
        // 更新心形进度条、等级、金钱等显示
        progressBar.progress = affectionLevel  // 使用当前好感度来更新进度条
        levelText.text = "Lv. 100"  // 假设等级为100
        moneyAmount.text = "100000"  // 假设金钱为100000
        characterName.text = "俗头"  // 假设角色名称为「俗头」
    }
}
