package com.example.kakaologin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kakaologin.databinding.ActivityMainBinding
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(Constants.TAG, "사용자 정보 요청 실패 $error")
            } else if (user != null) {
                Log.d(Constants.TAG, "사용자 정보 요청 성공 : $user")
                binding.txtNickName.text = user.kakaoAccount?.profile?.nickname
                binding.txtEmail.text = user.kakaoAccount?.email
            }


        }
    }
}