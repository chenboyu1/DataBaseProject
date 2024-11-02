package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class ShopActivity2 : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop2)

        /*val page1Button = findViewById<Button>(R.id.button_page_1)
        page1Button.setOnClickListener {
            // 启动 NewActivity
            val intent = Intent(this, ShopActivity2::class.java)
            startActivity(intent)
        }//跳轉按鈕*/

        // 獲取每個商品按鈕
        val button2Product1: Button = findViewById(R.id.button2_product_1)
        val button2Product2: Button = findViewById(R.id.button2_product_2)
        val button2Product3: Button = findViewById(R.id.button2_product_3)
        val button2Product4: Button = findViewById(R.id.button2_product_4)
        val button2Product5: Button = findViewById(R.id.button2_product_5)
        val button2Product6: Button = findViewById(R.id.button2_product_6)
        val button2Product7: Button = findViewById(R.id.button2_product_7)
        val button2Product8: Button = findViewById(R.id.button2_product_8)
        val button2Product9: Button = findViewById(R.id.button2_product_9)
        val button2Product10: Button = findViewById(R.id.button2_product_10)

        // 設置點擊事件
        button2Product1.setOnClickListener { showPurchaseDialog("商品 1") }
        button2Product2.setOnClickListener { showPurchaseDialog("商品 2") }
        button2Product3.setOnClickListener { showPurchaseDialog("商品 3") }
        button2Product4.setOnClickListener { showPurchaseDialog("商品 4") }
        button2Product5.setOnClickListener { showPurchaseDialog("商品 5") }
        button2Product6.setOnClickListener { showPurchaseDialog("商品 6") }
        button2Product7.setOnClickListener { showPurchaseDialog("商品 7") }
        button2Product8.setOnClickListener { showPurchaseDialog("商品 8") }
        button2Product9.setOnClickListener { showPurchaseDialog("商品 9") }
        button2Product10.setOnClickListener { showPurchaseDialog("商品 10") }
    }

    private fun showPurchaseDialog(productName: String) {
        // 在這裡顯示確認購買的對話框
        Toast.makeText(this, "確認購買 $productName", Toast.LENGTH_SHORT).show()
        // 你可以在這裡啟動自定義的對話框
    }
}
