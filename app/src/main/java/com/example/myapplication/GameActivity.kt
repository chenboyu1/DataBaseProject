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

//新增的，連結後端等功能
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Button
import android.widget.ScrollView
import android.widget.HorizontalScrollView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.GlobalVariable.Companion.decorate
import com.example.myapplication.GlobalVariable.Companion.food //要引用
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import androidx.lifecycle.lifecycleScope

class GameActivity : AppCompatActivity() {
    var charac = 0;
    var currentdecorate = 0;
    var imgId = arrayOf(
        R.drawable.jewel02_amethyst, R.drawable.jewel03_aquamarine, R.drawable.jewel05_emerald,
        R.drawable.jewel06_moonstone, R.drawable.jewel08_peridot, R.drawable.jewel10_pink_tourmaline
        , R.drawable.jewel12_tanzanite, R.drawable.jewel15_colorful);
    var decorationId = arrayOf(
        R.drawable.decoration1_graduation_cap, R.drawable.decoration2_hbd_hat, R.drawable.decoration3,
        R.drawable.decoration4_red_fur_hat, R.drawable.decoration5_rosette, R.drawable.decoration6_dandelion
        , R.drawable.decoration7_fire, R.drawable.decoration8_star, R.drawable.decoration9_leaves,
        R.drawable.decoration10_leaf);
    var foodId = arrayOf(
        R.drawable.food1, R.drawable.food2_can_cola, R.drawable.food3_spaghetti,
        R.drawable.food4_sweets_donuts_box, R.drawable.food5_sweets_purin, R.drawable.food6_macarons
        , R.drawable.food7_fish, R.drawable.food8_sundae, R.drawable.food9_local_ice,
        R.drawable.food10);
    var foodName = arrayOf(
        "刈包", "肥宅快樂水", "義大利麵", "甜甜圈", "布丁", "馬卡龍", "鯛魚燒冰淇淋", "聖代", "在地冰淇淋", "豪華餐車");

    // 定義組件
    private lateinit var heartIcon: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var levelText: TextView
    private lateinit var moneyAmount: TextView

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
    private lateinit var sideboxScroll: HorizontalScrollView

