package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.myapplication.GlobalVariable.Companion.decorate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
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
        R.drawable.decoration10_leaf)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package)
        val imageView: ImageView = findViewById(R.id.myImageView)
        val decorativeIcon: ImageView = findViewById(R.id.decorativeIcon)
        // 获取 ScrollView 和按钮容器
        val sideboxScroll = findViewById<ScrollView>(R.id.sideboxScroll)
        val sidebox = findViewById<LinearLayout>(R.id.sidebox)
        val retbtn = findViewById<ImageButton>(R.id.retbtn)

        GlobalScope.launch(Dispatchers.Main) {
            // 等待 getcharac() 函式的返回結果
            charac = GlobalVariable.getCharac()
            currentdecorate = GlobalVariable.getcurrentdecorate()
            Log.d("currentdecorate", "$currentdecorate")
            Log.d("charac", "$charac")
            imageView.setImageResource(imgId[charac])
            if(currentdecorate == 99) decorativeIcon.visibility = View.GONE
            else decorativeIcon.setImageResource(decorationId[currentdecorate])
        }
        for (i in 0 until 10){
            if(decorate[i] == 1){
                createDecorativeButton(this, sidebox, i);
            }
        }
        retbtn.setOnClickListener {
            jumptoActivity(GameActivity::class.java)
        }
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
        button.setBackgroundResource(decorationId[id])

        // Add the button to the parent layout
        parentLayout.addView(button)

        // Set the touch listener to handle opacity changes on touch events
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.alpha = 0.5f  // Make the button semi-transparent when pressed
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.alpha = 1f  // Restore opacity when released or canceled
                }
            }
            sendSelectedButtonToServer(id)
            GlobalVariable.setcurrentdecorate(id)
            val decorativeIcon: ImageView = findViewById(R.id.decorativeIcon)
            decorativeIcon.visibility = View.VISIBLE
            // Set the decoration icon image
            decorativeIcon.setImageResource(decorationId[button.id])
            button.text = "已選擇"
            // Return false to allow other touch events to be processed
            false
        }
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
        Log.d("sendtoback", json)
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("http://140.136.151.129:3000/decoration") // 如果使用模擬器，請使用這個地址
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@PackageActivity, "請求失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@PackageActivity, "請求成功: $responseBody", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@PackageActivity, "伺服器錯誤: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
    private fun jumptoActivity(targetActivity: Class<*>) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
    }
}
