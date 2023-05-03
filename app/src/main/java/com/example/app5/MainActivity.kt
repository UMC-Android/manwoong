package com.example.app5

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app5.databinding.ActivityMainBinding
import com.example.app5.databinding.ActivityMemoItemBinding


data class text_content(val id: Int, val text_memo: String)


class MainActivity : AppCompatActivity(), MemoItemClickListener{
    private lateinit var nextIntent: Intent
    private lateinit var binding: ActivityMainBinding
    private lateinit var getResultText: ActivityResultLauncher<Intent>

    private lateinit var dbHelper: MemoDbHelper
    private lateinit var adapter: MemoAdapter
    private lateinit var memoList: MutableList<text_content>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // setContentView 대신에 뷰 바인딩 루트 뷰를 사용하여 액티비티 레이아웃을 설정합니다.
        setContentView(binding.root)

        dbHelper = MemoDbHelper(this)
        adapter = MemoAdapter(this, this)
        memoList = mutableListOf()

        // RecyclerView 초기화
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)

        val db = dbHelper.readableDatabase
        val cursor = db.query(MemoDbHelper.TABLE_MEMO, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(MemoDbHelper.KEY_ID))
            val memo = cursor.getString(cursor.getColumnIndexOrThrow(MemoDbHelper.KEY_MEMO))
            memoList.add(text_content(id, memo))
        }
        adapter.submitList(memoList)
        cursor.close()
        db.close()



        getResultText = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val mString = result.data?.getStringExtra("memo_text")
                Log.d("mainActivity","onCreate: good To go:$mString")

                val db = dbHelper.writableDatabase
                val values = ContentValues().apply {
                    put(MemoDbHelper.KEY_MEMO, mString)
                }
                val id = db.insert(MemoDbHelper.TABLE_MEMO, null, values).toInt()
                memoList.add(text_content(id, mString!!))
                adapter.submitList(memoList)
                db.close()
            }
        }





        nextIntent = Intent(this, EditmemoActivity::class.java)

        binding.btnadd.setOnClickListener { // 버튼 클릭시 할 행동
            getResultText.launch(nextIntent)  // 화면 전환하기

        }


    }


    override fun onMemoItemClick(memo: text_content) {
        // 클릭된 메모의 정보(memo)를 이용해 필요한 작업을 수행합니다.
        // 예를 들어, 메모의 id를 이용해 해당 메모를 수정하는 화면으로 이동할 수 있습니다.
        val intent = Intent(this, EditmemoActivity::class.java)
        intent.putExtra("memo_id", memo.id)
        startActivityForResult(intent, EDIT_MEMO_REQUEST_CODE)
    }

    override fun onMemoItemLongClick(memo: text_content) {
        val db = dbHelper.writableDatabase
        db.delete(MemoDbHelper.TABLE_MEMO, "${MemoDbHelper.KEY_ID}=?", arrayOf(memo.id.toString()))
        memoList.remove(memo)
        adapter.submitList(memoList)
        db.close()
    }
    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }

    companion object {
        const val EDIT_MEMO_REQUEST_CODE = 2
    }
}




interface MemoItemClickListener {
    fun onMemoItemClick(memo: text_content)
    fun onMemoItemLongClick(memo: text_content)
}

class MemoAdapter(private val context: Context, private val listener: MemoItemClickListener) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    private var memoList = emptyList<text_content>()

    inner class MemoViewHolder(private val binding: ActivityMemoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onMemoItemClick(memoList[position])
                }
            }
            binding.root.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onMemoItemLongClick(memoList[position])
                }
                true
            }
        }

        fun bind(memo: text_content) {
            binding.rvName.text = memo.text_memo
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val binding = ActivityMemoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val memo = memoList[position]
        holder.bind(memo)
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    fun submitList(list: List<text_content>) {
        memoList = list
        notifyDataSetChanged()
    }


}


