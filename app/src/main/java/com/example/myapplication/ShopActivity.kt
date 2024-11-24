package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.myapplication.GlobalVariable.Companion.decorate
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class ShopActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack_shop1)
        buttonBack.setOnClickListener {
            // 如果你需要返回特定的主畫面 Activity，而不是僅僅結束當前 Activity
            val intent = Intent(this, GameActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val moneyAmount = findViewById<TextView>(R.id.money_amount)
        fun updateUI() {
            // 更新心形进度条、等级、金钱等显示
            moneyAmount.text = "100000"  // 假设金钱为100000
        }

        val page2Button = findViewById<Button>(R.id.button_page_2)
        page2Button.setOnClickListener {
            // 启动 NewActivity
            val intent = Intent(this, ShopActivity2::class.java)
            startActivity(intent)
        }//跳轉按鈕

        // 獲取每個商品按鈕
        val buttonProduct1: Button = findViewById(R.id.button_product_1)
        buttonProduct1.setOnClickListener {
            if(decorate[0] == 1){
                ShopBuy.showProductEnd(
                    this,  // 當前的 Context（活動或片段）
                    "畢業帽", // 商品名稱
                    "這是一頂畢業帽，非常適合拍照留念！", // 商品描述
                    R.drawable.decoration1_graduation_cap,
                    100
                )
            }else if(decorate[0] == 0) {
                ShopBuy.showProductDialog(
                    this,  // 當前的 Context（活動或片段）
                    "畢業帽", // 商品名稱
                    "這是一頂畢業帽，非常適合拍照留念！", // 商品描述
                    R.drawable.decoration1_graduation_cap,
                    100,
                    onBuyClicked = { isBought ->  // 當 button_buy 被按下時觸發的回調
                        if (isBought) {
                        // 處理按下購買按鈕的邏輯
                            println("購買成功!")
                            decorate[0] = 1
                            sendChangToServer(decorate)
                        } else {
                            // 處理按下取消按鈕的邏輯
                            println("取消購買")
                        }
                    }
                )
            }
        }
        val buttonProduct2: Button = findViewById(R.id.button_product_2)
        buttonProduct2.setOnClickListener {
            if(decorate[1] == 1){
                ShopBuy.showProductEnd(
                    this,  // 當前的 Context（活動或片段）
                    "生日帽", // 商品名稱
                    "戴上這頂生日帽，讓石頭瞬間變成焦點，保證比生日蛋糕還吸引眼球！", // 商品描述
                    R.drawable.decoration2_hbd_hat,
                    100
                )
            }else if(decorate[1] == 0) {
                ShopBuy.showProductDialog(
                    this,  // 當前的 Context（活動或片段）
                    "生日帽", // 商品名稱
                    "戴上這頂生日帽，讓石頭瞬間變成焦點，保證比生日蛋糕還吸引眼球！", // 商品描述
                    R.drawable.decoration2_hbd_hat,
                    100,
                    onBuyClicked = { isBought ->  // 當 button_buy 被按下時觸發的回調
                        if (isBought) {
                            // 處理按下購買按鈕的邏輯
                            println("購買成功!")
                            decorate[1] = 1
                            sendChangToServer(decorate)
                        } else {
                            // 處理按下取消按鈕的邏輯
                            println("取消購買")
                        }
                    }
                )
            }
        }//記得回傳資料
        val buttonProduct3: Button = findViewById(R.id.button_product_3)
        buttonProduct3.setOnClickListener {
            if(decorate[2] == 1){
                ShopBuy.showProductEnd(
                    this,  // 當前的 Context（活動或片段）
                    "貝雷帽", // 商品名稱
                    "戴上這頂貝雷帽，石頭就能瞬間變身藝術家，誰說只有畫家才需要靈感？", // 商品描述
                    R.drawable.decoration3 ,
                    100
                )
            }else if(decorate[2] == 0) {
                ShopBuy.showProductDialog(
                    this,  // 當前的 Context（活動或片段）
                    "貝雷帽", // 商品名稱
                    "戴上這頂貝雷帽，石頭就能瞬間變身藝術家，誰說只有畫家才需要靈感？", // 商品描述
                    R.drawable.decoration3 ,
                    100,
                    onBuyClicked = { isBought ->  // 當 button_buy 被按下時觸發的回調
                        if (isBought) {
                            // 處理按下購買按鈕的邏輯
                            println("購買成功!")
                            decorate[2] = 1
                            sendChangToServer(decorate)
                        } else {
                            // 處理按下取消按鈕的邏輯
                            println("取消購買")
                        }
                    }
                )
            }
        }
        val buttonProduct4: Button = findViewById(R.id.button_product_4)
        buttonProduct4.setOnClickListener {
            if(decorate[3] == 1){
                ShopBuy.showProductEnd(
                    this,  // 當前的 Context（活動或片段）
                    "紅毛帽", // 商品名稱
                    "戴上這頂紅毛帽，讓石頭不僅保暖，還能讓大家誤以為是冬季限定的超級英雄！", // 商品描述
                    R.drawable.decoration4_red_fur_hat,
                    100
                )
            }else if(decorate[3] == 0) {
                ShopBuy.showProductDialog(
                    this,  // 當前的 Context（活動或片段）
                    "紅毛帽", // 商品名稱
                    "戴上這頂紅毛帽，讓石頭不僅保暖，還能讓大家誤以為是冬季限定的超級英雄！", // 商品描述
                    R.drawable.decoration4_red_fur_hat,
                    100,
                    onBuyClicked = { isBought ->  // 當 button_buy 被按下時觸發的回調
                        if (isBought) {
                            // 處理按下購買按鈕的邏輯
                            println("購買成功!")
                            decorate[3] = 1
                            sendChangToServer(decorate)
                        } else {
                            // 處理按下取消按鈕的邏輯
                            println("取消購買")
                        }
                    }
                )
            }
        }
        val buttonProduct5: Button = findViewById(R.id.button_product_5)
        buttonProduct5.setOnClickListener {
            if(decorate[4] == 1){
                ShopBuy.showProductEnd(
                    this,  // 當前的 Context（活動或片段）
                    "蝴蝶結", // 商品名稱
                    "戴上這個蝴蝶結，讓石頭看起來甜美可愛。", // 商品描述
                    R.drawable.decoration5_rosette,
                    100
                )
            }else if(decorate[4] == 0) {
                ShopBuy.showProductDialog(
                    this,  // 當前的 Context（活動或片段）
                    "蝴蝶結", // 商品名稱
                    "戴上這個蝴蝶結，讓石頭看起來甜美可愛。", // 商品描述
                    R.drawable.decoration5_rosette,
                    100,
                    onBuyClicked = { isBought ->  // 當 button_buy 被按下時觸發的回調
                        if (isBought) {
                            // 處理按下購買按鈕的邏輯
                            println("購買成功!")
                            decorate[4] = 1
                            sendChangToServer(decorate)
                        } else {
                            // 處理按下取消按鈕的邏輯
                            println("取消購買")
                        }
                    }
                )
            }
        }
        val buttonProduct6: Button = findViewById(R.id.button_product_6)
        buttonProduct6.setOnClickListener {
            if(decorate[5] == 1){
                ShopBuy.showProductEnd(
                    this,  // 當前的 Context（活動或片段）
                    "蒲公英", // 商品名稱
                    "戴上這顆蒲公英，隨時對石頭許個願——希望風能把你的煩惱吹走！", // 商品描述
                    R.drawable.decoration6_dandelion,
                    100
                )
            }else if(decorate[5] == 0) {
                ShopBuy.showProductDialog(
                    this,  // 當前的 Context（活動或片段）
                    "蒲公英", // 商品名稱
                    "戴上這顆蒲公英，隨時對石頭許個願——希望風能把你的煩惱吹走！", // 商品描述
                    R.drawable.decoration6_dandelion,
                    100,
                    onBuyClicked = { isBought ->  // 當 button_buy 被按下時觸發的回調
                        if (isBought) {
                            // 處理按下購買按鈕的邏輯
                            println("購買成功!")
                            decorate[5] = 1
                            sendChangToServer(decorate)
                        } else {
                            // 處理按下取消按鈕的邏輯
                            println("取消購買")
                        }
                    }
                )
            }
        }
        val buttonProduct7: Button = findViewById(R.id.button_product_7)
        buttonProduct7.setOnClickListener {
            if(decorate[6] == 1){
                ShopBuy.showProductEnd(
                    this,  // 當前的 Context（活動或片段）
                    "火焰", // 商品名稱
                    "讓這團火焰點燃你對石頭的熱情", // 商品描述
                    R.drawable.decoration7_fire,
                    100
                )
            }else if(decorate[6] == 0) {
                ShopBuy.showProductDialog(
                    this,  // 當前的 Context（活動或片段）
                    "火焰", // 商品名稱
                    "讓這團火焰點燃你對石頭的熱情", // 商品描述
                    R.drawable.decoration7_fire,
                    100,
                    onBuyClicked = { isBought ->  // 當 button_buy 被按下時觸發的回調
                        if (isBought) {
                            // 處理按下購買按鈕的邏輯
                            println("購買成功!")
                            decorate[6] = 1
                            sendChangToServer(decorate)
                        } else {
                            // 處理按下取消按鈕的邏輯
                            println("取消購買")
                        }
                    }
                )
            }
        }
        val buttonProduct8: Button = findViewById(R.id.button_product_8)
        buttonProduct8.setOnClickListener {
            if(decorate[7] == 1){
                ShopBuy.showProductEnd(
                    this,  // 當前的 Context（活動或片段）
                    "星光閃閃", // 商品名稱
                    "這款星光閃閃，讓石頭成為夜晚最亮的星!", // 商品描述
                    R.drawable.decoration8_star,
                    100
                )
            }else if(decorate[7] == 0) {
                ShopBuy.showProductDialog(
                    this,  // 當前的 Context（活動或片段）
                    "星光閃閃", // 商品名稱
                    "這款星光閃閃，讓石頭成為夜晚最亮的星!", // 商品描述
                    R.drawable.decoration8_star,
                    100,
                    onBuyClicked = { isBought ->  // 當 button_buy 被按下時觸發的回調
                        if (isBought) {
                            // 處理按下購買按鈕的邏輯
                            println("購買成功!")
                            decorate[7] = 1
                            sendChangToServer(decorate)
                        } else {
                            // 處理按下取消按鈕的邏輯
                            println("取消購買")
                        }
                    }
                )
            }
        }
        val buttonProduct9: Button = findViewById(R.id.button_product_9)
        buttonProduct9.setOnClickListener {
            if(decorate[8] == 1){
                ShopBuy.showProductEnd(
                    this,  // 當前的 Context（活動或片段）
                    "銀杏葉", // 商品名稱
                    "這片銀杏葉，不僅是大自然的藝術品，還能讓石頭擁有'秋天專屬'的時尚感，走在路上像是走進電影鏡頭！", // 商品描述
                    R.drawable.decoration9_leaves,
                    100
                )
            }else if(decorate[8] == 0) {
                ShopBuy.showProductDialog(
                    this,  // 當前的 Context（活動或片段）
                    "銀杏葉", // 商品名稱
                    "這片銀杏葉，不僅是大自然的藝術品，還能讓石頭擁有'秋天專屬'的時尚感，走在路上像是走進電影鏡頭！", // 商品描述
                    R.drawable.decoration9_leaves,
                    100,
                    onBuyClicked = { isBought ->  // 當 button_buy 被按下時觸發的回調
                        if (isBought) {
                            // 處理按下購買按鈕的邏輯
                            println("購買成功!")
                            decorate[8] = 1
                            sendChangToServer(decorate)
                        } else {
                            // 處理按下取消按鈕的邏輯
                            println("取消購買")
                        }
                    }
                )
            }
        }
        val buttonProduct10: Button = findViewById(R.id.button_product_10)
        buttonProduct10.setOnClickListener {
            if(decorate[9] == 1){
                ShopBuy.showProductEnd(
                    this,  // 當前的 Context（活動或片段）
                    "嫩葉", // 商品名稱
                    "這片嫩葉像是大自然的愛心信，能讓石頭感覺到春天的氣息。", // 商品描述
                    R.drawable.decoration10_leaf,
                    100
                )
            }else if(decorate[9] == 0) {
                ShopBuy.showProductDialog(
                    this,  // 當前的 Context（活動或片段）
                    "嫩葉", // 商品名稱
                    "這片嫩葉像是大自然的愛心信，能讓石頭感覺到春天的氣息。", // 商品描述
                    R.drawable.decoration10_leaf,
                    100,
                    onBuyClicked = { isBought ->  // 當 button_buy 被按下時觸發的回調
                        if (isBought) {
                            // 處理按下購買按鈕的邏輯
                            println("購買成功!")
                            decorate[9] = 1
                            sendChangToServer(decorate)
                        } else {
                            // 處理按下取消按鈕的邏輯
                            println("取消購買")
                        }
                    }
                )
            }
        }
    }

    private fun sendChangToServer(decorations: IntArray) {
        // 驗證裝飾品陣列長度
        if (decorations.size != 10) {
            Log.e("ValidationError", "The decorations array must have exactly 10 elements.")
            return
        }

        val client = OkHttpClient()
        val username = GlobalVariable.getName()

        // 構建 JSON 資料
        val json = """
    {
      "username": "$username",
      "decorations": ${decorations.joinToString(prefix = "[", postfix = "]")}
    }
    """.trimIndent()

        // 構建 Request Body
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        // 構建 HTTP POST 請求
        val request = Request.Builder()
            .url("http://140.136.151.129:3000/shop_dec") // 模擬器測試請改用 http://10.0.2.2:3000/shop_dec
            .post(body)
            .build()
    }

    /*private fun sendChangToServer(id: IntArray) {
        val client = OkHttpClient()
        val username = GlobalVariable.getName()

        // 將 IntArray 轉換為 JSON 格式
        val idJson = id.joinToString(prefix = "[", postfix = "]")

        // 構建 JSON 請求資料
        val json = """
    {
      "username": "$username",
      "decoration": $idJson
    }
    """

        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("http://140.136.151.129:3000/shop_dec") // 如果使用模擬器，請使用正確的地址
            .post(body)
            .build()
    }*/
}
