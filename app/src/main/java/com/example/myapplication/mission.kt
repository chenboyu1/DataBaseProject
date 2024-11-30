package com.example.myapplication
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
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import com.example.myapplication.GlobalVariable.Companion.missionbutton
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import Manager
import android.util.Log
import android.widget.TextView
import okhttp3.Call

class mission : AppCompatActivity() {

    var money = Manager.money

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.)

        // 呼叫方法來顯示彈跳視窗
        showAlertDialog()
    }

    private fun showAlertDialog() {
        // 使用 LayoutInflater 載入自訂佈局
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
            //金錢顯示
            moneyAmount.text = money.toString()
        }

        Manager.onMoneyChanged = { updatedMoney ->
            updateUI(updatedMoney)
        }

        // 初始化 UI
        updateUI(Manager.money)

        // 設定按鈕的動作
        val btnMission1: Button = dialogView.findViewById(R.id.btn_mission_1)
        val btnMission2: Button = dialogView.findViewById(R.id.btn_mission_2)
        val btnMission3: Button = dialogView.findViewById(R.id.btn_mission_3)
        val btnMission4: Button = dialogView.findViewById(R.id.btn_mission_4)
        val btnCancel: ImageButton = dialogView.findViewById(R.id.btn_exit)

        btnMission1.setOnClickListener {
            if (missionbutton[0] == 0) {
                money += 25 // 增加25點
                missionbutton[0] = 1
                sendSelectedButtonToServer(missionbutton)
                SendMoneyToServer(money)

                // 禁用按鈕並更改外觀
                btnMission1.isEnabled = false
                btnMission1.setBackgroundColor(ContextCompat.getColor(this, R.color.gray)) // 灰色背景
                btnMission1.setTextColor(ContextCompat.getColor(this, R.color.light_gray)) // 文字變淺
            }
        }

        btnMission2.setOnClickListener {
            if (missionbutton[1] == 0) {
                money += 25
                missionbutton[1] = 1
                sendSelectedButtonToServer(missionbutton)
                SendMoneyToServer(money)

                btnMission2.isEnabled = false
                btnMission2.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                btnMission2.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
            }
        }



        btnMission3.setOnClickListener {
            // 按下確認按鈕後的操作
            if(missionbutton[2] == 0) {
                money += 25 // 增加25點
                missionbutton[2] = 1
                sendSelectedButtonToServer(missionbutton)
                SendMoneyToServer(money)

                btnMission3.isEnabled = false
                btnMission3.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                btnMission3.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
            }

        }
        btnMission4.setOnClickListener {
            // 按下確認按鈕後的操作
            if(missionbutton[3] == 0) {
                money += 25 // 增加25點
                missionbutton[3] = 1
                sendSelectedButtonToServer(missionbutton)
                SendMoneyToServer(money)

                btnMission4.isEnabled = false
                btnMission4.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                btnMission4.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
            }


        }
        btnCancel.setOnClickListener {
            dialog.dismiss() // 關閉彈跳視窗

            // 切換到遊戲畫面
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            finish() // 可選，防止返回到此活動
        }

    }
    private fun sendSelectedButtonToServer(missionbutton: IntArray) {
        val client = OkHttpClient()
        val username = GlobalVariable.getName()
        val json = """
        {
          "username": "$username",
          "timer" : "${GlobalVariable.missionbutton[0]}",
          "timer2" : "${GlobalVariable.missionbutton[1]}",
          "timer3" : "${GlobalVariable.missionbutton[2]}",
          "timer4" : "${GlobalVariable.missionbutton[3]}"
        }
        """
        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)

        val request = Request.Builder()
            .url("http://140.136.151.129:3000/dailymission") // 如果使用模擬器，請使用這個地址
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
    private fun updateButtonState(button: Button, index: Int) {
        if (missionbutton[index] == 0) {
            // 恢復按鈕樣式
            button.isEnabled = true
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            button.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else {
            // 禁用按鈕樣式
            button.isEnabled = false
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
            button.setTextColor(ContextCompat.getColor(this, R.color.light_gray))
        }
    }
    private fun SendMoneyToServer(id: Int) {
        val client = OkHttpClient()
        val username = GlobalVariable.getName()

        // 構建 JSON 請求資料
        val json = """
        {
          "username": "$username",
          "money": $id
        }
        """

        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
        Log.d("shop", "shop")
        val request = Request.Builder()
            .url("http://140.136.151.129:3000/money") // 如果使用模擬器，請使用正確的地址140.136.151.129 or 10.0.2.2
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@mission, "請求失敗: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@mission, "請求成功: $responseBody", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@mission, "伺服器錯誤: $responseBody", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
