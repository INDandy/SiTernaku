package com.dicoding.siternaku.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.siternaku.database.AppDatabase
import com.dicoding.siternaku.entity.Article
import com.dicoding.siternaku.repository.ArticleRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ArticleRepository
    val allArticles: LiveData<List<Article>>
    val isLoading = MutableLiveData<Boolean>()

    init {
        val dao = AppDatabase.getDatabase(application).articleDao()
        repository = ArticleRepository(dao)
        allArticles = repository.allArticles
        isLoading.value = true
    }

    fun insert(article: Article) = viewModelScope.launch {
        repository.insert(article)
    }

    fun update(article: Article) = viewModelScope.launch {
        repository.update(article)
    }

    fun delete(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }

    fun getArticleById(id: Int): LiveData<Article?> {
        val result = MutableLiveData<Article?>()
        viewModelScope.launch {
            result.postValue(repository.getArticleById(id))
        }
        return result
    }

    fun setLoading(loading: Boolean) {
        isLoading.value = loading
    }
}