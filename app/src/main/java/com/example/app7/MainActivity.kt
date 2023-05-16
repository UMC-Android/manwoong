package com.example.app7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.app7.databinding.ActivityMainBinding
import kotlin.concurrent.thread
import android.media.SoundPool
import android.os.Looper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val soundPool = SoundPool.Builder().build()
    private var soundId: Int = 0

    private var total = 300
    private var started = false

    private val handler = Handler(Looper.getMainLooper()) {
        val second = String.format("%03.1f 초", (total % 600).toFloat() / 10)
        binding.secondview.text = "$second"
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        soundId = soundPool.load(applicationContext, R.raw.sound1, 1)

        binding.timerstart.setOnClickListener {
            if (!started) {
                started = true
                total = 300
                binding.secondview.text = "30.0 초"
                startTimerThread()
            }
        }
    }

    private fun startTimerThread() {
        thread {
            while (started) {
                Thread.sleep(100)
                if (started) {
                    total -= 1
                    handler.sendEmptyMessage(0)
                    if (total == 0) {
                        soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
                        started = false

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}
