package com.dicoding.siternaku.data

data class LaporanAyam(
    val tanggal: String,
    val kategori: String,
    val biaya: Double,
    val pendapatan: Double,
    val keuntungan: Double,
    val detail: String,
    val ringkasan: String
)
