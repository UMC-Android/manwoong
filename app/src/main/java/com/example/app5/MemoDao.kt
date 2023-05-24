package com.example.app5

import androidx.room.*

@Dao
interface MemoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)//id가 같을시 대체된다고 함.
    fun insert(memo: Memo)

    @Query("SELECT * FROM memo")
    fun getAll(): List<Memo>

    @Query("SELECT * FROM memo WHERE _id IN (:memoIds)")
    fun loadAllByIds(memoIds: IntArray): List<Memo>

    @Query("SELECT * FROM memo WHERE title LIKE :title AND " +
            "content LIKE :content LIMIT 1")
    fun findByTitle_content(title: String, content: String): Memo

    @Insert
    fun insertAll(vararg memos: Memo)

    @Delete
    fun delete(memo: com.example.app5.Memo)
}