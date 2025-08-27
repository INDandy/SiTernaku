package com.dicoding.siternaku.ui.calculatorkambing

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.siternaku.R
import com.dicoding.siternaku.adapter.LaporanKambingAdapter

class CalculatorKambing : Fragment() {

    private val viewModel: CalculatorKambingViewModel by viewModels()

    private val createDocumentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    val file = viewModel.simpanRingkasanKeTXT(requireContext()) { pesan ->
                        Toast.makeText(requireContext(), pesan, Toast.LENGTH_SHORT).show()
                    }
                    file?.let {
                        requireContext().contentResolver.openOutputStream(uri)?.use { outputStream ->
                            outputStream.write(it.readText().toByteArray())
                        }
                        Toast.makeText(requireContext(), "Ringkasan disimpan ke lokasi yang dipilih", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_calculator_kambing, container, false)

        val etJumlahKambingPakan = view.findViewById<EditText>(R.id.etJumlahKambingPakan)
        val etHijauanPerEkor = view.findViewById<EditText>(R.id.etHijauanPerEkor)
        val etKonsentratPerEkor = view.findViewById<EditText>(R.id.etKonsentratPerEkor)
        val etHargaPakan = view.findViewById<EditText>(R.id.etHargaPakan)
        val btnHitungPakan = view.findViewById<Button>(R.id.btnHitungPakan)
        val tvHasilPakan = view.findViewById<TextView>(R.id.tvHasilPakan)

        val etJumlahKambingPanen = view.findViewById<EditText>(R.id.etJumlahKambingPanen)
        val etBobotAwalPanen = view.findViewById<EditText>(R.id.etBobotAwalPanen)
        val etRataPertambahan = view.findViewById<EditText>(R.id.etRataPertambahan)
        val etHargaJualKg = view.findViewById<EditText>(R.id.etHargaJualKg)
        val btnHitungPanen = view.findViewById<Button>(R.id.btnHitungPanen)
        val tvHasilPanen = view.findViewById<TextView>(R.id.tvHasilPanen)

        val etJumlahKambingPerah = view.findViewById<EditText>(R.id.etJumlahKambingPerah)
        val etLiterPerEkor = view.findViewById<EditText>(R.id.etLiterPerEkor)
        val etHargaSusu = view.findViewById<EditText>(R.id.etHargaSusu)
        val btnHitungSusu = view.findViewById<Button>(R.id.btnHitungSusu)
        val tvHasilSusu = view.findViewById<TextView>(R.id.tvHasilSusu)

        val etJumlahIndukan = view.findViewById<EditText>(R.id.etJumlahIndukan)
        val etRataAnakPerTahun = view.findViewById<EditText>(R.id.etRataAnakPerTahun)
        val etHargaJualAnak = view.findViewById<EditText>(R.id.etHargaJualAnak)
        val btnHitungPerkembangbiakan = view.findViewById<Button>(R.id.btnHitungPerkembangbiakan)
        val tvHasilPerkembangbiakan = view.findViewById<TextView>(R.id.tvHasilPerkembangbiakan)

        val rvLaporan = view.findViewById<RecyclerView>(R.id.rvLaporanKambing)
        rvLaporan.layoutManager = LinearLayoutManager(requireContext())
        val adapter = LaporanKambingAdapter(emptyList())
        rvLaporan.adapter = adapter

        val btnSimpanRingkasan = view.findViewById<Button>(R.id.btnSimpanRingkasanKambing)

        viewModel.hasilPakan.observe(viewLifecycleOwner, Observer { tvHasilPakan.text = it })
        viewModel.hasilPanen.observe(viewLifecycleOwner, Observer { tvHasilPanen.text = it })
        viewModel.hasilSusu.observe(viewLifecycleOwner, Observer { tvHasilSusu.text = it })
        viewModel.hasilPerkembangbiakan.observe(viewLifecycleOwner, Observer { tvHasilPerkembangbiakan.text = it })
        viewModel.rekapList.observe(viewLifecycleOwner) {
            adapter.items = it
            adapter.notifyDataSetChanged()
        }

        btnHitungPakan.setOnClickListener {
            val jumlah = etJumlahKambingPakan.text.toString().toIntOrNull() ?: 0
            val hijauan = etHijauanPerEkor.text.toString().toDoubleOrNull() ?: 0.0
            val konsentrat = etKonsentratPerEkor.text.toString().toDoubleOrNull() ?: 0.0
            val harga = etHargaPakan.text.toString().toDoubleOrNull() ?: 0.0
            viewModel.hitungPakan(jumlah, hijauan, konsentrat, harga)
        }

        btnHitungPanen.setOnClickListener {
            val jumlah = etJumlahKambingPanen.text.toString().toIntOrNull() ?: 0
            val bobotAwal = etBobotAwalPanen.text.toString().toDoubleOrNull() ?: 0.0
            val pertambahan = etRataPertambahan.text.toString().toDoubleOrNull() ?: 0.0
            val harga = etHargaJualKg.text.toString().toDoubleOrNull() ?: 0.0
            viewModel.hitungPanen(jumlah, bobotAwal, pertambahan, harga)
        }

        btnHitungSusu.setOnClickListener {
            val jumlah = etJumlahKambingPerah.text.toString().toIntOrNull() ?: 0
            val liter = etLiterPerEkor.text.toString().toDoubleOrNull() ?: 0.0
            val harga = etHargaSusu.text.toString().toDoubleOrNull() ?: 0.0
            viewModel.hitungSusu(jumlah, liter, harga)
        }

        btnHitungPerkembangbiakan.setOnClickListener {
            val jumlah = etJumlahIndukan.text.toString().toIntOrNull() ?: 0
            val rataAnak = etRataAnakPerTahun.text.toString().toDoubleOrNull() ?: 0.0
            val harga = etHargaJualAnak.text.toString().toDoubleOrNull() ?: 0.0
            viewModel.hitungPerkembangbiakan(jumlah, rataAnak, harga)
        }

        btnSimpanRingkasan.setOnClickListener {
            if (viewModel.rekapList.value.isNullOrEmpty()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Perhatian")
                    .setMessage("Tidak ada data yang dapat disimpan")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }

            val options = arrayOf("Simpan Otomatis", "Simpan ke Lokasi Lain")
            AlertDialog.Builder(requireContext())
                .setTitle("Pilih opsi simpan")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> {
                            val file = viewModel.simpanRingkasanKeTXT(requireContext()) { pesan ->
                                Toast.makeText(requireContext(), pesan, Toast.LENGTH_SHORT).show()
                            }
                            file?.let {
                                Toast.makeText(requireContext(), "Rekapan disimpan: ${it.name}", Toast.LENGTH_SHORT).show()
                            }
                        }
                        1 -> {
                            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TITLE, "Rekapan_Kambing.txt")
                            }
                            createDocumentLauncher.launch(intent)
                        }
                    }
                }.show()
        }
        return view
    }
}

