package com.dicoding.siternaku.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val thumbnailUri: String?,
    val title: String,
    val heading: String?,
    val h3: String?,
    val link: String?
)

