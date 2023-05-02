package com.example.app4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app4.databinding.ActivityMainBinding
import com.example.app4.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater) // setContentView 대신에 뷰 바인딩 루트 뷰를 사용하여 액티비티 레이아웃을 설정합니다.
        setContentView(binding.root)

        val text = intent.getStringExtra("editTextContent") // Intent에서 Key를 email로 가지고 있는 값 가져오기

        binding.cont.text = text          // 텍스트뷰에 아이디 출력

    }
}