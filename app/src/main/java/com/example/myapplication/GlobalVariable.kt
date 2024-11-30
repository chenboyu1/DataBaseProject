package com.example.myapplication

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class GlobalVariable : ComponentActivity() {
    companion object {
        private var username: String = ""
        private var charac: Int = 0
        private var affection: Int = 0
        public var decorate: IntArray = IntArray(10)
        public var food: IntArray = IntArray(10)
        private var currentdecorate: Int = 0
        public var missionbutton: IntArray = IntArray(4)
        private var money: Int = 0
        private var country: String = ""
        private var region: String = ""
        // 修改 變數值
        fun setName(name: String) {
            this.username = name //name
            // username = name // 直接設置username
        }
        // 取得 變數值
        fun getName(): String {
            return username
        }

        fun setCharac(charac : Int){
            this.charac = charac
        }

        // 取得 charac 值
        fun getCharac(): Int {
            Log.d("getcharac", "$charac")
            return charac
        }
        fun getAffection(): Int {
            Log.d("getaffection", "$affection")
            return affection
        }
        //底下函式透過呼叫setDecorate()來存進decorate陣列
        suspend fun setDecorate(decorateArray : IntArray) {
            this.decorate = decorateArray
        }
        //底下函式透過呼叫setDecorate()來存進decorate陣列
        suspend fun setFood(foodArray : IntArray) {
            this.food = foodArray
        }

        fun setcurrentdecorate(decorate : Int) {
            this.currentdecorate = decorate
        }
        fun getcurrentdecorate(): Int {
            return currentdecorate
        }

        fun setmoney(money : Int){
            this.money = money
        }

        fun getmoney(): Int{
            return money
        }

        fun getcountry(): String{
            return country
        }

        fun getregion(): String{
            return region
        }

        fun setRegion(country:String, region:String){
            this.country = country
            this.region = region
        }

        // 設定角色、裝飾、金錢
        suspend fun setbasicData(){
            val client = OkHttpClient()
            val username = GlobalVariable.getName()  // 這裡可以根據需要修改為 GlobalVariable.getName()

            val request = Request.Builder()
                .url("http://140.136.151.129:3000/basicData?username=$username")
                .get()
                .build()

            return withContext(Dispatchers.IO) {
                try {
                    // 使用 execute() 同步請求
                    val response: Response = client.newCall(request).execute()

                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        val data = JSONArray(responseBody)

                        // 將 JSON 陣列轉換為 IntArray
                        val dataArray = IntArray(data.length()) { i ->
                            data.getInt(i)
                        }
                        Log.d("data", "${dataArray[0]}${dataArray[1]}${dataArray[2]}")
                        charac = dataArray[0]
                        currentdecorate = dataArray[1]
                        money = dataArray[2]
                        affection = dataArray[3]
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
        suspend fun setregion(){
            val client = OkHttpClient()
            val username = GlobalVariable.getName()  // 這裡可以根據需要修改為 GlobalVariable.getName()

            val request = Request.Builder()
                .url("http://140.136.151.129:3000/region?username=$username")
                .get()
                .build()

            return withContext(Dispatchers.IO) {
                try {
                    // 使用 execute() 同步請求
                    val response: Response = client.newCall(request).execute()

                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        Log.d("region", "$responseBody")
                        val data = JSONArray(responseBody)
                        Log.d("region", "$data")
                        country = data.getString(0)
                        region = data.getString(1)
                        //Log.d("region", "$country, $region")
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

        suspend fun setdecorate(): IntArray { //mainActivity呼叫這函式
            val client = OkHttpClient()
            val username = GlobalVariable.getName()  // 這裡可以根據需要修改為 GlobalVariable.getName()

            val request = Request.Builder()
                .url("http://140.136.151.129:3000/package?username=$username")
                .get()
                .build()

            return withContext(Dispatchers.IO) {
                try {
                    // 發送同步請求
                    val response: Response = client.newCall(request).execute()

                    if (response.isSuccessful) {
                        // 解析返回的 JSON，提取 decorate 數組
                        val responseBody = response.body?.string()
                        val newDecorate = JSONArray(responseBody)

                        // 將 JSON 陣列轉換為 IntArray
                        val decorateArray = IntArray(newDecorate.length()) { i ->
                            newDecorate.getInt(i)
                        }
                        Log.d("test", "${decorateArray[0]}${decorateArray[1]}${decorateArray[2]}${decorateArray[3]}")
                        setDecorate(decorateArray)
                        return@withContext decorateArray
                    } else {
                        return@withContext IntArray(10)  // 若請求失敗，返回默認的 10 個 0
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    return@withContext IntArray(10)  // 若網絡錯誤，返回默認的 10 個 0
                }
            }
        }
        suspend fun setfood(): IntArray { //mainActivity呼叫這函式
            val client = OkHttpClient()
            val username = GlobalVariable.getName()  // 這裡可以根據需要修改為 GlobalVariable.getName()

            val request = Request.Builder()
                .url("http://140.136.151.129:3000/food?username=$username") //要暫時從140.136.151.129改成10.0.2.2
                .get()
                .build()

            return withContext(Dispatchers.IO) {
                try {
                    // 發送同步請求
                    val response: Response = client.newCall(request).execute()
                    if (!response.isSuccessful) {
                        Log.e("API Error", "Response code: ${response.code}, message: ${response.message}, body: ${response.body?.string()}")
                    }

                    if (response.isSuccessful) {
                        // 解析返回的 JSON，提取 decorate 數組
                        val responseBody = response.body?.string()
                        val newFood = JSONArray(responseBody)

                        // 將 JSON 陣列轉換為 IntArray
                        val foodArray = IntArray(newFood.length()) { i ->
                            newFood.getInt(i)
                        }
                        Log.d("test1", "${foodArray[0]}${foodArray[1]}${foodArray[2]}${foodArray[3]}${foodArray[4]}${foodArray[5]}${foodArray[6]}${foodArray[7]}${foodArray[8]}${foodArray[9]}")
                        setFood(foodArray)
                        return@withContext foodArray
                    } else {
                        return@withContext IntArray(10)  // 若請求失敗，返回默認的 10 個 0
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    return@withContext IntArray(10)  // 若網絡錯誤，返回默認的 10 個 0
                }
            }
        }

        suspend fun setmission() {
            val client = OkHttpClient()
            val username = GlobalVariable.getName()  // 這裡可以根據需要修改為 GlobalVariable.getName()

            val request = Request.Builder()
                .url("http://140.136.151.129:3000/dailymission?username=$username")
                .get()
                .build()

            return withContext(Dispatchers.IO) {
                try {
                    // 使用 execute() 同步請求
                    val response: Response = client.newCall(request).execute()

                    if (response.isSuccessful) {
                        val Body = response.body?.string()
                        val timer = JSONArray(Body)

                        // 將 JSON 陣列轉換為 IntArray
                        missionbutton = IntArray(timer.length()) { i ->
                            timer.getInt(i)
                        }
                        Log.d("missionbutton", "${missionbutton[0]}${missionbutton[1]}${missionbutton[2]}${missionbutton[3]}")
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
    }
}
