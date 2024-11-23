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
        public var decorate: IntArray = IntArray(10)
        public var food: IntArray = IntArray(10)
        private var currentdecorate: Int = 0
        // 修改 變數值
        fun setName(name: String) {
            this.username = "b" //name
            // username = name // 直接設置username
        }
        // 取得 變數值
        fun getName(): String {
            return username
        }

        // 修改 charac 值，使用協程來調用 suspend 函式
        suspend fun setCharac() {
            this.charac = setcharac()
        }
        // 取得 charac 值
        fun getCharac(): Int {
            Log.d("getcharac", "$charac")
            return charac
        }
        //底下函式透過呼叫setDecorate()來存進decorate陣列
        suspend fun setDecorate(decorateArray : IntArray) {
            this.decorate = decorateArray
        }
        //底下函式透過呼叫setDecorate()來存進decorate陣列
        suspend fun setFood(foodArray : IntArray) {
            this.food = foodArray
        }
        //回傳decorate陣列用global variable
        suspend fun setcurrentdecorate() {
            this.currentdecorate = setcurrentDecorate()
        }
        fun getcurrentdecorate(): Int {
            return currentdecorate
        }

        // 改為 suspend 函式，並在協程中調用
        suspend fun setcharac(): Int {
            val client = OkHttpClient()
            val username = GlobalVariable.getName()  // 這裡可以根據需要修改為 GlobalVariable.getName()

            val request = Request.Builder()
                .url("http://140.136.151.129:3000/charac?username=$username")
                .get()
                .build()

            return withContext(Dispatchers.IO) {
                try {
                    // 使用 execute() 同步請求
                    val response: Response = client.newCall(request).execute()

                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        responseBody?.toInt() ?: 0 // 將回應的字串轉為整數，若無返回則為 0
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
        suspend fun setcurrentDecorate(): Int {
            val client = OkHttpClient()
            val username = GlobalVariable.getName()  // 這裡可以根據需要修改為 GlobalVariable.getName()

            val request = Request.Builder()
                .url("http://140.136.151.129:3000/charac?username=$username")
                .get()
                .build()

            return withContext(Dispatchers.IO) {
                try {
                    // 使用 execute() 同步請求
                    val response: Response = client.newCall(request).execute()

                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        responseBody?.toInt() ?: 0 // 將回應的字串轉為整數，若無返回則為 0
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
