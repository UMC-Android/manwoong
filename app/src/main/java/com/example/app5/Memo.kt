package com.example.app5

import androidx.room.*

@Entity(tableName = "memo")
data class Memo (
    @PrimaryKey(autoGenerate = true) var _id: Long? = null,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "content") val content: String?
)
