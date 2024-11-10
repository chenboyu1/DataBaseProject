package com.example.myapplication

import androidx.activity.ComponentActivity

class GlobalVariable : ComponentActivity() {
    companion object {
        //存放變數
        private var username: String = ""

        //修改 變數値
        fun setName(name: String){
            this.username = name
        }

        //取得 變數值
        fun getName(): String{
            return username
        }
    }
}