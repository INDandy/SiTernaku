package com.dicoding.siternaku.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.siternaku.AddArticleActivity
import com.dicoding.siternaku.DetailArticleActivity
import com.dicoding.siternaku.adapter.ArticleAdapter
import com.dicoding.siternaku.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        articleAdapter = ArticleAdapter(
            onItemClick = { article ->
                val intent = Intent(requireContext(), DetailArticleActivity::class.java)
                intent.putExtra("ARTICLE_ID", article.id)
                startActivity(intent)
            },
            onItemLongClick = { article ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Artikel")
                    .setMessage("Yakin mau hapus artikel ini?")
                    .setPositiveButton("Hapus") { _, _ ->
                        homeViewModel.delete(article)
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        )


        binding.recyclerViewArticles.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }

        homeViewModel.allArticles.observe(viewLifecycleOwner) {
            articleAdapter.submitList(it)
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), AddArticleActivity::class.java))
        }

        return binding.root
    }
}
