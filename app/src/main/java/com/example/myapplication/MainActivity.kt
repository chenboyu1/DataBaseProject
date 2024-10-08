package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 取得 UI 中的元件
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // 註冊按鈕點擊事件
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                registerUser(username, password)
            } else {
                Toast.makeText(this, "請輸入帳號和密碼", Toast.LENGTH_SHORT).show()
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
            .url("http://10.0.2.2:3000/register") // 如果使用模擬器，請使用這個地址
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
                        //透過intent帶其他值給下一頁SecondActivity
                        JumptoActivity(InitialActivity::class.java, username, password)
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
            .url("http://10.0.2.2:3000/login") // 如果使用模擬器，請使用這個地址
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
                        JumptoActivity(PackageActivity::class.java, username, password)
                    } else {
                        Toast.makeText(this@MainActivity, "登入失敗: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun JumptoActivity(targetActivity: Class<*>, username: String, password: String) {
        val intent = Intent(this, InitialActivity::class.java).apply {
            putExtra("EXTRA_USERNAME", username)
            putExtra("EXTRA_PASSWORD", password)
        }
        startActivity(intent)
    }
}
