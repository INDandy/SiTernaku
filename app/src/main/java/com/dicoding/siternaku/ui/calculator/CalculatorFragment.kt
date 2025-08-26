package com.dicoding.siternaku.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dicoding.siternaku.R
import com.dicoding.siternaku.data.HewanInput

class CalculatorFragment : Fragment() {

    private val viewModel: CalculatorViewModel by viewModels()
    private lateinit var containerInput: LinearLayout
    private lateinit var btnTambah: Button
    private lateinit var btnHitung: Button
    private lateinit var tvHasil: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calculator, container, false)

        containerInput = root.findViewById(R.id.containerInput)
        btnTambah = root.findViewById(R.id.btnTambah)
        btnHitung = root.findViewById(R.id.btnHitung)
        tvHasil = root.findViewById(R.id.tvHasil)

        btnTambah.setOnClickListener {
            tambahFormHewan(inflater)
        }

        btnHitung.setOnClickListener {
            viewModel.resetData()
            containerInput.children.forEach { view ->
                val spinner = view.findViewById<Spinner>(R.id.spJenis)
                val etJumlah = view.findViewById<EditText>(R.id.etJumlah)
                val etUsia = view.findViewById<EditText>(R.id.etUsia)

                val jenis = spinner.selectedItem.toString()
                val jumlah = etJumlah.text.toString().toIntOrNull() ?: 0
                val usia = etUsia.text.toString().toIntOrNull() ?: 0

                if (jumlah > 0) {
                    viewModel.tambahHewan(HewanInput(jenis, jumlah, usia))
                }
            }
            viewModel.hitungKebutuhan()
        }

        viewModel.hasil.observe(viewLifecycleOwner, Observer {
            tvHasil.text = it
        })

        tambahFormHewan(inflater)
        tambahFormHewan(inflater)

        return root
    }


    private fun tambahFormHewan(inflater: LayoutInflater) {
        val itemView = inflater.inflate(R.layout.item_hewan_input, containerInput, false)

        val btnHapus = itemView.findViewById<ImageButton>(R.id.btnHapus)
        btnHapus.setOnClickListener {
            containerInput.removeView(itemView)
        }

        containerInput.addView(itemView)
    }
}
