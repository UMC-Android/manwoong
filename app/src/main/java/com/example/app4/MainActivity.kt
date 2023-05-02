
package com.example.app4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.app4.databinding.ActivityMainBinding
import android.app.AlertDialog




class MainActivity : AppCompatActivity() {
    private lateinit var nextIntent: Intent
    private lateinit var binding: ActivityMainBinding
    private var editTextContent: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // setContentView 대신에 뷰 바인딩 루트 뷰를 사용하여 액티비티 레이아웃을 설정합니다.
        setContentView(binding.root)
        Log.d("lifecycle","onCreate")

        nextIntent = Intent(this,SecondActivity::class.java)

        binding.btnnext.setOnClickListener { // 버튼 클릭시 할 행동
            nextIntent.putExtra("editTextContent", binding.viewContent.text.toString())  // EditText의 내용을 Intent에 추가합니다.
            startActivity(nextIntent)  // 화면 전환하기
        }

    }
    override fun onResume() {
        super.onResume()

        if (editTextContent.isNotEmpty()) {
            binding.viewContent.setText(editTextContent)
        }
        // onPause에서 저장한 전역변수 내용으로 EditText 내용 설정
    }

    override fun onPause() {
        super.onPause()

        editTextContent = binding.viewContent.text.toString()
        // 현재까지 작성한 내용 전역변수에 저장
    }

    override fun onRestart() {
        super.onRestart()

        val builder = AlertDialog.Builder(this)
        builder.setMessage("계속 작성하시겠습니까?")
        builder.setPositiveButton("네") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setNegativeButton("아니오") { dialog, _ ->
            editTextContent = ""
            binding.viewContent.text.clear()
            dialog.dismiss()
        }
        builder.create().show()
        // Dialog를 활용하여 다시 작성할지 묻는 창 띄우기
    }





}