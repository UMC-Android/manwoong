package com.example.app5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var memoDB: AppDatabase? = null
    private var memoList = listOf<Memo>()
    private lateinit var mainAdapter: MainAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        memoDB = AppDatabase.getInstance(this)

        val r = Runnable {
            try {
                memoList = memoDB?.memoDao()?.getAll()!!

                runOnUiThread {
                    mainAdapter = MainAdapter(memoList)
                    mainAdapter.notifyDataSetChanged()

                    binding.rv.adapter = mainAdapter
                    binding.rv.layoutManager = LinearLayoutManager(this)
                    binding.rv.setHasFixedSize(true)
                }
            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }

        val thread = Thread(r)
        thread.start()

        binding.btnadd.setOnClickListener {
            val intent = Intent(this, EditmemoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        memoDB = null
        super.onDestroy()
    }
}
