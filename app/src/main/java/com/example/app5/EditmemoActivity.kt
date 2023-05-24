package com.example.app5

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app5.databinding.ActivityEditmemoBinding

//activity_editmemo
class EditmemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditmemoBinding
    private var memoDb : AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditmemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        memoDb = AppDatabase.getInstance(this)

        val addRunnable = Runnable {
            val title = binding.editTextTitle.text.toString()
            val content = binding.editTextContent.text.toString()
            val newmemo = Memo(title = title, content = content)
            memoDb?.memoDao()?.insert(newmemo)
        }


        binding.btnsave.setOnClickListener {
            val addThread = Thread(addRunnable)
            addThread.start()

            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}
