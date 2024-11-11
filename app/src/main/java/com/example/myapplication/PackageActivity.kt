package com.example.myapplication

import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.ComponentActivity


class PackageActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package)
        val imageView: ImageView = findViewById(R.id.myImageView)
        val decorativeIcon: ImageView = findViewById(R.id.decorativeIcon)
        // 获取 ScrollView 和按钮容器
        val sideboxScroll = findViewById<ScrollView>(R.id.sideboxScroll)
        val sidebox = findViewById<LinearLayout>(R.id.sidebox)
        val IntArray = intArrayOf(0,1,1,0,1,1,1,1,1,1)
        if(IntArray[0]==1){
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                225.dpToPx(), // 按鈕寬度
                223.dpToPx() // 按鈕高度
            ).apply {
                gravity = Gravity.CENTER // 將按鈕置中
            }
            button.setBackgroundResource(R.drawable.decoration1_graduation_cap)
            sidebox.addView(button)
        }
        if(IntArray[1]==1){
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                225.dpToPx(), // 按鈕寬度
                223.dpToPx() // 按鈕高度
            ).apply {
                gravity = Gravity.CENTER // 將按鈕置中
            }
            button.setBackgroundResource(R.drawable.decoration2_hbd_hat)
            sidebox.addView(button)
            button.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.alpha = 0.5f // 按下时按钮变得透明
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        v.alpha = 1f // 松开或取消时恢复透明度
                    }
                }
                decorativeIcon.setImageResource(R.drawable.decoration2_hbd_hat)
                false
            }
        }
        if(IntArray[2]==1){
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                225.dpToPx(), // 按鈕寬度
                223.dpToPx() // 按鈕高度
            ).apply {
                gravity = Gravity.CENTER // 將按鈕置中
            }
            button.setBackgroundResource(R.drawable.decoration3)
            sidebox.addView(button)
            button.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.alpha = 0.5f // 按下时按钮变得透明
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        v.alpha = 1f // 松开或取消时恢复透明度
                    }
                }
                decorativeIcon.setImageResource(R.drawable.decoration3)
                false
            }
        }
        if(IntArray[3]==1){
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                225.dpToPx(), // 按鈕寬度
                223.dpToPx() // 按鈕高度
            ).apply {
                gravity = Gravity.CENTER // 將按鈕置中
            }
            button.setBackgroundResource(R.drawable.decoration4_red_fur_hat)
            sidebox.addView(button)
        }

// 动态生成按钮
        /*val buttonCount = 20 // 你可以通过变量动态调整按钮数量
        for (i in 0 until buttonCount) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                225.dpToPx(), // 按鈕寬度
                223.dpToPx() // 按鈕高度
            )

            sidebox.addView(button)
        }*/

    }
    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}
