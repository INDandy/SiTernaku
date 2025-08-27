package com.dicoding.siternaku

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        homeViewModel.getArticleById(articleId).observe(this) { article ->
            article?.let {
                binding.tvTitle.text = it.title
                binding.tvHeading.text = it.heading
                binding.tvH3.text = it.h3
                binding.tvLink.text = it.link
                binding.tvLink.setOnClickListener { _ ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.link))
                    startActivity(intent)
                }

                binding.ivThumbnail.background =
                    ContextCompat.getDrawable(this, R.drawable.image_border)
                binding.ivThumbnail.clipToOutline = true

                Glide.with(this)
                    .load(Uri.parse(it.thumbnailUri))
                    .placeholder(R.drawable.image_placeholder)
                    .centerCrop()
                    .into(binding.ivThumbnail)
            }
        }
    }
}
