package com.dicoding.siternaku.ui.calculator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.siternaku.R
import com.dicoding.siternaku.ui.calculatorayam.CalculatorAyamFragment
import com.dicoding.siternaku.ui.calculatorkambing.CalculatorKambing
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)


        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 2
            override fun createFragment(position: Int): Fragment =
                if (position == 0) CalculatorAyamFragment() else CalculatorKambing()
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = if (pos == 0) "Ayam" else "Kambing"
        }.attach()
    }
}



