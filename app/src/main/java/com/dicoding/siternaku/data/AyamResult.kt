package com.dicoding.siternaku.data

data class AyamResult(
    val pakanHarianKg: Double,
    val pakanBulananKg: Double,
    val biayaPakanHarian: Double,
    val biayaPakanBulanan: Double,
    val telurPerHari: Double,
    val telurPerMinggu: Double,
    val telurPerBulan: Double,
    val pendapatanTelurPerBulan: Double,
    val totalBobotPanenKg: Double,
    val pendapatanPanen: Double,
    val ayamHidup: Int
)
