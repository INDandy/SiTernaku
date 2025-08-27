package com.dicoding.siternaku.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicoding.siternaku.entity.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Update
    suspend fun update(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM articles ORDER BY id DESC")
    fun getAllArticles(): LiveData<List<Article>>

    @Query("SELECT * FROM articles ORDER BY id DESC LIMIT :limit OFFSET :offset")
    suspend fun getArticlesPaged(offset: Int, limit: Int): List<Article>

    @Query("SELECT * FROM articles ORDER BY id DESC")
    fun getAllArticlesPaging(): PagingSource<Int, Article>

    @Query("SELECT * FROM articles WHERE id = :id LIMIT 1")
    suspend fun getArticleById(id: Int): Article?
}



