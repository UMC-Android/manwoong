
package com.example.app4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.app4.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var nextIntent: Intent
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // setContentView 대신에 뷰 바인딩 루트 뷰를 사용하여 액티비티 레이아웃을 설정합니다.
        setContentView(binding.root)
        Log.d("lifecycle","onCreate")

        nextIntent = Intent(this,SecondActivity::class.java)
        nextIntent.putExtra("email", "cheonwoong21@naver.com")   // Intent에 이메일 주소 넣기
        nextIntent.putExtra("password", 1234)

        binding.btnnext.setOnClickListener { // 버튼 클릭시 할 행동
            startActivity(nextIntent)  // 화면 전환하기
        }
    }



    override fun onStart() {
        super.onStart()
        Log.d("lifecycle","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle","onResume")
    }

    override fun onPause(){
        super.onPause()
        Log.d("lifecycle","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("lifecycle","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycle","onDestroy")
    }



}