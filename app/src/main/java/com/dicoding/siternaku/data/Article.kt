package com.dicoding.siternaku.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "thumbnailUri")
    val thumbnailUri: String? = null,

    @ColumnInfo(name = "heading")
    val heading: String? = null,

    @ColumnInfo(name = "subHeading")
    val subHeading: String? = null,

    @ColumnInfo(name = "h3")
    val h3: String? = null,

    @ColumnInfo(name = "h4")
    val h4: String? = null,

    @ColumnInfo(name = "h5")
    val h5: String? = null,

    @ColumnInfo(name = "h6")
    val h6: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "link")
    val link: String? = null,

    @ColumnInfo(name = "date")
    val date: String? = null
)

