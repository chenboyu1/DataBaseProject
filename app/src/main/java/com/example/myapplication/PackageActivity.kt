package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.ComponentActivity


class PackageActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package)


        // 获取 ScrollView 和按钮容器
        val sideboxScroll = findViewById<ScrollView>(R.id.sideboxScroll)
        val sidebox = findViewById<LinearLayout>(R.id.sidebox)


// 动态生成按钮
        val buttonCount = 20 // 你可以通过变量动态调整按钮数量
        for (i in 0 until buttonCount) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                225.dpToPx(), // 按鈕寬度
                223.dpToPx() // 按鈕高度
            )
            sidebox.addView(button)
        }

    }
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
