package com.dicoding.siternaku.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.siternaku.dao.ArticleDao
import com.dicoding.siternaku.entity.Article
import kotlinx.coroutines.flow.Flow

class ArticleRepository(private val dao: ArticleDao) {

    fun getAllArticlesPaging(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { dao.getAllArticlesPaging() }
        ).flow
    }

    suspend fun insert(article: Article) = dao.insert(article)
    suspend fun update(article: Article) = dao.update(article)
    suspend fun delete(article: Article) = dao.delete(article)
    suspend fun getArticleById(id: Int): Article? = dao.getArticleById(id)
}
