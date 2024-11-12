package com.example.myapplication

import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class PackageActivity : ComponentActivity() {
    val values = IntArray(10)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package)
        val imageView: ImageView = findViewById(R.id.myImageView)
        val decorativeIcon: ImageView = findViewById(R.id.decorativeIcon)
        // 获取 ScrollView 和按钮容器
        val sideboxScroll = findViewById<ScrollView>(R.id.sideboxScroll)
        val sidebox = findViewById<LinearLayout>(R.id.sidebox)

        fetchData()
        if(values[0]==1){
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                225.dpToPx(), // 按鈕寬度
                223.dpToPx() // 按鈕高度
            ).apply {
                gravity = Gravity.CENTER // 將按鈕置中
            }
            button.setBackgroundResource(R.drawable.decoration1_graduation_cap)
            sidebox.addView(button)
        }else{
            //Toast.makeText(this@PackageActivity, "${values[0]}${values[1]}${values[2]}${values[3]}", Toast.LENGTH_SHORT).show()
        }
        if(values[1]==1){
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                225.dpToPx(), // 按鈕寬度
                223.dpToPx() // 按鈕高度
            ).apply {
                gravity = Gravity.CENTER // 將按鈕置中
            }
            button.setBackgroundResource(R.drawable.decoration2_hbd_hat)
            sidebox.addView(button)
            button.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.alpha = 0.5f // 按下时按钮变得透明
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        v.alpha = 1f // 松开或取消时恢复透明度
                    }
                }
                decorativeIcon.setImageResource(R.drawable.decoration2_hbd_hat)
                false
            }
        }
        if(values[2]==1){
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                225.dpToPx(), // 按鈕寬度
                223.dpToPx() // 按鈕高度
            ).apply {
                gravity = Gravity.CENTER // 將按鈕置中
            }
            button.setBackgroundResource(R.drawable.decoration3)
            sidebox.addView(button)
            button.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.alpha = 0.5f // 按下时按钮变得透明
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        v.alpha = 1f // 松开或取消时恢复透明度
                    }
                }
                decorativeIcon.setImageResource(R.drawable.decoration3)
                false
            }
        }
        if(values[3]==1){
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                225.dpToPx(), // 按鈕寬度
                223.dpToPx() // 按鈕高度
            ).apply {
                gravity = Gravity.CENTER // 將按鈕置中
            }
            button.setBackgroundResource(R.drawable.decoration4_red_fur_hat)
            sidebox.addView(button)
        }

// 动态生成按钮
        /*val buttonCount = 20 // 你可以通过变量动态调整按钮数量
        for (i in 0 until buttonCount) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                225.dpToPx(), // 按鈕寬度
                223.dpToPx() // 按鈕高度
            )

            sidebox.addView(button)
        }*/

    }
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    fun fetchData() {
        val client = OkHttpClient()
        val username = 'a';//GlobalVariable.getName();
        val request = Request.Builder()
            .url("http://140.136.151.129:3000/package?username=$username")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@PackageActivity, "第一種失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val responseBody = response.body?.string() // 先保存response body，避免多次調用

                runOnUiThread {
                    if (response.isSuccessful && responseBody != null) {
                        Toast.makeText(this@PackageActivity, "成功: $responseBody", Toast.LENGTH_SHORT).show()
                        try {
                            val jsonObject = JSONObject(responseBody)
                            for (i in 0 until 10) {
                                values[i] = jsonObject.getInt("decorate${i + 1}")
                            }
                            Toast.makeText(this@PackageActivity, "${values[0]}${values[1]}${values[2]}${values[3]}", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            println("解析回應資料時出錯: ${e.message}")
                        }
                    } else {
                        println("無法取得資料，狀態碼: ${response.code}，訊息: ${response.message}")
                        Toast.makeText(this@PackageActivity, "第二種失敗: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
