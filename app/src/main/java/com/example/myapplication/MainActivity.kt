package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // 啟動背景音樂服務
        val intent = Intent(this, MusicService::class.java)
        startService(intent)

        // 取得 UI 中的元件
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // 註冊按鈕點擊事件
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            if(btnRegister.text == "下一步"){
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    registerUser(username, password)
                    GlobalVariable.setName(username)
                } else {
                    Toast.makeText(this, "請輸入帳號和密碼", Toast.LENGTH_SHORT).show()
                }
            }else {
                val btnLogin = findViewById<Button>(R.id.btnLogin)
                val btnRegister = findViewById<Button>(R.id.btnRegister)
                btnLogin.visibility = View.GONE
                btnRegister.text = "下一步"
            }
        }

        // 登入按鈕點擊事件
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                Toast.makeText(this, "請輸入帳號和密碼", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 註冊用戶的方法
    private fun registerUser(username: String, password: String) {
        val client = OkHttpClient()

        val json = """
        {
          "username": "$username",
          "password": "$password"
        }
        """
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("http://140.136.151.129:3000/register") // 如果使用模擬器，請使用這個地址10.0.2.2
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "註冊失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "註冊成功: $responseBody", Toast.LENGTH_SHORT).show()
                        GlobalVariable.setName(username) //設置全域變數username
                        //透過intent帶其他值給下一頁SecondActivity
                        jumptoActivity(Choosecharac::class.java)
                    } else {
                        Toast.makeText(this@MainActivity, "註冊失敗: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    // 登入用戶的方法
    private fun loginUser(username: String, password: String) {
        val client = OkHttpClient()

        val json = """
        {
          "username": "$username",
          "password": "$password"
        }
        """
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("http://140.136.151.129:3000/login") // 如果使用模擬器，請使用這個地址
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "登入失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "登入成功: $responseBody", Toast.LENGTH_SHORT).show()
                        GlobalVariable.setName(username) //設置全域變數username
                        GlobalScope.launch(Dispatchers.Main) {
                            GlobalVariable.setbasicData()
                            GlobalVariable.setregion()
                            GlobalVariable.setdecorate()
                            GlobalVariable.setfood()
                            GlobalVariable.setmission()
                        }
                        jumptoActivity(GameActivity::class.java)

                    } else {
                        Toast.makeText(this@MainActivity, "登入失敗: $responseBody", Toast.LENGTH_SHORT).show()
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
