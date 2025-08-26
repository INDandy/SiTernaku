package com.dicoding.siternaku.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.siternaku.data.HewanInput

class CalculatorViewModel : ViewModel() {

    private val _daftarHewan = MutableLiveData<List<HewanInput>>(emptyList())
    val daftarHewan: LiveData<List<HewanInput>> = _daftarHewan

    private val _hasil = MutableLiveData<String>()
    val hasil: LiveData<String> = _hasil

    fun tambahHewan(hewan: HewanInput) {
        val listBaru = _daftarHewan.value.orEmpty().toMutableList()
        listBaru.add(hewan)
        _daftarHewan.value = listBaru
    }

    fun hitungKebutuhan() {
        val data = _daftarHewan.value.orEmpty()
        if (data.isEmpty()) {
            _hasil.value = "Belum ada data hewan."
            return
        }

        val sb = StringBuilder()
        data.forEach { h ->
            val kebutuhan = if (h.jenis == "Ayam") {
                if (h.usia < 6) h.jumlah * 50 else h.jumlah * 100
            } else {
                if (h.usia < 12) h.jumlah * 200 else h.jumlah * 500
            }
            sb.append("${h.jumlah} ${h.jenis} (usia ${h.usia} bulan) → butuh $kebutuhan gram pakan\n")
        }

        _hasil.value = sb.toString()
    }

    fun resetData() {
        _daftarHewan.value = emptyList()
    }

}
