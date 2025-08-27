package com.dicoding.siternaku.ui.home

import android.graphics.pdf.LoadParams
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.siternaku.dao.ArticleDao
import com.dicoding.siternaku.entity.Article

class ArticlePagingSource(private val dao: ArticleDao) : PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val articles = dao.getArticlesPaged(page * pageSize, pageSize)
            LoadResult.Page(
                data = articles,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { pos ->
            val page = pos / state.config.pageSize
            page
        }
    }
}
