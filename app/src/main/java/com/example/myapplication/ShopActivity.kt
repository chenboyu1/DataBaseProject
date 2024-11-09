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
        val buttonProduct2: Button = findViewById(R.id.button_product_2)
        val buttonProduct3: Button = findViewById(R.id.button_product_3)
        val buttonProduct4: Button = findViewById(R.id.button_product_4)
        val buttonProduct5: Button = findViewById(R.id.button_product_5)
        val buttonProduct6: Button = findViewById(R.id.button_product_6)
        val buttonProduct7: Button = findViewById(R.id.button_product_7)
        val buttonProduct8: Button = findViewById(R.id.button_product_8)
        val buttonProduct9: Button = findViewById(R.id.button_product_9)
        val buttonProduct10: Button = findViewById(R.id.button_product_10)

        // 設置點擊事件
        buttonProduct1.setOnClickListener { showPurchaseDialog("商品 1") }
        buttonProduct2.setOnClickListener { showPurchaseDialog("商品 2") }
        buttonProduct3.setOnClickListener { showPurchaseDialog("商品 3") }
        buttonProduct4.setOnClickListener { showPurchaseDialog("商品 4") }
        buttonProduct5.setOnClickListener { showPurchaseDialog("商品 5") }
        buttonProduct6.setOnClickListener { showPurchaseDialog("商品 6") }
        buttonProduct7.setOnClickListener { showPurchaseDialog("商品 7") }
        buttonProduct8.setOnClickListener { showPurchaseDialog("商品 8") }
        buttonProduct9.setOnClickListener { showPurchaseDialog("商品 9") }
        buttonProduct10.setOnClickListener { showPurchaseDialog("商品 10") }
    }

    private fun showPurchaseDialog(productName: String) {
        // 在這裡顯示確認購買的對話框
        Toast.makeText(this, "確認購買 $productName", Toast.LENGTH_SHORT).show()
        // 你可以在這裡啟動自定義的對話框
    }
}
