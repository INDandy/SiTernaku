package com.dicoding.siternaku.ui.calculatorayam

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
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.siternaku.R
import com.dicoding.siternaku.adapter.RekapAdapter

class CalculatorAyamFragment : Fragment() {

    private val viewModel: CalculatorAyamViewModel by viewModels()

    private val createDocumentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    val content = viewModel.getRingkasanText()
                    requireContext().contentResolver.openOutputStream(uri)?.use { outputStream ->
                        outputStream.write(content.toByteArray())
                    }
                    Toast.makeText(requireContext(), "Ringkasan disimpan ke lokasi yang dipilih", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_calculator_ayam, container, false)

        // ==================== Bind View ====================
        val etJumlahAyam = view.findViewById<EditText>(R.id.etJumlahAyam)
        val etPakanPerEkor = view.findViewById<EditText>(R.id.etPakanPerEkor)
        val etHargaPakan = view.findViewById<EditText>(R.id.etHargaPakan)
        val btnHitungPakan = view.findViewById<Button>(R.id.btnHitungPakan)
        val tvHasilPakan = view.findViewById<TextView>(R.id.tvHasilPakan)

        val etJumlahAyamBetina = view.findViewById<EditText>(R.id.etJumlahAyamBetina)
        val etPersenProduksiTelur = view.findViewById<EditText>(R.id.etPersenProduksiTelur)
        val etHargaTelur = view.findViewById<EditText>(R.id.etHargaTelur)
        val btnHitungTelur = view.findViewById<Button>(R.id.btnHitungTelur)
        val tvHasilTelur = view.findViewById<TextView>(R.id.tvHasilTelur)

        val etBobotPanen = view.findViewById<EditText>(R.id.etBobotPanen)
        val etHargaJualKg = view.findViewById<EditText>(R.id.etHargaJualKg)
        val btnHitungPanen = view.findViewById<Button>(R.id.btnHitungPanen)
        val tvHasilPanen = view.findViewById<TextView>(R.id.tvHasilPanen)

        val etMortalitas = view.findViewById<EditText>(R.id.etMortalitas)
        val btnHitungMortalitas = view.findViewById<Button>(R.id.btnHitungMortalitas)
        val tvHasilMortalitas = view.findViewById<TextView>(R.id.tvHasilMortalitas)

        val rvLaporan = view.findViewById<RecyclerView>(R.id.rvLaporan)
        rvLaporan.layoutManager = LinearLayoutManager(requireContext())
        val adapter = RekapAdapter()
        rvLaporan.adapter = adapter

        // 🔹 Tombol Simpan Ringkasan
        val btnSimpanRingkasan = view.findViewById<Button>(R.id.btnSimpanRingkasan)

        // ==================== Observers ====================
        viewModel.hasilPakan.observe(viewLifecycleOwner, Observer {
            tvHasilPakan.text = it
        })

        viewModel.hasilTelur.observe(viewLifecycleOwner, Observer {
            tvHasilTelur.text = it
        })

        viewModel.hasilPanen.observe(viewLifecycleOwner, Observer {
            tvHasilPanen.text = it
        })

        viewModel.hasilMortalitas.observe(viewLifecycleOwner, Observer {
            tvHasilMortalitas.text = it
        })

        viewModel.rekapList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.toList())
        })

        // ==================== Click Listeners ====================
        btnHitungPakan.setOnClickListener {
            val jumlahAyam = etJumlahAyam.text.toString().toIntOrNull() ?: 0
            val pakanPerEkor = etPakanPerEkor.text.toString().toDoubleOrNull() ?: 0.0
            val hargaPakan = etHargaPakan.text.toString().toDoubleOrNull() ?: 0.0
            viewModel.hitungPakan(jumlahAyam, pakanPerEkor, hargaPakan)
        }

        btnHitungTelur.setOnClickListener {
            val jumlahBetina = etJumlahAyamBetina.text.toString().toIntOrNull() ?: 0
            val persenProduksi = etPersenProduksiTelur.text.toString().toDoubleOrNull() ?: 0.0
            val hargaTelur = etHargaTelur.text.toString().toDoubleOrNull() ?: 0.0
            viewModel.hitungTelur(jumlahBetina, persenProduksi, hargaTelur)
        }

        btnHitungPanen.setOnClickListener {
            val jumlahAyam = etJumlahAyam.text.toString().toIntOrNull() ?: 0
            val bobot = etBobotPanen.text.toString().toDoubleOrNull() ?: 0.0
            val hargaPerKg = etHargaJualKg.text.toString().toDoubleOrNull() ?: 0.0
            viewModel.hitungPanen(jumlahAyam, bobot, hargaPerKg)
        }

        btnHitungMortalitas.setOnClickListener {
            val jumlahAyam = etJumlahAyam.text.toString().toIntOrNull() ?: 0
            val mortalitas = etMortalitas.text.toString().toDoubleOrNull() ?: 0.0
            val bobot = etBobotPanen.text.toString().toDoubleOrNull() ?: 0.0
            val hargaKg = etHargaJualKg.text.toString().toDoubleOrNull() ?: 0.0
            viewModel.hitungMortalitas(jumlahAyam, mortalitas, hargaKg, bobot)
        }

        btnSimpanRingkasan.setOnClickListener {
            val options = arrayOf("Simpan Otomatis", "Simpan ke Lokasi Lain")
            AlertDialog.Builder(requireContext())
                .setTitle("Pilih opsi simpan")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> { // Simpan Otomatis
                            val file = viewModel.simpanRingkasanKeTXT(requireContext())
                            if (file != null) {
                                Toast.makeText(requireContext(), "Ringkasan disimpan: ${file.name}", Toast.LENGTH_SHORT).show()

                                val uri = FileProvider.getUriForFile(
                                    requireContext(),
                                    "${requireContext().packageName}.provider",
                                    file
                                )
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    setDataAndType(uri, "text/plain")
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                }
                                startActivity(Intent.createChooser(intent, "Buka ringkasan dengan:"))
                            } else {
                                Toast.makeText(requireContext(), "Tidak ada data untuk disimpan", Toast.LENGTH_SHORT).show()
                            }
                        }
                        1 -> { // Simpan ke lokasi lain
                            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TITLE, "Ringkasan_Ayam.txt")
                            }
                            createDocumentLauncher.launch(intent)
                        }
                    }
                }
                .show()
        }
        return view
    }
}
