package com.dicoding.siternaku.ui.calculatorkambing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.siternaku.R

class CalculatorKambing : Fragment() {

    companion object {
        fun newInstance() = CalculatorKambing()
    }

    private val viewModel: CalculatorKambingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_calculator_kambing, container, false)
    }
}