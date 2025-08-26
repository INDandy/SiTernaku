package com.dicoding.siternaku.repository

import androidx.lifecycle.LiveData
import com.dicoding.siternaku.dao.ArticleDao
import com.dicoding.siternaku.entity.Article

class ArticleRepository(private val dao: ArticleDao) {
    val allArticles: LiveData<List<Article>> = dao.getAllArticles()

    suspend fun insert(article: Article) {
        dao.insert(article)
    }

    suspend fun update(article: Article) {
        dao.update(article)
    }

    suspend fun delete(article: Article) {
        dao.delete(article)
    }
    suspend fun getArticleById(id: Int): Article? {
        return dao.getArticleById(id)
    }
}

