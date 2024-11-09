package com.example.myapplication
import android.widget.ImageButton
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
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
        val btnMission1: Button = dialogView.findViewById(R.id.btn_mission_1)
        val btnMission2: Button = dialogView.findViewById(R.id.btn_mission_2)
        val btnMission3: Button = dialogView.findViewById(R.id.btn_mission_3)
        val btnMission4: Button = dialogView.findViewById(R.id.btn_mission_4)
        val btnCancel: ImageButton = dialogView.findViewById(R.id.btn_exit)

        btnMission1.setOnClickListener {
            // 按下確認按鈕後的操作
            money += 25 // 增加25點

        }
        btnMission2.setOnClickListener {
            // 按下確認按鈕後的操作
            money += 25 // 增加25點

        }
        btnMission3.setOnClickListener {
            // 按下確認按鈕後的操作
            money += 25 // 增加25點

        }
        btnMission4.setOnClickListener {
            // 按下確認按鈕後的操作
            money += 25 // 增加25點

        }
        btnCancel.setOnClickListener {
            dialog.dismiss() // 關閉彈跳視窗
        }
    }
}
