package com.dicoding.siternaku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.siternaku.databinding.ItemArticleBinding
import com.dicoding.siternaku.entity.Article


class ArticleAdapter(
    private val onItemClick: (Article) -> Unit,
    private val onItemLongClick: (Article) -> Unit
) : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    inner class ArticleViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {

            binding.tvTitle.text = article.title
            binding.tvContentPreview.text = (article.description ?: "").take(50) + "..."


            binding.root.setOnClickListener { onItemClick(article) }

            binding.root.setOnLongClickListener {
                onItemLongClick(article)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }
}


