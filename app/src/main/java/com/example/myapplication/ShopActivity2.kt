package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity

class ShopActivity2 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop2)

        val buttonBack = findViewById<ImageButton>(R.id.buttonBack_shop2)
        buttonBack.setOnClickListener {
            // 如果你需要返回特定的主畫面 Activity，而不是僅僅結束當前 Activity
            val intent = Intent(this, GameActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val page1Button = findViewById<Button>(R.id.button_page_1)
        page1Button.setOnClickListener {
            // 启动 NewActivity
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
        }//跳轉按鈕*/

        // 獲取每個商品按鈕
        val button2Product1: Button = findViewById(R.id.button2_product_1)
        button2Product1.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "刈包", // 商品名稱
                "「包子界的跑車」，外面軟綿綿，裡面卻有滿滿的豬肉、花生粉和酸菜，吃一口就知道，這不是普通的包，是包你開心的包！", // 商品描述
                R.drawable.food1, // 商品圖片
                100.0 // 商品價格
            )
        }
        val button2Product2: Button = findViewById(R.id.button2_product_2)
        button2Product2.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "肥宅快樂水", // 商品名稱
                "喝了之後，你的腦袋會突然覺得“我可以征服世界”，但你的身體卻會告訴你：“好吧，先征服沙發。", // 商品描述
                R.drawable.food2_can_cola, // 商品圖片
                100.0
            )
        }
        val button2Product3: Button = findViewById(R.id.button2_product_3)
        button2Product3.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "義大利麵", // 商品名稱
                "長長的麵條就像人生中的挑戰，一開始亂七八糟，但吃到最後，你就會發現，這一切其實都在醬料的掌控之中！", // 商品描述
                R.drawable.food3_spaghetti, // 商品圖片
                100.0
            )
        }
        val button2Product4: Button = findViewById(R.id.button2_product_4)
        button2Product4.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "甜甜圈", // 商品名稱
                "不僅是早餐，它是一個圓圓的誘惑，外表誘人，內心空虛，吃了讓你瞬間覺得人生充滿希望，兩口下肚，連希望也跟著消失了！", // 商品描述
                R.drawable.food4_sweets_donuts_box,// 商品圖片
                100.0
            )
        }
        val button2Product5: Button = findViewById(R.id.button2_product_5)
        button2Product5.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "布丁", // 商品名稱
                "吃上一口，滑得像是生活中所有的煩惱都被輕輕拂去，結果再吃一口，你就會發現，這種幸福也來得太快，消失得太快了！！", // 商品描述
                R.drawable.food5_sweets_purin, // 商品圖片
                100.0
            )
        }
        val button2Product6: Button = findViewById(R.id.button2_product_6)
        button2Product6.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "馬卡龍", // 商品名稱
                "不僅是一顆小小的甜點，它是法式的浪漫與挑戰——外表脆脆的，內心卻像情感一樣柔軟，吃一口，心情就像馬卡龍的顏色，瞬間豐富多彩！", // 商品描述
                R.drawable.food6_macarons, // 商品圖片
                100.0
            )
        }
        val button2Product7: Button = findViewById(R.id.button2_product_7)
        button2Product7.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "鯛魚燒冰淇淋", // 商品名稱
                "外脆內嫩的鯛魚燒和冰涼的冰淇淋搭配，就像是一場甜品界的愛情故事，開始熱情澎湃，結局卻清涼又美好，吃完只剩下幸福的餘韻，還想再來一個！", // 商品描述
                R.drawable.food7_fish, // 商品圖片
                100.0
            )
        }
        val button2Product8: Button = findViewById(R.id.button2_product_8)
        button2Product8.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "聖代", // 商品名稱
                "冰淇淋界的奢華大餐，滿滿的巧克力醬、果仁和水果層層堆疊，每一口都像是人生中的小確幸，唯一的問題是——吃完後，會不會後悔當初沒再多點一球？", // 商品描述
                R.drawable.food8_sundae, // 商品圖片
                100.0
            )
        }
        val button2Product9: Button = findViewById(R.id.button2_product_9)
        button2Product9.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "在地冰淇淋", // 商品名稱
                "這不僅是冰淇淋，它是「你不小心放生了這顆甜蜜寶寶」，它的命運早已注定，和地板進行了一次不太美好的親密接觸。", // 商品描述
                R.drawable.food9_local_ice, // 商品圖片
                100.0
            )
        }
        val button2Product10: Button = findViewById(R.id.button2_product_10)
        button2Product10.setOnClickListener {
            ShopBuy.showProductDialog(
                this,  // 當前的 Context（活動或片段）
                "豪華餐車", // 商品名稱
                "這輛餐車就是「食物界的百寶箱」，不管你是想吃鹹的、甜的、辣的，還是想挑戰混搭風，這裡應有盡有，讓你每次站在它面前都像選擇自己的命運一樣，滿滿的誘惑，無法抗拒！", // 商品描述
                R.drawable.food10,// 商品圖片
                100.0
            )
        }
    }
}
