package com.example.myapplication

import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException


class Choosecharac : ComponentActivity() {
    var imgId: IntArray = intArrayOf(
        R.drawable.jewel02_amethyst, R.drawable.jewel05_emerald,
        R.drawable.jewel08_peridot, R.drawable.jewel10_pink_tourmaline, R.drawable.jewel03_aquamarine
        , R.drawable.jewel12_tanzanite, R.drawable.jewel15_colorful);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choosecharac)

        // 獲取按鈕容器的引用
        val buttonContainer = findViewById<LinearLayout>(R.id.sidebox)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER // 將按鈕置中
        }
        // 假設這是你希望生成的按鈕數量
        val buttonCount = 7 // 可以根據需要修改這個數值

        // 使用迴圈根據數量生成按鈕
        for (i in 0 until buttonCount) {
            val button = ImageButton(this)
            button.id = i
            button.layoutParams = params
            button.layoutParams = LinearLayout.LayoutParams(
                200.dpToPx(), // 按鈕寬度
                200.dpToPx() // 按鈕高度
            ).apply {
                gravity = Gravity.CENTER // 將按鈕置中
            }
            //button.background = resources.getDrawable(R.drawable.background, null) // 設定背景
            button.setImageResource(imgId[i])
            button.setOnClickListener {
                val selectedButtonId = button.id // 取得按鈕 ID 或其他資訊
                Toast.makeText(this, "按鈕 $selectedButtonId 已被點擊", Toast.LENGTH_SHORT).show()
                sendSelectedButtonToServer(selectedButtonId.toString()) // 傳遞到後端
            }
            // 將按鈕添加到容器中
            buttonContainer.addView(button)
        }
    }

    // 擴展函數將 dp 轉換為 px
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    private fun sendSelectedButtonToServer(charac: String) {
        val client = OkHttpClient()
        val username = GlobalVariable.getName()
        val json = """
        {
          "username": "$username",
          "charac": "$charac"
        }
        """
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("http://10.0.2.2:3000/charac") // 如果使用模擬器，請使用這個地址
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@Choosecharac, "註冊失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@Choosecharac, "註冊成功: $responseBody", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@Choosecharac, "註冊失敗: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
