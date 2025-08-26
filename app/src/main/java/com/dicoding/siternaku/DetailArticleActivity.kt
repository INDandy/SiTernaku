package com.dicoding.siternaku

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.siternaku.databinding.ActivityDetailArticleBinding
import com.dicoding.siternaku.ui.home.HomeViewModel

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleId = intent.getIntExtra("ARTICLE_ID", -1)
        homeViewModel.allArticles.observe(this) { articles ->
            val article = articles.find { it.id == articleId }
            article?.let {
                binding.tvTitle.text = it.title
                binding.tvHeading.text = it.heading
                binding.tvSubHeading.text = it.subHeading
                binding.tvH1.text = it.heading
                binding.tvH2.text = it.subHeading
                binding.tvH3.text = it.h3
                binding.tvH4.text = it.h4
                binding.tvH5.text = it.h5
                binding.tvH6.text = it.h6
                binding.tvDescription.text = it.description
                binding.tvLink.text = it.link
                binding.tvLink.setOnClickListener { _ ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.link))
                    startActivity(intent)
                }
                binding.tvContent.text = it.content

                // thumbnail image pakai Glide/Picasso
                Glide.with(this)
                    .load(Uri.parse(it.thumbnailUri))
                    .placeholder(R.drawable.image_placeholder)
                    .into(binding.ivThumbnail)
            }
        }
    }
}