    // 定義好感度
    private var affectionLevel = 0
    //獲取資料庫金錢
    var moneynumber = GlobalVariable.getmoney()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)  // 你的 XML 布局文件名稱

        val imageView: ImageView = findViewById(R.id.myImageView)
        val decorativeIcon: ImageView = findViewById(R.id.decorativeIcon)
        val country = GlobalVariable.getcountry()
        val region = GlobalVariable.getregion()

        // 获取 ScrollView 和按钮容器，送禮選項區域
        sideboxScroll = findViewById<HorizontalScrollView>(R.id.sideboxScroll)
        val sidebox = findViewById<LinearLayout>(R.id.sidebox)

        // 初始化組件
        heartIcon = findViewById(R.id.heart_icon)
        progressBar = findViewById(R.id.progressBar)
        levelText = findViewById(R.id.level_text)
        //moneyAmount = findViewById(R.id.money_amount)
        moneyAmount = findViewById<TextView>(R.id.money_amount)

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

        // 確保資料在畫面顯示之前已經準備好
        lifecycleScope.launch {
            // 同步更新資料
            charac = GlobalVariable.getCharac()
            currentdecorate = GlobalVariable.getcurrentdecorate()

            // 使用更新後的資料來顯示UI
            imageView.setImageResource(imgId[charac])  // 顯示角色圖片
            if(currentdecorate == 99) decorativeIcon.visibility = View.GONE
            else decorativeIcon.setImageResource(decorationId[currentdecorate])  // 顯示裝飾圖片

            val buttonsToCreate = (0 until 10).filter { food[it] > 0 }
            buttonsToCreate.forEach { i ->
                createDecorativeButton(this@GameActivity, sidebox, i)
            }
        }


        var open = 1;
        // 設置按鈕點擊事件
        interactionButton.setOnClickListener {
            // 顯示額外的按鈕
            open *= -1
            if(open == -1)showAdditionalButtons()
            else hideAdditionalButtons()
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
            jumptoActivity(ChatActivity::class.java)
        }

        taskButton.setOnClickListener {
            // 跳轉到任務界面
            jumptoActivity(mission::class.java)
        }

        shopButton.setOnClickListener {
            // 跳轉到商城界面
            jumptoActivity(ShopActivity::class.java)
        }

        backpackButton.setOnClickListener {
            // 跳轉到背包界面
            jumptoActivity(PackageActivity::class.java)
        }

        // 顯示初始數值
        updateUI()
    }

    // 跳轉到界面
    private fun jumptoActivity(targetActivity: Class<*>) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    fun createDecorativeButton(context: Context, parentLayout: LinearLayout, id: Int) {
        // 外層 LinearLayout 用來排列按鈕和數量文字
        val buttonContainer = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL // 垂直排列
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // 設置按鈕
        val button = Button(context).apply {
            this.id = id

            // 設置按鈕大小為正方形
            layoutParams = LinearLayout.LayoutParams(
                150.dpToPx(),  // 按鈕寬度
                150.dpToPx()   // 按鈕高度與寬度相同，保持正方形
            ).apply {
                marginEnd = 8.dpToPx() // 設置按鈕之間的間距
                marginStart = 8.dpToPx()
            }

            // 設置背景圖片
            setBackgroundResource(foodId[id])

            // 設置按鈕的文字為食物數量
            text = ""

            // 設置按鈕點擊事件
            setOnClickListener {
                val currentQuantity = food[id] ?: 0
                if (currentQuantity > 0) {
                    food[id] = currentQuantity - 1
                    text = "剩餘數量：${food[id]}"
                    if (food[id] == 0) {
                        parentLayout.removeView(buttonContainer) // 移除數量為 0 的按鈕
                    }
                }
            }
        }

        // 顯示食物名稱的 TextView
        val quantityText = TextView(context).apply {
            text = "${foodName[id]}"  // 顯示食物數量
            gravity = Gravity.CENTER // 文字居中
        }

        // 把按鈕和數量顯示放進一個垂直排列的 LinearLayout 中
        buttonContainer.addView(button)
        buttonContainer.addView(quantityText)

        // 最後將整個按鈕容器加入父布局
        parentLayout.addView(buttonContainer)
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
        // 隱藏其他 UI，顯示送禮選項
        messageBox.visibility = View.GONE
        sideboxScroll.visibility = View.VISIBLE
        hideAdditionalButtons()

        // 更新禮物選項
        updateGiftButtons()
    }


    private fun updateGiftButtons() {
        val sidebox = findViewById<LinearLayout>(R.id.sidebox)

        // 確保只移除數量為 0 的按鈕，並更新其他按鈕的數量
        for (i in 0 until foodId.size) {
            val button = sidebox.findViewById<Button>(i) // 根據 ID 尋找現有按鈕
            val foodQuantity = food[i] ?: 0

            if (foodQuantity > 0) {
                if (button == null) {
                    // 如果按鈕不存在但數量 > 0，新增按鈕
                    createDecorativeButton(this, sidebox, i)
                } else {
                    // 更新按鈕文字
                    button.text = "食物 $i：$foodQuantity"
                }
            } else {
                // 如果數量為 0，移除按鈕
                button?.let { sidebox.removeView(it) }
            }
        }
    }


    private fun updateUI() {
        // 更新心形進度條、等級、金錢等顯示
        progressBar.progress = affectionLevel  // 使用當前好感度來更新進度條
        levelText.text = getString(R.string.level_text)  // 通過字符串資源動態顯示等級
        moneyAmount.text = moneynumber.toString()  // 通過字符串資源動態顯示金錢
    }
}
