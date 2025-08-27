package com.dicoding.siternaku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.siternaku.R
import com.dicoding.siternaku.data.LaporanKambing
import java.text.NumberFormat
import java.util.Locale

class LaporanKambingAdapter(
    var items: List<LaporanKambing>
) : RecyclerView.Adapter<LaporanKambingAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTanggal: TextView = view.findViewById(R.id.tvTanggal)
        val tvKategori: TextView = view.findViewById(R.id.tvKategori)
        val tvBiaya: TextView = view.findViewById(R.id.tvBiaya)
        val tvPendapatan: TextView = view.findViewById(R.id.tvPendapatan)
        val tvKeuntungan: TextView = view.findViewById(R.id.tvKeuntungan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_laporan_kambing, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvTanggal.text = item.tanggal
        holder.tvKategori.text = item.kategori
        holder.tvBiaya.text = "Biaya: ${item.biaya.formatRupiah()}"
        holder.tvPendapatan.text = "Pendapatan: ${item.pendapatan.formatRupiah()}"
        holder.tvKeuntungan.text = "Keuntungan: ${item.keuntungan.formatRupiah()}"
    }

    override fun getItemCount() = items.size
}

fun Double.formatRupiah(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    formatter.maximumFractionDigits = 0
    return formatter.format(this)
}

