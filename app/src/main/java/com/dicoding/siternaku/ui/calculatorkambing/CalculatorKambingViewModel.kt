package com.dicoding.siternaku.ui.calculatorkambing

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.siternaku.data.LaporanKambing
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalculatorKambingViewModel : ViewModel() {

    private val _rekapList = MutableLiveData<MutableList<LaporanKambing>>(mutableListOf())
    val rekapList: MutableLiveData<MutableList<LaporanKambing>> get() = _rekapList

    val hasilPakan = MutableLiveData<String>()
    val hasilPanen = MutableLiveData<String>()
    val hasilSusu = MutableLiveData<String>()
    val hasilPerkembangbiakan = MutableLiveData<String>()

    private fun getTanggal(): String = SimpleDateFormat("dd/MM", Locale.getDefault()).format(Date())

    fun hitungPakan(jumlahKambing: Int, hijauan: Double, konsentrat: Double, harga: Double) {
        val totalPakan = jumlahKambing * (hijauan + konsentrat)
        val biayaHarian = totalPakan * harga
        val biayaBulanan = biayaHarian * 30
        hasilPakan.value = "Total Pakan: $totalPakan kg\n" +
                "Biaya Harian: Rp%,.0f".format(biayaHarian) + "\n" +
                "Biaya Bulanan: Rp%,.0f".format(biayaBulanan)

        val laporan = LaporanKambing(
            tanggal = getTanggal(),
            kategori = "Pakan",
            biaya = biayaHarian,
            pendapatan = 0.0,
            keuntungan = -biayaHarian
        )
        _rekapList.value?.add(laporan)
        _rekapList.postValue(_rekapList.value)
    }

    fun hitungPanen(
        jumlahKambing: Int,
        bobotAwal: Double,
        pertambahan: Double,
        hargaPerKg: Double
    ) {
        val bobotAkhir = bobotAwal + pertambahan
        val totalBerat = jumlahKambing * bobotAkhir
        val pendapatan = totalBerat * hargaPerKg
        hasilPanen.value = "Bobot Akhir: $bobotAkhir kg\n" +
                "Total Berat: $totalBerat kg\n" +
                "Pendapatan: Rp%,.0f".format(pendapatan)

        val laporan = LaporanKambing(
            tanggal = getTanggal(),
            kategori = "Panen",
            biaya = 0.0,
            pendapatan = pendapatan,
            keuntungan = pendapatan
        )
        _rekapList.value?.add(laporan)
        _rekapList.postValue(_rekapList.value)
    }

    fun hitungSusu(jumlahKambing: Int, literPerEkor: Double, hargaPerLiter: Double) {
        val totalHarian = jumlahKambing * literPerEkor
        val totalBulanan = totalHarian * 30
        val pendapatan = totalHarian * hargaPerLiter
        hasilSusu.value = "Produksi Harian: $totalHarian Liter\n" +
                "Produksi Bulanan: $totalBulanan Liter\n" +
                "Pendapatan: Rp%,.0f".format(pendapatan)

        val laporan = LaporanKambing(
            tanggal = getTanggal(),
            kategori = "Susu",
            biaya = 0.0,
            pendapatan = pendapatan,
            keuntungan = pendapatan
        )
        _rekapList.value?.add(laporan)
        _rekapList.postValue(_rekapList.value)
    }

    fun hitungPerkembangbiakan(jumlahIndukan: Int, rataAnakPerTahun: Double, hargaJual: Double) {
        val totalAnak = jumlahIndukan * rataAnakPerTahun
        val pendapatan = totalAnak * hargaJual
        hasilPerkembangbiakan.value = "Estimasi Anak: $totalAnak ekor\n" +
                "Pendapatan Tambahan: Rp%,.0f".format(pendapatan)

        val laporan = LaporanKambing(
            tanggal = getTanggal(),
            kategori = "Perkembangbiakan",
            biaya = 0.0,
            pendapatan = pendapatan,
            keuntungan = pendapatan
        )
        _rekapList.value?.add(laporan)
        _rekapList.postValue(_rekapList.value)
    }

    fun simpanRingkasanKeTXT(context: Context, onError: (String) -> Unit): File? {
        val data = _rekapList.value ?: emptyList()
        if (data.isEmpty()) {
            onError("Tidak ada data yang akan disimpan")
            return null
        }

        val fileName = "Rekapan_Kambing_${System.currentTimeMillis()}.txt"
        val file = File(context.getExternalFilesDir(null), fileName)

        val content = buildString {
            append("=== Rekapan Laporan Kambing ===\n\n")
            data.forEach {
                append("Tanggal: ${it.tanggal}\n")
                append("Kategori: ${it.kategori}\n")
                append("Biaya: Rp%,.0f".format(it.biaya) + "\n")
                append("Pendapatan: Rp%,.0f".format(it.pendapatan) + "\n")
                append("Keuntungan: Rp%,.0f".format(it.keuntungan) + "\n")
                append("\n-----------------\n\n")
            }
        }

        file.writeText(content)
        return file
    }
}
