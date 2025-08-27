package com.dicoding.siternaku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.siternaku.R
import com.dicoding.siternaku.data.LaporanAyam

class RekapAdapter : RecyclerView.Adapter<RekapAdapter.RekapViewHolder>() {

    private var data: List<LaporanAyam> = listOf()

    fun submitList(list: List<LaporanAyam>) {
        data = list
        notifyDataSetChanged()
    }

    inner class RekapViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvKategori: TextView = view.findViewById(R.id.tvKategori)
        val tvTanggal: TextView = view.findViewById(R.id.tvTanggal)
        val tvPendapatan: TextView = view.findViewById(R.id.tvPendapatan)
        val tvBiaya: TextView = view.findViewById(R.id.tvBiaya)
        val tvKeuntungan: TextView = view.findViewById(R.id.tvKeuntungan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RekapViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rekap, parent, false)
        return RekapViewHolder(view)
    }

    override fun onBindViewHolder(holder: RekapViewHolder, position: Int) {
        val item = data[position]
        holder.tvKategori.text = item.kategori
        holder.tvTanggal.text = item.tanggal
        holder.tvPendapatan.text = "Pendapatan: Rp${item.pendapatan}"
        holder.tvBiaya.text = "Biaya: Rp${item.biaya}"
        holder.tvKeuntungan.text = "Keuntungan: Rp${item.keuntungan}"
    }

    override fun getItemCount(): Int = data.size
}