package com.dicoding.siternaku.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val thumbnailUri: String?,
    val title: String,
    val heading: String?,
    val h3: String?,
    val link: String?,
    val date: String = SimpleDateFormat("dd MMMM yyyy", Locale("id")).format(Date())
)

