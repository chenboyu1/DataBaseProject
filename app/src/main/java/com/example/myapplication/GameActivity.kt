
package com.example.myapplication

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game) // 設定佈局


        // 取得進度條並設定進度
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.progress = 50 // 設定進度條為 50%
    }
}