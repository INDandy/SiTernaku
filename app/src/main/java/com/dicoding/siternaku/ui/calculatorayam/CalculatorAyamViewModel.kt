package com.dicoding.siternaku.ui.calculatorayam

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.siternaku.data.GrafikData
import com.dicoding.siternaku.data.LaporanAyam
import java.io.File
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalculatorAyamViewModel : ViewModel() {

    private val _hasilPakan = MutableLiveData<String>()
    val hasilPakan: LiveData<String> get() = _hasilPakan

    private val _hasilTelur = MutableLiveData<String>()
    val hasilTelur: LiveData<String> get() = _hasilTelur

    private val _hasilPanen = MutableLiveData<String>()
    val hasilPanen: LiveData<String> get() = _hasilPanen

    private val _hasilMortalitas = MutableLiveData<String>()
    val hasilMortalitas: LiveData<String> get() = _hasilMortalitas

    private val _rekapList = MutableLiveData<MutableList<LaporanAyam>>(mutableListOf())
    val rekapList: LiveData<MutableList<LaporanAyam>> get() = _rekapList

    private val _grafikPakan = MutableLiveData<MutableList<GrafikData>>(mutableListOf())
    val grafikPakan: LiveData<MutableList<GrafikData>> get() = _grafikPakan

    private val _grafikBobot = MutableLiveData<MutableList<GrafikData>>(mutableListOf())
    val grafikBobot: LiveData<MutableList<GrafikData>> get() = _grafikBobot

    private val _grafikTelur = MutableLiveData<MutableList<GrafikData>>(mutableListOf())
    val grafikTelur: LiveData<MutableList<GrafikData>> get() = _grafikTelur

    fun hitungPakan(jumlahAyam: Int, pakanPerEkor: Double, hargaPakan: Double) {
        val totalPakan = jumlahAyam * pakanPerEkor
        val biayaHarian = totalPakan * hargaPakan
        val biayaBulanan = biayaHarian * 30
        _hasilPakan.value = "Total Pakan: $totalPakan kg\n" +
                "Biaya Harian: ${biayaHarian.formatRupiah()}\n" +
                "Biaya Bulanan: ${biayaBulanan.formatRupiah()}"

        _grafikPakan.value?.add(GrafikData(getTanggal(), totalPakan))
        _grafikPakan.postValue(_grafikPakan.value)

        tambahRekap("Pakan", biayaHarian, biayaBulanan)
    }

    fun hitungTelur(jumlahBetina: Int, persenProduksi: Double, hargaTelur: Double) {
        val produksiHarian = jumlahBetina * (persenProduksi / 100)
        val pendapatanHarian = produksiHarian * hargaTelur
        val produksiBulanan = produksiHarian * 30
        val pendapatanBulanan = produksiBulanan * hargaTelur

        _hasilTelur.value = "Produksi Harian: $produksiHarian butir\n" +
                "Pendapatan Harian: ${pendapatanHarian.formatRupiah()}\n" +
                "Produksi Bulanan: $produksiBulanan butir\n" +
                "Pendapatan Bulanan: ${pendapatanBulanan.formatRupiah()}"

        _grafikTelur.value?.add(GrafikData(getTanggal(), produksiHarian))
        _grafikTelur.postValue(_grafikTelur.value)

        tambahRekap("Telur", 0.0, pendapatanBulanan)
    }

    fun hitungPanen(jumlahAyam: Int, bobot: Double, hargaPerKg: Double) {
        val totalBobot = jumlahAyam * bobot
        val pendapatan = totalBobot * hargaPerKg

        _hasilPanen.value = "Total Bobot: $totalBobot kg\n" +
                "Pendapatan Panen: ${pendapatan.formatRupiah()}"

        _grafikBobot.value?.add(GrafikData(getTanggal(), totalBobot))
        _grafikBobot.postValue(_grafikBobot.value)

        tambahRekap("Panen", 0.0, pendapatan)
    }

    fun hitungMortalitas(jumlahAyam: Int, mortalitas: Double, hargaKg: Double, bobot: Double) {
        val mati = jumlahAyam * (mortalitas / 100)
        val hidup = jumlahAyam - mati
        val potensiKehilangan = mati * bobot * hargaKg

        _hasilMortalitas.value = "Mati: ${mati.toInt()} ekor\n" +
                "Hidup: ${hidup.toInt()} ekor\n" +
                "Potensi Kehilangan Pendapatan: ${potensiKehilangan.formatRupiah()}"

        tambahRekap("Mortalitas", 0.0, -potensiKehilangan)
    }

    private fun tambahRekap(
        kategori: String,
        biaya: Double,
        pendapatan: Double,
        detail: String = "",
        ringkasan: String = ""
    ) {
        val keuntungan = pendapatan - biaya

        val laporan = LaporanAyam(
            tanggal = getTanggal(),
            kategori = kategori,
            biaya = biaya,
            pendapatan = pendapatan,
            keuntungan = keuntungan,
            detail = if (detail.isNotEmpty()) detail else "Biaya: ${biaya.formatRupiah()}, Pendapatan: ${pendapatan.formatRupiah()}",
            ringkasan = if (ringkasan.isNotEmpty()) ringkasan else if (keuntungan >= 0) "Untung" else "Rugi"
        )

        _rekapList.value?.add(laporan)
        _rekapList.postValue(_rekapList.value)
    }

    private fun getTanggal(): String {
        val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun Double.formatRupiah(): String {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        if (format is java.text.DecimalFormat) {
            format.applyPattern("Rp #,###.##")
            format.minimumFractionDigits = 2
            format.maximumFractionDigits = 2
        }
        return format.format(this)
    }

    fun getRingkasanText(): String {
        val data = _rekapList.value ?: emptyList()
        if (data.isEmpty()) return "Belum ada data laporan."

        return buildString {
            append("=== Rekapan Laporan Ayam ===\n\n")
            data.forEach {
                append("Tanggal: ${it.tanggal}\n")
                append("Kategori: ${it.kategori}\n")
                append("Biaya: ${it.biaya.formatRupiah()}\n")
                append("Pendapatan: ${it.pendapatan.formatRupiah()}\n")
                append("Keuntungan: ${it.keuntungan.formatRupiah()}\n")
                append("\n-----------------\n\n")
            }
        }
    }

    fun simpanRingkasanKeTXT(context: Context, onError: (String) -> Unit): File? {
        val data = _rekapList.value ?: emptyList()
        if (data.isEmpty()) {
            onError("Tidak ada data yang akan disimpan")
            return null
        }

        val fileName = "Rekapan_Ayam_${System.currentTimeMillis()}.txt"
        val file = File(context.getExternalFilesDir(null), fileName)

        val content = getRingkasanText()
        file.writeText(content)

        return file
    }
}
