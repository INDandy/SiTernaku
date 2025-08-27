package com.dicoding.siternaku

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.dicoding.siternaku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedIndex = 0
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                selectedImageUri = uri
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedIndex = savedInstanceState?.getInt("SELECTED_INDEX") ?: 0

        binding = ActivityMainBinding.inflate(layoutInflater)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        binding.navView.onItemSelected = onItemSelected@{ index ->
            if (selectedIndex == index) return@onItemSelected
            selectedIndex = index
            when (index) {
                0 -> navController.navigate(R.id.navigation_home)
                1 -> navController.navigate(R.id.navigation_calculator)
                2 -> navController.navigate(R.id.navigation_settings)
            }
        }

        binding.navView.onItemSelected.invoke(selectedIndex)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SELECTED_INDEX", selectedIndex)
    }
}

