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
        val countries = listOf("請選擇","臺北市", "新北市", "基隆市", "桃園市", "新竹市", "新竹縣", "苗栗縣", "台中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "台南市", "高雄市", "屏東縣", "台東縣", "花蓮縣", "宜蘭縣", "澎湖縣", "金門縣", "連江縣")
        val citiesByCountry = mapOf(
            "臺北市" to listOf("中正區", "大同區", "中山區", "松山區", "大安區", "萬華區", "信義區", "壽山區", "文山區", "北投區", "內湖區", "南港區", "觀光區"),
            "新北市" to listOf("板橋區", "三重區", "中和區", "永和區", "土城區", "樹林區", "鶯歌區", "三峽區", "新莊區", "泰山區", "林口區", "蘆洲區", "五股區", "八里區", "逢甲區", "金山區", "深坑區", "石碇區", "雙和區"),
            "桃園市" to listOf("桃園區", "中壢區", "平鎮區", "龍潭區", "內壢區", "外埔區", "橋頭區", "興化區"),
            "台中市" to listOf("中區", "東區", "南區", "西區", "北區", "西屯區", "南屯區", "北屯區", "大里區", "太平區", "大雅區", "東勢區", "石岡區", "新社區", "南區"),
            "台南市" to listOf("中西區", "東區", "南區", "北區", "安平區", "安南區", "永康區", "歸仁區", "新化區", "新營區", "鹽水區", "白河區", "柳營區", "後壁區", "東山區", "麻豆區", "官田區", "大內區", "山上區"),
            "高雄市" to listOf("新興區", "前金區", "苓雅區", "鹽埕區", "鼓山區", "旗津區", "前鎮區", "三民區", "楠梓區", "小港區", "左營區", "大寮區", "鳥松區", "林園區", "大樹區", "旗山區", "美濃區", "六龜區"),
            "基隆市" to listOf("仁愛區", "信義區", "中正區", "中山區", "安樂區", "萬里區", "金山區", "汐止區", "樹林區"),
            "新竹市" to listOf("東區", "西區", "北區", "香山區"),
            "新竹縣" to listOf("竹北市", "湖口鄉", "新豐鄉", "關西鎮", "芎林鄉", "寶山鄉", "竹東鎮", "五峰鄉", "橫山鄉", "尖石鄉", "北埔鄉", "峨眉鄉"),
            "苗栗縣" to listOf("苗栗市", "苑裡鎮", "通霄鎮", "竹南鎮", "頭份市", "三灣鄉", "南庄鄉", "獅潭鄉", "後龍鎮", "三義鄉", "西湖鄉", "卓蘭鎮"),
            "彰化縣" to listOf("彰化市", "芬園鄉", "花壇鄉", "秀水鄉", "鹿港鎮", "福興鄉", "線西鄉", "和美鎮", "伸港鄉", "員林市", "社頭鄉", "永靖鄉", "埔心鄉", "大村鄉", "埔鹽鄉", "田中鎮", "北斗鎮", "二林鎮"),
            "南投縣" to listOf("南投市", "草屯鎮", "竹山鎮", "集集鎮", "名間鄉", "鹿谷鄉", "中寮鄉", "魚池鄉", "水里鄉", "信義鄉", "仁愛鄉"),
            "雲林縣" to listOf("斗六市", "斗南鎮", "虎尾鎮", "西螺鎮", "土庫鎮", "北港鎮", "臺西鄉", "東勢鄉", "崙背鄉", "麥寮鄉", "林內鄉", "莿桐鄉"),
            "嘉義市" to listOf("東區", "西區"),
            "嘉義縣" to listOf("太保市", "朴子市", "布袋鎮", "大林鎮", "民雄鄉", "溪口鄉", "新港鄉", "六腳鄉", "東石鄉", "阿里山鄉", "水上鄉", "中埔鄉"),
            "屏東縣" to listOf("屏東市", "三地門", "霧台鄉", "瑪家鄉", "九如鄉", "里港鄉", "高樹鄉", "鹽埔鄉", "長治鄉", "麟洛鄉", "竹田鄉", "內埔鄉"),
            "台東縣" to listOf("台東市", "成功鎮", "關山鎮", "卑南鄉", "鹿野鄉", "池上鄉", "東河鄉", "長濱鄉", "太麻里鄉", "大武鄉", "綠島鄉", "蘭嶼鄉"),
            "澎湖縣" to listOf("馬公市", "湖西鄉", "白沙鄉", "西嶼鄉", "望安鄉", "七美鄉"),
            "金門縣" to listOf("金城鎮", "金湖鎮", "金寧鄉", "烈嶼鄉", "烏坵鄉"),
            "連江縣" to listOf("南竿鄉", "北竿鄉", "莒光鄉", "東引鄉")
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
            lifecycleScope.launch(Dispatchers.Main) { // 使用 lifecycleScope 代替 GlobalScope
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