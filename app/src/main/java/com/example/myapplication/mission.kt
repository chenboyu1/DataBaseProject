package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.widget.ImageButton
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.GlobalVariable.Companion.missionbutton
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import android.util.Log
import android.widget.TextView

class mission : AppCompatActivity() {

    var money = Manager.money

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_mission)

        // 呼叫方法來顯示彈跳視窗
        showAlertDialog()
        Log.d("mmm", "$money")
    }

    private fun showAlertDialog() {
        val inflater: LayoutInflater = layoutInflater
        val dialogView: View = inflater.inflate(R.layout.daily_mission, null)

        // 建立 AlertDialog 並套用透明樣式
        val builder = AlertDialog.Builder(this, R.style.TransparentDialog)
            .setView(dialogView)
        val dialog: AlertDialog = builder.create()
        dialog.show()

        // 調整對話框大小
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val moneyAmount = findViewById<TextView>(R.id.money_amount)

        fun updateUI(money: Int) {
            moneyAmount.text = money.toString()
        }

        Manager.onMoneyChanged = { updatedMoney ->
            SendMoneyToServer(money)
        }

        // 初始化按鈕
        val btnMission1: Button = dialogView.findViewById(R.id.btn_mission_1)
        val btnMission2: Button = dialogView.findViewById(R.id.btn_mission_2)
        val btnMission3: Button = dialogView.findViewById(R.id.btn_mission_3)
        val btnMission4: Button = dialogView.findViewById(R.id.btn_mission_4)
        val btnCancel: ImageButton = dialogView.findViewById(R.id.btn_exit)

        // 設置按鈕點擊事件
        setupMissionButton(btnMission1, 0)
        setupMissionButton(btnMission2, 1)
        setupMissionButton(btnMission3, 2)
        setupMissionButton(btnMission4, 3)

        btnCancel.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupMissionButton(button: Button, missionIndex: Int) {
        if(missionIndex == 0){
            // 初始化按鈕外觀
            if (missionbutton[missionIndex] == 1) {
                disableButton(button)
            }

            // 設置按鈕的點擊行為
            button.setOnClickListener {
                if (missionbutton[missionIndex] == 0) {
                    money += 25
                    missionbutton[missionIndex] = 1
                    sendSelectedButtonToServer(missionbutton)
                    SendMoneyToServer(money)
                    disableButton(button)
                }
            }
        }else{
            // 初始化按鈕外觀
            if (missionbutton[missionIndex] == 1) {
                disableButton(button)
            }

            // 設置按鈕的點擊行為
            button.setOnClickListener {
                if (missionbutton[missionIndex] == 0) {
                    showError(this)
                    disableButton(button)
                }else if(missionbutton[missionIndex] == 2){
                    money += 25
                    missionbutton[missionIndex] = 1
                    sendSelectedButtonToServer(missionbutton)
                    SendMoneyToServer(money)
                    disableButton(button)
                }
            }
        }
    }

    private fun disableButton(button: Button) {
        button.isEnabled = false
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
        button.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
    }

    private fun showError(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.daily_mission_error)

        val btnOk = dialog.findViewById<Button>(R.id.btn1k)
        btnOk.setOnClickListener {
            dialog.dismiss() // 關閉視窗
        }

        dialog.show()
    }//任務未完成的訊息

    private fun sendSelectedButtonToServer(missionbutton: IntArray) {
        val client = OkHttpClient()
        val username = GlobalVariable.getName()
        val json = """
        {
          "username": "$username",
          "timer": "${missionbutton[0]}",
          "timer2": "${missionbutton[1]}",
          "timer3": "${missionbutton[2]}",
          "timer4": "${missionbutton[3]}"
        }
        """
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("http://140.136.151.129:3000/dailymission")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@mission, "失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@mission, "成功: $responseBody", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@mission, "失敗: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun SendMoneyToServer(id: Int) {
        val client = OkHttpClient()
        val username = GlobalVariable.getName()

        val json = """
        {
          "username": "$username",
          "money": $id
        }
        """

        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
        Log.d("shop", "shop")
        val request = Request.Builder()
            .url("http://140.136.151.129:3000/money")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@mission, "失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@mission, "成功: $responseBody", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@mission, "失敗: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
