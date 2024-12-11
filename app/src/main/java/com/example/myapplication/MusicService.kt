package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        // 初始化背景音樂資源
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music) // 將背景音樂檔案放入 res/raw 文件夾
        mediaPlayer!!.isLooping = true // 設置音樂循環播放
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.start() // 開始播放背景音樂
        }
        return START_STICKY // 確保 Service 被意外關閉後可以重新啟動
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            mediaPlayer!!.stop() // 停止播放
            mediaPlayer!!.release() // 釋放資源
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null // 此處返回 null，因為不需要綁定 Service
    }
}