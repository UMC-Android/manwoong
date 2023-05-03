package com.example.app5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.app5.databinding.ActivityEditmemoBinding

//activity_editmemo
class EditmemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditmemoBinding
    private lateinit var dbHelper: MemoDbHelper // DBHelper 변수 추가
    private var memoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditmemoBinding.inflate(layoutInflater) // setContentView 대신에 뷰 바인딩 루트 뷰를 사용하여 액티비티 레이아웃을 설정합니다.
        setContentView(binding.root)
        Log.d("lifecycle","onCreate")

        dbHelper = MemoDbHelper(this)

        memoId = intent.getLongExtra("memo_id", -1)
        if (memoId != -1L) {
            val memo = dbHelper.getMemo(memoId)

            binding.editMemo.setText(memo?.text)
        }


        binding.btnsave.setOnClickListener { // 버튼 클릭시 할 행동
            val memoText = binding.editMemo.text.toString()
            dbHelper.insertMemo(memoText) // DBHelper의 insertMemo 메서드 호출
            setResult(RESULT_OK, Intent().apply { putExtra("memo_text", memoText) })
            if (!isFinishing) finish()//  화면 종료
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close() // DBHelper의 close 메서드 호출
    }

}