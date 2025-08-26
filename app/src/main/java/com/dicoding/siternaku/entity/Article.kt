package com.dicoding.siternaku.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val thumbnailUri: String?,
    val title: String,

    val heading: String?,
    val subHeading: String?,
    val h3: String?,
    val h4: String?,
    val h5: String?,
    val h6: String?,
    val content: String?,

    val description: String,
    val link: String?
)

