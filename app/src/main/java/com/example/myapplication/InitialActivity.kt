package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException

data class User(
    val username: String,
    val password: String,
    val region: String
)

interface ApiService {
    @POST("your/endpoint/here")
    fun submitUserData(@Body user: User): Call<Void>
}

class InitialActivity : ComponentActivity() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        val intent = intent
        val username = intent.getStringExtra("EXTRA_USERNAME")
        val password = intent.getStringExtra("EXTRA_PASSWORD")
        // 獲取 Spinner 的參考
        val spinner: Spinner = findViewById(R.id.spinner)

        // 定義下拉選項
        val options = arrayOf("台北市", "新北市", "基隆市", "桃園市", "新竹市", "新竹縣", "苗栗縣", "台中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "台南市", "高雄市", "屏東縣", "台東縣", "花蓮縣", "宜蘭縣", "澎湖縣", "金門縣", "連江縣")

        // 創建適配器
        val adapter = ArrayAdapter(this, R.layout.spinner_item, options)

        // 設置適配器
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.adapter = adapter

        // 設置選項改變的監聽器
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedRegion = parent.getItemAtPosition(position).toString().apply {
                    //val user = User(username, password, selectedRegion)
                    saveRegion(username.toString(), password.toString(), this)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // 沒有選擇的情況
            }
        }
    }
    private fun saveRegion(username: String, password: String, region: String) {
        val client = OkHttpClient()

        val json = """
        {
          "username": "$username",
          "password": "$password",
          "region": "$region"
        }
        """
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("http://10.0.2.2:3000/region") // 如果使用模擬器，請使用這個地址
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@InitialActivity, "註冊失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@InitialActivity, "註冊成功: $responseBody", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@InitialActivity, "註冊失敗: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}