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

        val id = intent.getStringExtra("email") // Intent에서 Key를 email로 가지고 있는 값 가져오기
        val pw= intent.getIntExtra("password", 0) // Intent에서 Key를 password로 가지고 있는 값 가져오기
        // 두번째 인자 값의 뜻은.. 값을 찾지 못했을땐 0을 대신 넣겠다는 뜻

        binding.email.text = id             // 텍스트뷰에 아이디 출력
        binding.passwd.text = pw.toString()  // 텍스트뷰에 비밀번호 출력
    }
}