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
            val (kebutuhan, merkLink) = when(h.jenis) {
                "Ayam" -> {
                    val gram = when {
                        h.usia in 0..1 -> h.jumlah * 50
                        h.usia in 2..2 -> h.jumlah * 70
                        h.usia in 3..5 -> h.jumlah * 90
                        else -> h.jumlah * 100
                    }
                    val merk = when(h.usia) {
                        in 0..1 -> "<a href='https://shopee.co.id/Pakan-ayam-bangkok-A591k-A594k-hi-pro-vite-i.1493360521.44150878504?sp_atk=25a07748-1692-45cc-8fd6-89c8eecf092b&xptdk=25a07748-1692-45cc-8fd6-89c8eecf092b'>BR-1K - Liat produknya disini</a>"
                        in 2..2 -> "<a href='https://shopee.co.id/Pakan-ayam-bangkok-A591k-A594k-hi-pro-vite-i.1493360521.44150878504?sp_atk=25a07748-1692-45cc-8fd6-89c8eecf092b&xptdk=25a07748-1692-45cc-8fd6-89c8eecf092b'>BR-2K - Liat produknya disini</a>"
                        in 3..5 -> "<a href='https://shopee.co.id/PAKAN-AYAM-A591K-A594K-HI-PRO-VITE-i.782076908.44053527161?sp_atk=83472d81-854e-4498-99da-4ef9abe06538&xptdk=83472d81-854e-4498-99da-4ef9abe06538'>BR-2K - Liat produknya disini</a>"
                        else -> "<a href='https://shopee.co.id/PAKAN-AYAM-A591K-A594K-HI-PRO-VITE-i.782076908.44053527161?sp_atk=83472d81-854e-4498-99da-4ef9abe06538&xptdk=83472d81-854e-4498-99da-4ef9abe06538'>BR-3K - Liat produknya disini</a> / <a href='https://shopee.co.id/PAKAN-AYAM-A591K-A594K-HI-PRO-VITE-i.782076908.44053527161?sp_atk=83472d81-854e-4498-99da-4ef9abe06538&xptdk=83472d81-854e-4498-99da-4ef9abe06538'>Voer A591K - Liat produknya disini</a>"
                    }
                    gram to merk
                }
                "Kambing" -> {
                    val gram = when {
                        h.usia in 0..5 -> h.jumlah * 150
                        h.usia in 6..12 -> h.jumlah * 200
                        else -> h.jumlah * 500
                    }
                    val merk = when(h.usia) {
                        in 0..5 -> "<a href='https://shopee.co.id/Ampas-Kedelai-Oven-Bau-harum-Untuk-Pakan-Ruminansia-Kambing-Sapi-Protein-16-persen.-i.1585492246.40760399456?sp_atk=5e8ced6c-6a17-4946-a67f-110c04509153&xptdk=5e8ced6c-6a17-4946-a67f-110c04509153'>Ternak Mandiri - Liat produknya disini</a>"
                        in 6..12 -> "<a href='https://shopee.co.id/Ampas-Kedelai-Oven-Bau-harum-Untuk-Pakan-Ruminansia-Kambing-Sapi-Protein-16-persen.-i.1585492246.40760399456?sp_atk=5e8ced6c-6a17-4946-a67f-110c04509153&xptdk=5e8ced6c-6a17-4946-a67f-110c04509153'>GDM Pakan Kambing - Liat produknya disini</a>"
                        else -> "<a href='https://shopee.co.id/Premix-Supermix-MS-1-Penggemukan-Campuran-Pakan-Untuk-Sapi-Kambing-Domba-1Kg-i.1559356037.44004995287?sp_atk=4ce2e6c6-91b3-44b5-8d9c-53f9cf987475&xptdk=4ce2e6c6-91b3-44b5-8d9c-53f9cf987475'>GDM Pakan Kambing - Liat produknya disini</a> / <a href='https://shopee.co.id/Premix-Supermix-MS-1-Penggemukan-Campuran-Pakan-Untuk-Sapi-Kambing-Domba-1Kg-i.1559356037.44004995287?sp_atk=4ce2e6c6-91b3-44b5-8d9c-53f9cf987475&xptdk=4ce2e6c6-91b3-44b5-8d9c-53f9cf987475'>Ternak Mandiri - Liat produknya disini</a>"
                    }
                    gram to merk
                }
                else -> 0 to "-"
            }

            sb.append("${h.jumlah} ${h.jenis} (usia ${h.usia} bulan) → butuh $kebutuhan gram pakan\n")
            sb.append("Merk pakan yang direkomendasikan: $merkLink\n\n")
        }

        _hasil.value = sb.toString()
    }

    fun resetData() {
        _daftarHewan.value = emptyList()
    }
}


