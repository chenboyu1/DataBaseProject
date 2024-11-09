package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity



    class mission : AppCompatActivity() {

        var money = 0

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // 呼叫方法來顯示彈跳視窗
            showAlertDialog()
        }

        private fun showAlertDialog() {
            // 使用 LayoutInflater 來載入自訂佈局
            val inflater: LayoutInflater = layoutInflater
            val dialogView: View = inflater.inflate(R.layout.daily_mission, null)

            // 建立 AlertDialog
            val builder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setTitle("每日任務")

            val dialog: AlertDialog = builder.create()
            dialog.show()

            // 調整對話框大小
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            // 設定按鈕的動作
            val btnConfirm: Button = dialogView.findViewById(R.id.mission_item_1)
            val btnConfirm2: Button = dialogView.findViewById(R.id.mission_item_2)
            val btnConfirm3: Button = dialogView.findViewById(R.id.mission_item_3)
            val btnConfirm4: Button = dialogView.findViewById(R.id.mission_item_4)
            val btnCancel: Button = dialogView.findViewById(R.id.btn_exit)

            btnConfirm.setOnClickListener {
                // 按下確認按鈕後的操作
                money += 25 // 增加25點
            }
            btnConfirm2.setOnClickListener {
                // 按下確認按鈕後的操作
                money += 25 // 增加25點

            }
            btnConfirm3.setOnClickListener {
                // 按下確認按鈕後的操作
                money += 25 // 增加25點

            }
            btnConfirm4.setOnClickListener {
                // 按下確認按鈕後的操作
                money += 25 // 增加25點
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss() // 關閉彈跳視窗
            }
        }
    }

