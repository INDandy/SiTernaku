package com.dicoding.siternaku

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.dicoding.siternaku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Atur visibilitas FAB berdasarkan fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.navigation_home) {
                binding.fabAdd.show()
            } else {
                binding.fabAdd.hide()
            }
        }

        binding.fabAdd.setOnClickListener {
            Toast.makeText(this, "FAB di Home ditekan", Toast.LENGTH_SHORT).show()
        }

        binding.navView.onItemSelected = { index ->
            when (index) {
                0 -> navController.navigate(R.id.navigation_home)
                1 -> navController.navigate(R.id.navigation_dashboard)
                2 -> navController.navigate(R.id.navigation_notifications)
            }
        }
    }
}
