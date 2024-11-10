package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class ShopActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val page2Button = findViewById<Button>(R.id.button_page_2)
        page2Button.setOnClickListener {
            // 启动 NewActivity
            val intent = Intent(this, ShopActivity2::class.java)
            startActivity(intent)
        }//跳轉按鈕

        // 獲取每個商品按鈕
        val buttonProduct1: Button = findViewById(R.id.button_product_1)
        buttonProduct1.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "畢業帽", // 商品名稱
                "這是一頂畢業帽，非常適合拍照留念！", // 商品描述
                R.drawable.decoration1_graduation_cap // 商品圖片
            )
        }
        val buttonProduct2: Button = findViewById(R.id.button_product_2)
        buttonProduct2.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "生日帽", // 商品名稱
                "戴上這頂生日帽，讓石頭瞬間變成焦點，保證比生日蛋糕還吸引眼球！", // 商品描述
                R.drawable.decoration2_hbd_hat // 商品圖片
            )
        }
        val buttonProduct3: Button = findViewById(R.id.button_product_3)
        buttonProduct3.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "貝雷帽", // 商品名稱
                "戴上這頂貝雷帽，石頭就能瞬間變身藝術家，誰說只有畫家才需要靈感？", // 商品描述
                R.drawable.decoration3 // 商品圖片
            )
        }
        val buttonProduct4: Button = findViewById(R.id.button_product_4)
        buttonProduct4.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "紅毛帽", // 商品名稱
                "戴上這頂紅毛帽，讓石頭不僅保暖，還能讓大家誤以為是冬季限定的超級英雄！", // 商品描述
                R.drawable.decoration4_red_fur_hat // 商品圖片
            )
        }
        val buttonProduct5: Button = findViewById(R.id.button_product_5)
        buttonProduct5.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "蝴蝶結", // 商品名稱
                "戴上這個蝴蝶結，讓石頭看起來甜美可愛。", // 商品描述
                R.drawable.decoration5_rosette // 商品圖片
            )
        }
        val buttonProduct6: Button = findViewById(R.id.button_product_6)
        buttonProduct6.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "蒲公英", // 商品名稱
                "戴上這顆蒲公英，隨時對石頭許個願——希望風能把你的煩惱吹走！", // 商品描述
                R.drawable.decoration6_dandelion // 商品圖片
            )
        }
        val buttonProduct7: Button = findViewById(R.id.button_product_7)
        buttonProduct7.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "火焰", // 商品名稱
                "讓這團火焰點燃你對石頭的熱情", // 商品描述
                R.drawable.decoration7_fire // 商品圖片
            )
        }
        val buttonProduct8: Button = findViewById(R.id.button_product_8)
        buttonProduct8.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "星光閃閃", // 商品名稱
                "這款星光閃閃，讓石頭成為夜晚最亮的星!", // 商品描述
                R.drawable.decoration8_star // 商品圖片
            )
        }
        val buttonProduct9: Button = findViewById(R.id.button_product_9)
        buttonProduct9.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "銀杏葉", // 商品名稱
                "這片銀杏葉，不僅是大自然的藝術品，還能讓石頭擁有'秋天專屬'的時尚感，走在路上像是走進電影鏡頭！", // 商品描述
                R.drawable.decoration9_leaves // 商品圖片
            )
        }
        val buttonProduct10: Button = findViewById(R.id.button_product_10)
        buttonProduct10.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "嫩葉", // 商品名稱
                "這片嫩葉像是大自然的愛心信，能讓石頭感覺到春天的氣息。", // 商品描述
                R.drawable.decoration10_leaf // 商品圖片
            )
        }
    }
}
