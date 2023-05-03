package com.example.app5

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Memo(val id: Long, val text: String)

class MemoDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "memo.db"
        const val DATABASE_VERSION = 1
        const val TABLE_MEMO = "memo"
        const val KEY_ID = "_id"
        const val KEY_MEMO = "memo_text"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 테이블 생성 SQL
        val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_MEMO ($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_MEMO TEXT)"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 데이터베이스 업그레이드시 호출되는 메서드
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MEMO")
        onCreate(db)
    }

    fun insertMemo(memo: String) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(KEY_MEMO, memo)
        }

        db.insert(TABLE_MEMO, null, values)

        db.close()
    }

    fun getMemo(id: Long): Memo? {
        val db = this.readableDatabase

        val selectQuery = "SELECT * FROM $TABLE_MEMO WHERE $KEY_ID = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))

        var memo: Memo? = null
        if (cursor.moveToFirst()) {
            val memoColumnIndex = cursor.getColumnIndex(KEY_MEMO)
            if (memoColumnIndex != -1) {
                val memoText = cursor.getString(memoColumnIndex)
                memo = Memo(id, memoText)
            }
        }

        cursor.close()
        db.close()
        return memo
    }
}

