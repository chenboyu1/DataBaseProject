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
        R.drawable.jewel02_amethyst, R.drawable.jewel05_emerald,
        R.drawable.jewel08_peridot, R.drawable.jewel10_pink_tourmaline, R.drawable.jewel03_aquamarine
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
    private lateinit var sideboxScroll: ScrollView

    // 定義好感度
    private var affectionLevel = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)  // 你的 XML 布局文件名稱

        val imageView: ImageView = findViewById(R.id.myImageView)
        val decorativeIcon: ImageView = findViewById(R.id.decorativeIcon)

        // 获取 ScrollView 和按钮容器，送禮選項區域
        val sideboxScroll = findViewById<ScrollView>(R.id.sideboxScroll)
        val sidebox = findViewById<LinearLayout>(R.id.sidebox)

        // 初始化組件
        heartIcon = findViewById(R.id.heart_icon)
        progressBar = findViewById(R.id.progressBar)
        levelText = findViewById(R.id.level_text)
        moneyAmount = findViewById(R.id.money_amount)

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

        /*GlobalScope.launch(Dispatchers.Main) {
            // 等待 getcharac() 函式的返回結果
            GlobalVariable.setName("b")
            charac = GlobalVariable.getCharac()
            currentdecorate = GlobalVariable.getcurrentdecorate()
            Log.d("currentdecorate", "$currentdecorate")
            Log.d("charac", "$charac")
            imageView.setImageResource(imgId[charac])  // 顯示角色圖片
            decorativeIcon.setImageResource(decorationId[currentdecorate])  // 顯示裝飾圖片

            for (i in 0 until 10){
                if(food[i] > 0){
                    createDecorativeButton(this@GameActivity, sidebox, i);
                }
            }
        }*/
        // 確保資料在畫面顯示之前已經準備好
        lifecycleScope.launch {
            // 同步更新資料
            GlobalVariable.setName("b")
            GlobalVariable.setCharac()
            charac = GlobalVariable.getCharac()
            GlobalVariable.setcurrentdecorate()
            currentdecorate = GlobalVariable.getcurrentdecorate()

            // 使用更新後的資料來顯示UI
            // 例如設置圖片等
            imageView.setImageResource(imgId[charac])  // 顯示角色圖片
            decorativeIcon.setImageResource(decorationId[currentdecorate])  // 顯示裝飾圖片

            for (i in 0 until 10){
                if(food[i] > 0){
                    createDecorativeButton(this@GameActivity, sidebox, i);
                }
            }
        }

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

        // 顯示初始數值
        //updateUI()
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
    fun createDecorativeButton(context: Context, parentLayout: LinearLayout, id: Int) {
        val button = Button(context)
        button.id = id
        // Set up layout parameters
        button.layoutParams = LinearLayout.LayoutParams(
            225.dpToPx(),  // Button width
            223.dpToPx()   // Button height
        ).apply {
            gravity = Gravity.CENTER  // Center the button within its parent
        }

        // Set button background resource
        button.setBackgroundResource(foodId[id])

        // Add the button to the parent layout
        parentLayout.addView(button)

    }
    fun sendSelectedButtonToServer(id: Int) {
        val client = OkHttpClient()
        val username = GlobalVariable.getName()
        val json = """
        {
          "username": "$username",
          "decoration": $id
        }
        """
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("http://10.0.2.2:3000/decoration") // 如果使用模擬器，請使用這個地址10.0.2.2，140.136.151.129
            .post(body)
            .build()
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
        /*// 顯示送禮選項布局
        giftOptionsLayout.visibility = View.VISIBLE*/
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
        /*giftOptionsLayout.visibility = View.GONE*/
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
        /*// 跳轉到商城界面
        val intent = Intent(this, ShopActivity::class.java)
        startActivity(intent)*/
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
    }
}
