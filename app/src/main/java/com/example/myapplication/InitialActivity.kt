package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException



class InitialActivity : ComponentActivity() {
    private lateinit var spinnerCountry: Spinner
    private lateinit var spinnerRegion: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        // 獲取 Spinner 的參考
        spinnerCountry = findViewById(R.id.spinner_country)
        spinnerRegion = findViewById(R.id.spinner_region)
        // 定義下拉選項
        val countries = listOf("請選擇","臺北市", "新北市", "基隆市", "桃園市", "新竹市", "新竹縣", "苗栗縣", "臺中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "臺南市", "高雄市", "屏東縣", "臺東縣", "花蓮縣", "宜蘭縣", "澎湖縣", "金門縣", "連江縣")
        val citiesByCountry = mapOf(
            "基隆市" to listOf("七堵區", "中山區", "中正區", "仁愛區", "安樂區"),
            "臺北市" to listOf("中正區", "北投區", "南港區", "士林區", "大安區", "文山區"),
            "新北市" to listOf(
                "三重區", "八里區", "土城區", "坪林區", "平溪區", "新店區", "樹林區", "汐止區",
                "泰山區", "淡水區", "烏來區", "瑞芳區", "石碇區", "石門區", "貢寮區", "鶯歌區"
            ),
            "桃園市" to listOf(
                "中壢區", "八德區", "大園區", "大溪區", "平鎮區", "復興區", "新屋區",
                "桃園區", "楊梅區", "蘆竹區", "龍潭區"
            ),
            "新竹市" to listOf("東區", "香山區"),
            "新竹縣" to listOf("五峰鄉", "寶山鄉", "新埔鎮", "新豐鄉", "湖口鄉", "竹北市", "關西鎮"),
            "苗栗縣" to listOf(
                "三義鄉", "公館鄉", "大湖鄉", "後龍鎮", "竹南鎮", "苑裡鎮",
                "西湖鄉", "通霄鎮", "造橋鄉", "銅鑼鄉", "頭份市", "頭屋鄉"
            ),
            "臺中市" to listOf(
                "北區", "后里區", "和平區", "大甲區", "大肚區", "太平區",
                "新社區", "梧棲區", "沙鹿區", "清水區", "神岡區", "西屯區",
                "霧峰區", "龍井區"
            ),
            "彰化縣" to listOf(
                "北斗鎮", "和美鎮", "大城鄉", "大村鄉", "彰化市",
                "田中鎮", "芳苑鄉", "鹿港鎮"
            ),
            "南投縣" to listOf(
                "中寮鄉", "仁愛鄉", "信義鄉", "名間鄉", "竹山鎮",
                "草屯鎮", "魚池鄉", "鹿谷鄉"
            ),
            "雲林縣" to listOf(
                "二崙鄉", "口湖鄉", "古坑鄉", "四湖鄉", "斗南鎮",
                "林內鄉", "臺西鄉", "虎尾鎮", "西螺鎮", "麥寮鄉"
            ),
            "嘉義市" to listOf("東區", "西區"),
            "嘉義縣" to listOf(
                "中埔鄉", "大埔鄉", "大林鎮", "布袋鎮", "東石鄉",
                "梅山鄉", "民雄鄉", "水上鄉", "溪口鄉", "竹崎鄉",
                "義竹鄉", "阿里山鄉", "鹿草鄉"
            ),
            "臺南市" to listOf(
                "七股區", "中西區", "六甲區", "北門區", "善化區", "安南區",
                "官田區", "後壁區", "新化區", "新市區", "新營區",
                "楠西區", "永康區", "玉井區", "白河區", "關廟區", "麻豆區"
            ),
            "高雄市" to listOf(
                "仁武區", "六龜區", "大樹區", "岡山區", "旗山區", "旗津區",
                "杉林區", "楠梓區", "橋頭區", "田寮區", "甲仙區",
                "茂林區", "路竹區", "鳳山區"
            ),
            "屏東縣" to listOf(
                "南州鄉", "恆春鎮", "東港鎮", "林邊鄉", "竹田鄉",
                "車城鄉", "長治鄉"
            ),
            "臺東縣" to listOf(
                "卑南鄉", "大武鄉", "太麻里鄉", "成功鎮", "臺東市",
                "蘭嶼鄉", "鹿野鄉"
            ),
            "花蓮縣" to listOf("吉安鄉", "秀林鄉", "花蓮市"),
            "宜蘭縣" to listOf("三星鄉", "五結鄉", "壯圍鄉", "宜蘭市", "礁溪鄉", "羅東鎮"),
            "澎湖縣" to listOf("望安鄉", "白沙鄉", "西嶼鄉", "馬公市"),
            "金門縣" to listOf("烈嶼鄉", "金城鎮", "金湖鎮"),
            "連江縣" to listOf("南竿鄉", "東引鄉")
        )

        // 創建適配器
        val adapter = ArrayAdapter(this, R.layout.spinner_item, countries)
        // 設置適配器
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinnerCountry.adapter = adapter

        // 設置選項改變的監聽器
        spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCountry = parent?.getItemAtPosition(position).toString()
                val selectedCities = citiesByCountry[selectedCountry] ?: emptyList()
                updateCitySpinner(selectedCities)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        val buttonnext: Button = findViewById(R.id.nextstep)
        buttonnext.setOnClickListener{
            GlobalScope.launch(Dispatchers.Main) { // 使用 lifecycleScope 代替 GlobalScope
                saveRegion() // 保存地區
                getWeather() // 獲取天氣
                jumptoActivity(GameActivity::class.java) // 跳轉
            }
        }
    }

    private suspend fun getWeather() {
        val client = OkHttpClient()
        val username = GlobalVariable.getName()  // 這裡可以根據需要修改為 GlobalVariable.getName()
        val country = spinnerCountry.selectedItem.toString()
        val region = spinnerRegion.selectedItem.toString()
        val request = Request.Builder()
            .url("http://140.136.151.129/weather?country=$country&region=$region")
            .get()
            .build()

        return withContext(Dispatchers.IO) {
            try {
                // 使用 execute() 同步請求
                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val weather = responseBody.toString()
                    GlobalVariable.setweather(weather)
                    Log.d("weather", "Weather: $weather")
                } else {
                    // 若請求不成功，返回預設值 0
                    0
                }
            } catch (e: IOException) {
                e.printStackTrace()
                0 // 若網絡請求出錯，返回 0
            }
        }
    }

    private fun updateCitySpinner(cities: List<String>) {
        // Create a new adapter for the city spinner
        val cityAdapter = ArrayAdapter(this, R.layout.spinner_item, cities)
        cityAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinnerRegion.adapter = cityAdapter
        spinnerRegion.performClick()
    }

    private suspend fun jumptoActivity(targetActivity: Class<*>) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
    }
    private fun saveRegion() {
        val client = OkHttpClient()
        val username = GlobalVariable.getName();
        val country = spinnerCountry.selectedItem.toString()
        val region = spinnerRegion.selectedItem.toString()
        GlobalVariable.setRegion(country, region)
        val json = """
        {
          "username": "$username",
          "country": "$country",
          "region": "$region"
        }
        """
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("http://140.136.151.129:3000/region") // 如果使用模擬器，請使用這個地址
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@InitialActivity, "失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@InitialActivity, "成功: $responseBody", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@InitialActivity, "失敗: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}