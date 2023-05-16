package com.example.app7_2


import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.example.app7_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MediaPlayer 초기화
        mediaPlayer = MediaPlayer.create(this, R.raw.song)

        // Set up the start/stop button click listener
        binding.startstopBtn.setOnClickListener {
            if (isPlaying) {
                pauseAudio()
            } else {
                playAudio()
            }
        }

        // Set up the SeekBar listener
        binding.timebar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                    updateCurrentTime(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No implementation needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // No implementation needed
            }
        })

        // Set initial duration
        val duration = mediaPlayer.duration
        binding.durationTime.text = getTimeString(duration)

        // Set initial current time
        updateCurrentTime(0)
    }

    private fun playAudio() {
        mediaPlayer.start()

        isPlaying = true

        // Update SeekBar and CurrentTime TextView
        updateSeekBar()
    }

    private fun pauseAudio() {
        mediaPlayer.pause()

        isPlaying = false
    }

    private fun updateSeekBar() {
        binding.timebar.max = mediaPlayer.duration

        val handler = android.os.Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (mediaPlayer.isPlaying) {
                    binding.timebar.progress = mediaPlayer.currentPosition
                    updateCurrentTime(mediaPlayer.currentPosition)
                    handler.postDelayed(this, 1000) // Update every second
                }
            }
        }, 0)
    }

    private fun updateCurrentTime(progress: Int) {
        binding.currentTime.text = getTimeString(progress)
    }

    private fun getTimeString(timeMs: Int): String {
        val seconds = (timeMs / 1000) % 60
        val minutes = (timeMs / (1000 * 60)) % 60

        return String.format("%d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}