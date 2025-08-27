package com.dicoding.siternaku.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.siternaku.database.AppDatabase
import com.dicoding.siternaku.entity.Article
import com.dicoding.siternaku.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ArticleRepository

    val allArticlesPaging: Flow<PagingData<Article>>

    init {
        val dao = AppDatabase.getDatabase(application).articleDao()
        repository = ArticleRepository(dao)
        allArticlesPaging = repository.getAllArticlesPaging().cachedIn(viewModelScope)
    }

    fun insert(article: Article) = viewModelScope.launch { repository.insert(article) }
    fun update(article: Article) = viewModelScope.launch { repository.update(article) }
    fun delete(article: Article) = viewModelScope.launch { repository.delete(article) }
    fun getArticleById(articleId: Int): LiveData<Article?> {
        val result = MutableLiveData<Article?>()
        viewModelScope.launch {
            result.postValue(repository.getArticleById(articleId))
        }
        return result
    }
}