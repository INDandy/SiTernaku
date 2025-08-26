package com.dicoding.siternaku

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.siternaku.databinding.ActivityAddArticleBinding
import com.dicoding.siternaku.entity.Article
import com.dicoding.siternaku.ui.home.HomeViewModel

class AddArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddArticleBinding
    private val viewModel: HomeViewModel by viewModels()
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                selectedImageUri = uri

                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                binding.ivThumbnail.setImageURI(selectedImageUri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPickImage.setOnClickListener {
            openGallery()
        }

        binding.btnSaveArticle.setOnClickListener {
            saveArticle()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        pickImageLauncher.launch(intent)
    }

    private fun saveArticle() {
        val article = Article(
            thumbnailUri = selectedImageUri?.toString(),
            title = binding.etTitle.text.toString(),
            heading = binding.etHeading.text.toString(),
            subHeading = binding.etSubHeading.text.toString(),
            h3 = binding.etH3.text.toString(),
            h4 = binding.etH4.text.toString(),
            h5 = binding.etH5.text.toString(),
            h6 = binding.etH6.text.toString(),
            description = binding.etDescription.text.toString(),
            link = binding.etLink.text.toString(),
            content = binding.etDescription.text.toString()
        )

        viewModel.insert(article)
        Toast.makeText(this, "Artikel berhasil disimpan!", Toast.LENGTH_SHORT).show()
        finish()
    }
}

