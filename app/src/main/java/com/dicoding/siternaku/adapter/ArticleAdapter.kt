package com.dicoding.siternaku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.siternaku.R
import com.dicoding.siternaku.databinding.ItemArticleBinding
import com.dicoding.siternaku.entity.Article


class ArticleAdapter(
    private val onItemClick: (Article) -> Unit,
    private val onItemLongClick: ((Article) -> Unit)? = null
) : PagingDataAdapter<Article, ArticleAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.tvDate.text = article.date

            Glide.with(binding.ivThumbnail.context)
                .load(article.thumbnailUri)
                .placeholder(R.drawable.photos)
                .centerCrop()
                .into(binding.ivThumbnail)

            binding.root.setOnClickListener {
                onItemClick(article)
            }

            binding.root.setOnLongClickListener {
                onItemLongClick?.invoke(article)
                true
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}



